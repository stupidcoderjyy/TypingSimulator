package stupidcoder.simulator;

import java.awt.*;

public class ActionUtil {

    public static void clickButton(Robot robot, int keyCode, int times, int delay) {
        for (int i = 0 ; i < times ; i ++) {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            robot.delay(delay);
        }
    }

    public static void clickButton(Robot robot, int pressKey, int key, int times, int delay) {
        robot.keyPress(pressKey);
        for (int i = 0 ; i < times ; i ++) {
            robot.keyPress(key);
            robot.keyRelease(key);
        }
        robot.keyRelease(pressKey);
        robot.delay(delay);
    }

//    public static void pasteString(Robot robot, String string, int delay) {
//        Clipboard cb;
//        while (true) {
//            try {
//                cb = Toolkit.getDefaultToolkit().getSystemClipboard();
//                cb.setContents(new StringSelection(string), null);
//                break;
//            } catch (IllegalStateException ignored) {
//            }
//            try {
//                Thread.sleep(50);
//            } catch (Exception ignored) {
//            }
//        }
//        clickButton(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V, 1, delay);
//    }
}
