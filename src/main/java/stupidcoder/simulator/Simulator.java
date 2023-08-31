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
    private final Robot robot;

    private Pos prevEnd = null;
    private final int startIndex;

    private Simulator(IInput input, int waitTime, int startIndex, boolean simulate) {
        this.startIndex = startIndex;
        manager = new PosManager(this);
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

    public static void run(String path, int waitTime, int startIndex) {
        new Simulator(BufferedInput.fromResource(path), waitTime, startIndex, false);
    }

    public static void run(String path, int waitTime) {
        new Simulator(BufferedInput.fromResource(path), waitTime, 0, false);
    }

    public static void simulate(String path, int waitTime, int startIndex) {
        new Simulator(BufferedInput.fromResource(path), waitTime, startIndex, true);
    }
    public static void simulate(String path, int waitTime) {
        new Simulator(BufferedInput.fromResource(path), waitTime, 0, true);
    }


    protected void onBlockLoaded(Block parent, Block b) {
        manager.registerBlocks(parent, b);
    }

    protected void onBlockInserted(int rootHeight, Block b) {
        if (b.height == 0) {
            return;
        }
        if (b.userId < startIndex) {
            System.out.println("ignored: " + b.userId);
            return;
        }
        Pos start = b.start;
        if (prevEnd != null) {
            runTasK(b, new OpGoto(prevEnd, start, rootHeight));
        } else {
            prevEnd = new Pos(0, 0);
        }
        OpWriteCode taskWrite = new OpWriteCode(slices.get(b.userId));
        runTasK(b, taskWrite);
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
}
