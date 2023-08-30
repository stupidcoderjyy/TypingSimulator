package com.stupidcoder.coder.core.operations;

import com.stupidcoder.coder.core.ActionUtil;
import com.stupidcoder.coder.core.IRobotAction;
import com.stupidcoder.coder.core.OperationHandler;
import com.stupidcoder.coder.core.Pos;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OpGoto implements IRobotAction {
    private static int maxY = 0;
    private final Pos cur, target;
    private final boolean expandLine;

    public OpGoto(Pos cur, Pos target, boolean expandLine) {
        this.expandLine = expandLine;
        this.cur = cur;
        this.target = target;
    }

    @Override
    public void run(Robot robot) {
        int delay = OperationHandler.DELAY / 2;
        int downSteps = target.y - cur.y;
        if (expandLine) {
            int times = target.y - cur.y;
            ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, times, delay);
            ActionUtil.clickButton(robot, KeyEvent.VK_SHIFT, KeyEvent.VK_TAB, 10, 5);
        } else {
            ActionUtil.clickButton(robot, KeyEvent.VK_LEFT, cur.x, delay);
            if (downSteps > 0) {
                ActionUtil.clickButton(robot, KeyEvent.VK_DOWN, downSteps, delay);
            } else {
                ActionUtil.clickButton(robot, KeyEvent.VK_UP, -downSteps,delay);
            }
            ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, 1, delay);
            ActionUtil.clickButton(robot, KeyEvent.VK_UP, 1, delay);
            ActionUtil.clickButton(robot, KeyEvent.VK_SPACE, target.x, delay);
        }
        maxY = Math.max(maxY, target.y);
    }
}
