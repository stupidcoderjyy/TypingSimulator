package com.stupidcoder.coder.core;

import com.stupidcoder.coder.core.operations.OpGoto;
import com.stupidcoder.coder.core.operations.OpWriteCode;
import com.stupidcoder.util.ArrayUtil;
import com.stupidcoder.util.input.BufferedInput;
import com.stupidcoder.util.input.IInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OperationHandler implements IOperationRegister{
    public static final int DELAY = 10;
    private final List<List<String>> idToSlices = new ArrayList<>();
    private final List<IRobotAction> actions = new ArrayList<>();
    private final Block root = Block.of();
    private final BlockHandler handler;
    private final IInput input;

    private OperationHandler(IInput input) {
        handler = new BlockHandler(root, this);
        this.input = input;
        loadBlock();
        handler.simulate();
        run();
    }

    public static void run(String path) {
        new OperationHandler(BufferedInput.fromFile(path));
    }

    private Pos lastEnd = null;
    private int maxY = 0;

    @Override
    public void register(int userId, Pos start, Pos end) {
        if (lastEnd != null) {
            actions.add(new OpGoto(lastEnd, start, start.y > maxY));
            System.out.println("from " + lastEnd + " to " + start);
        }
        lastEnd = end;
        maxY = Math.max(maxY, end.y);
        actions.add(new OpWriteCode(idToSlices.get(userId)));
    }

    private void run() {
        try {
            Robot robot = new Robot();
            for (IRobotAction action : actions) {
                action.run(robot);
            }
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadBlock() {
        while (input.available()) {
            while (input.available() && input.read() == '$') {
                loadBlock(root, 0);
            }
        }
    }

    private void loadBlock(Block parent, int offsetY) {
        int id = readId();
        //对每一行进行读取（忽略每行开始的空格）
        input.markLexemeStart();
        int height = 0;
        Block block = Block.of(id);
        LOOP:
        while (input.available()) {
            int b = input.read();
            switch (b) {
                case '\r' -> {
                    height++;
                    input.retract();
                    ArrayUtil.resize(idToSlices, id + 1, ArrayList::new);
                    String lexeme = input.lexeme();
                    idToSlices.get(id).add(lexeme.equals("\n") ? "" : lexeme);
                    input.skip(2);
                    input.markLexemeStart();
                }
                case '$' -> {
                    if (input.read() == '$') {
                        skipLine();
                        input.markLexemeStart();
                        break LOOP;
                    }
                    input.retract();
                    loadBlock(block, height);
                }
                case '%' -> {
                    if (input.read() == '%') {
                        ignoreLines();
                    }
                }
            }
        }
        List<String> slices = idToSlices.get(id);
        if (slices.size() > 0) {
            block.width = slices.get(slices.size() - 1).length();
        }
        block.height = height;
        block.start.set(0, offsetY);
        handler.registerBlocks(parent, block);
    }

    private int readId() {
        input.markLexemeStart();
        int result;
        while (true) {
            int b = input.read();
            if (b == '$') {
                input.retract();
                result = Integer.parseInt(input.lexeme());
                break;
            }
        }
        skipLine();
        return result;
    }

    private void ignoreLines() {
        while (input.available()) {
            while (input.available() && input.read() != '%');
            if (input.read() == '%') {
                skipLine();
                input.skipSpaceAndTab();
                input.markLexemeStart();
                break;
            }
        }
    }

    private void skipLine() {
        while (input.available()) {
            if (input.read() == '\r') {
                input.read();
                return;
            }
        }
    }
}
