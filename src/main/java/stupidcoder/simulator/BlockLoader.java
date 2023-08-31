package stupidcoder.simulator;

import stupidcoder.util.ArrayUtil;
import stupidcoder.util.input.IInput;
import stupidcoder.util.input.InputException;

import java.util.ArrayList;
import java.util.List;

public class BlockLoader {
    private final IInput input;
    private final Block root = Block.of();
    private final List<List<String>> idToSlices;
    private final Simulator simulator;

    protected BlockLoader(Simulator simulator, IInput input) {
        this.simulator = simulator;
        this.idToSlices = simulator.slices;
        this.input = input;
    }

    protected void loadBlock() {
        while (input.available()) {
            if (input.read() == '$') {
                loadBlock(root, 0);
            }
            input.markLexemeStart();
        }
    }

    private void loadBlock(Block parent, int offsetY) {
        int id = readId();
        if (id < parent.userId) {
            throw new RuntimeException(String.format(
                        "child block id lower than parent (child: %d, parent : %d)",
                        id, parent.userId));
        }
        ArrayUtil.resize(idToSlices, id + 1, ArrayList::new);
        //对每一行进行读取（忽略每行开始的空格）
        input.markLexemeStart();
        int height = 0;  //高度不包含子块
        Block block = Block.of(id);
        LOOP:
        while (true) {
            if (!input.available()) {
                throw new InputException(input, "Unclosed block (id : " + id + ")");
            }
            int b = input.read();
            switch (b) {
                case '\r' -> {
                    height++;
                    input.retract();
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
        simulator.onBlockLoaded(parent, block);
        System.out.println("registered block: " + block.userId + " ,parent: " + parent.userId);
    }

    private int readId() {
        try {
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
        } catch (Exception e) {
            throw new InputException(input, e);
        }
    }

    private void ignoreLines() {
        try {
            while (true) {
                if (input.read() == '%') {
                    if (input.read() != '%') {
                        input.retract();
                        continue;
                    }
                    skipLine();
                    input.skipSpaceAndTab();
                    return;
                }
                input.markLexemeStart();
            }
        } catch (InputException e) {
            throw new InputException(input, "unclosed ignore area");
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
