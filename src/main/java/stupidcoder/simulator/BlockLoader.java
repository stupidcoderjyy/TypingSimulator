package stupidcoder.simulator;

import stupidcoder.util.ArrayUtil;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.InputException;

import java.util.ArrayList;
import java.util.List;

public class BlockLoader {
    private final BufferedInput input;
    private final Block root = Block.of();
    private final List<List<String>> idToSlices;
    private final Simulator simulator;

    protected BlockLoader(Simulator simulator, BufferedInput input) {
        this.simulator = simulator;
        this.idToSlices = simulator.slices;
        this.input = input;
    }

    protected void loadBlock() {
        if (input.find('$') < 0) {
            return;
        }
        loadBlock(root, 0);
    }

    private void loadBlock(Block parent, int offsetY) {
        int id = readId();
        if (id < parent.userId) {
            throw new InputException(String.format(
                        "child block id lower than parent (child: %d, parent : %d)",
                        id, parent.userId));
        }
        ArrayUtil.resize(idToSlices, id + 1, ArrayList::new);
        //对每一行进行读取（忽略每行开始的空格）
        int height = 0;  //高度不包含子块
        Block block = Block.of(id);
        LOOP:
        while (true) {
            input.mark();
            input.skip(' ');
            if (!input.available()) {
                throw new InputException("Unclosed block (id : " + id + ")");
            }
            int b = input.read();
            switch (b) {
                case '$':
                    input.removeMark();
                    if (input.read() == '$') {
                        input.skipLine();
                        break LOOP;
                    }
                    input.retract();
                    loadBlock(block, height);
                    break;
                case '%':
                    if (input.read() == '%') {
                        input.removeMark();
                        ignoreLines();
                    }
                    break;
                case '#':
                    input.removeMark();
                    input.mark();
                default:
                    height++;
                    if (b == '\r') {
                        input.retract();
                    }
                    input.approach('\r');
                    input.mark();
                    idToSlices.get(id).add(input.capture());
                    input.skipLine();
                    break;
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
        input.mark();
        input.approach('$');
        input.mark();
        int result = Integer.parseInt(input.capture());
        input.skipLine();
        return result;
    }

    private void ignoreLines() {
        try {
            while (true) {
                input.find('%');
                if (input.read() == '%') {
                    input.skipLine();
                    return;
                }
            }
        } catch (Exception e) {
            throw new InputException("unclosed ignore area");
        }
    }
}
