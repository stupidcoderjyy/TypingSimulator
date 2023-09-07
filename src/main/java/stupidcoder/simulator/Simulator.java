package stupidcoder.simulator;

import stupidcoder.simulator.operations.OpGoto;
import stupidcoder.simulator.operations.OpWriteCode;

import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.IInput;
import stupidcoder.util.input.InputException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulator {
    public static final int DELAY = 20;
    protected final List<List<String>> slices = new ArrayList<>();
    private final PosManager manager;
    private Robot robot;
    protected int startIndex = 0;
    protected int endIndex = Integer.MAX_VALUE;
    private int waitTime;

    private Pos prevEnd = null;

    public Simulator() {
        manager = new PosManager(this);
    }

    public void run(IInput input, boolean simulate) {
        BlockLoader loader = new BlockLoader(this, input);
        try {
            loader.loadBlock();
        } catch (Exception e) {
            if (e instanceof InputException ie) {
                ie.printStackTraceAndClose();
            } else {
                e.printStackTrace();
            }
            System.exit(e.hashCode());
        }
        try {
            this.robot = simulate ? null : new Robot();
            Thread.sleep(waitTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        manager.simulate();
        input.close();
    }

    public void run(String file, boolean simulate) {
        this.run(BufferedInput.fromResource(file), simulate);
    }

    protected void onBlockLoaded(Block parent, Block b) {
        manager.registerBlocks(parent, b);
    }

    protected void onBlockInserted(int rootHeight, Block b) {
        if (b.height == 0) {
            return;
        }
        if (b.userId < startIndex || b.userId > endIndex) {
            System.out.println("ignored: " + b.userId);
            return;
        }
        Pos start = b.start;
        if (prevEnd != null) {
            runTasK(b, new OpGoto(prevEnd, start, rootHeight));
        } else {
            prevEnd = new Pos(0, 0);
        }
        runTasK(b, new OpWriteCode(slices.get(b.userId)));
        prevEnd.set(start).shift(b.width, b.height - 1); //下一轮的起始位置
    }

    private void runTasK(Block b, IRobotAction action) {
        if (b.userId < startIndex) {
            System.out.println("task: " + action + "(ignored)");
        } else {
            System.out.println("task: " + action);
            action.run(robot);
        }
    }

    public void setRange(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
