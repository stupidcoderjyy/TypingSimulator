package com.stupidcoder.coder.core;

import com.stupidcoder.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {
    protected final Block root;
    private final List<Block> idToBlockPos = new ArrayList<>();

    private Block[] idToBlockGeneratedPos;
    private Block generatedRoot;
    private final IOperationRegister register;

    public BlockHandler(Block root, IOperationRegister register) {
        this.register = register;
        this.root = root;
        idToBlockPos.add(root);
    }

    protected void registerBlocks(Block parent, Block... blocks) {
        for (Block block : blocks) {
            parent.createChild(block);
            int userId = block.userId;
            ArrayUtil.resize(idToBlockPos, userId + 1, () -> null);
            idToBlockPos.set(userId, block);
        }
    }

    protected void simulate() {
        int size = idToBlockPos.size();
        generatedRoot = Block.of();
        idToBlockGeneratedPos = new Block[size];
        idToBlockGeneratedPos[0] = generatedRoot;
        for (int i = 1; i < size; i ++) {
            insertBlock(idToBlockPos.get(i));
        }
    }

    private void insertBlock(Block block) {
        Block result = Block.of(block);
        Block parent = block.parent == root ? generatedRoot : idToBlockGeneratedPos[block.parent.userId];
        result.parent = parent;
        int insertPos = getInsertPos(result);
        //插入
        parent.children.add(insertPos, result);
        //更新result的位置
        Pos start = result.start;
        start.shift(parent.start);
        if (insertPos > 0) {
            Block pre = parent.children.get(insertPos - 1);
            start.shift(0, pre.start.y + pre.height);
        }
        //更新所有块
        Block target = parent;
        int shiftStart = insertPos + 1;
        int totalSpace = result.height;
        do {
            target.height += totalSpace;
            target.shift(shiftStart, totalSpace);
            shiftStart = target.childId + 1;
            target = target.parent;
        } while (target != null);
        //更新数组
        idToBlockGeneratedPos[result.userId] = result;
        //设置op
        if (result.width == 0) {
            return;
        }
        Pos end = new Pos(start.x + result.width, start.y + result.height - 1);
        register.register(result.userId, new Pos(start), end);
    }

    private int getInsertPos(Block child) {
        List<Block> children = child.parent.children;
        if (children.isEmpty()) {
            return 0;
        }
        if (child.childId > children.get(children.size() - 1).childId) {
            return children.size();
        }
        return findFirstLower(child);
    }

    private int findFirstLower(Block child) {
        List<Block> children = child.parent.children;
        if (children.size() == 1) {
            return 0;
        }
        int targetChildId = child.childId;
        int l = 0, r = children.size();
        int m;
        while (l <= r) {
            m = (l + r) / 2;
            if (m == 0) {
                return 0;
            }
            if (targetChildId < children.get(m - 1).childId) {
                //在更上侧
                r = m - 1;
            } else if (targetChildId > children.get(m).childId) {
                //在更下侧
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }
}
