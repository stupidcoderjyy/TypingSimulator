package stupidcoder.simulator;

import stupidcoder.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class PosManager {
    protected final Block root = Block.of();
    private final List<Block> rawBlocks = new ArrayList<>();
    private Block[] generatedBlocks;
    private final Block generatedRoot = Block.of();
    private final Simulator simulator;

    protected PosManager(Simulator simulator) {
        this.simulator = simulator;
        rawBlocks.add(root);
    }

    protected void registerBlocks(Block parent, Block... blocks) {
        for (Block block : blocks) {
            parent.createChild(block);
            int userId = block.userId;
            ArrayUtil.resize(rawBlocks, userId + 1, () -> null);
            rawBlocks.set(userId, block);
        }
    }

    protected void simulate() {
        int size = rawBlocks.size();
        generatedBlocks = new Block[size];
        generatedBlocks[0] = generatedRoot;
        for (int i = 1; i < size; i ++) {
            insertBlock(rawBlocks.get(i));
        }
    }

    private void insertBlock(Block block) {
        //block.start是父块中在block上方slice的高度，不包含其他子块
        //block.width同样只包含了自己内部slice的高度，不包含子块
        if (block == null) {
            return;
        }
        Block target = Block.of(block);
        Block parent = block.parent == root ? generatedRoot : generatedBlocks[block.parent.userId];
        target.parent = parent;
        int insertPos = getInsertPos(target);
        //插入
        parent.children.add(insertPos, target);
        //更新块的位置
        target.start.shift(parent.start);
        if (insertPos > 0) {
            int totalHeight = 0;
            for (int i = 0 ; i < insertPos ; i ++) {
                totalHeight += parent.children.get(i).height;
            }
            target.start.shift(0, totalHeight);
        }
        //更新所有受影响的块
        Block p = parent;
        int shiftStart = insertPos + 1;
        int dHeight = target.height;
        do {
            p.height += dHeight; //插入子块后，所有父块拉伸
            p.shift(shiftStart, target.height); //移动所有被影响的块
            shiftStart = getChildIndex(p.parent, p) + 1;
            p = p.parent;
        } while (p != null);
        //更新数组
        generatedBlocks[target.userId] = target;
        //开始模拟
        simulator.onBlockInserted(generatedRoot.height - target.height, target);
    }

    private int getChildIndex(Block parent, Block child) {
        if (parent == null) {
            return -1;
        }
        int l = 0, r = parent.children.size() - 1;
        while (l < r) {
            int m = (l + r) >> 1;
            Block cur = parent.children.get(m);
            if (cur == child) {
                return m;
            }
            if (child.childId < cur.childId) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        if (l == r) {
            return child == parent.children.get(l) ? l : -1;
        }
        return -1;
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
            return child.childId < children.get(0).childId ? 0 : 1;
        }
        int targetChildId = child.childId;
        int l = 0, r = children.size() - 2;
        int m;
        while (l <= r) {
            m = (l + r) / 2;
            if (targetChildId < children.get(m).childId) {
                //在更上侧
                r = m - 1;
            } else if (targetChildId > children.get(m + 1).childId) {
                //在更下侧
                l = m + 1;
            } else {
                return m + 1;
            }
        }
        if (r < 0) {
            return 0;
        }
        return children.size();
    }
}
