package stupidcoder.simulator.operations;

import stupidcoder.simulator.ActionUtil;
import stupidcoder.simulator.IRobotAction;
import stupidcoder.simulator.Simulator;
import stupidcoder.simulator.Pos;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OpGoto implements IRobotAction {
    public static final int SINGLE_EXPAND = 1;
    public static final int SINGLE_MOVE = 2;
    public static final int COMPLEX_EXPAND = 3;
    private final Pos cur, target;
    private final int type;

    public OpGoto(Pos cur, Pos target, int rootHeight) {
        this.cur = cur;
        this.target = target;
        if (target.y < rootHeight) {
            this.type = SINGLE_MOVE;
        } else if (cur.y < rootHeight - 1) {
            this.type = COMPLEX_EXPAND;
        } else {
            this.type = SINGLE_EXPAND;
        }
    }

    @Override
    public void run(Robot robot) {
        if (robot == null) {
            return;
        }
        int delay = Simulator.DELAY / 2;
        int downSteps = target.y - cur.y;
        switch (type) {
            case SINGLE_EXPAND -> {
                int times = target.y - cur.y;
                ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, times, delay);
                ActionUtil.clickButton(robot, KeyEvent.VK_SHIFT, KeyEvent.VK_TAB, 10, delay);
            }
            case SINGLE_MOVE -> {
                ActionUtil.clickButton(robot, KeyEvent.VK_LEFT, cur.x, delay);
                if (downSteps > 0) {
                    ActionUtil.clickButton(robot, KeyEvent.VK_DOWN, downSteps, delay);
                } else {
                    ActionUtil.clickButton(robot, KeyEvent.VK_UP, -downSteps, delay);
                }
                ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, 1, delay);
                ActionUtil.clickButton(robot, KeyEvent.VK_UP, 1, delay);
                ActionUtil.clickButton(robot, KeyEvent.VK_SPACE, target.x, delay);
            }
            case COMPLEX_EXPAND -> {
                ActionUtil.clickButton(robot, KeyEvent.VK_LEFT, cur.x, delay);
                ActionUtil.clickButton(robot, KeyEvent.VK_DOWN, downSteps, delay);
                ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, 1, delay);
            }
        }
    }

    @Override
    public String toString() {
        return cur + " -> " + target;
    }
}