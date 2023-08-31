package stupidcoder.simulator.operations;

import stupidcoder.simulator.ActionUtil;
import stupidcoder.simulator.IRobotAction;
import stupidcoder.simulator.Simulator;
import stupidcoder.util.input.IInput;
import stupidcoder.util.input.StringInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class OpWriteCode implements IRobotAction {
    private final List<String> slices;

    public OpWriteCode(List<String> slices) {
        this.slices = slices;
    }

    @Override
    public void run(Robot robot) {
        if (slices.isEmpty() || robot == null) {
            return;
        }
        for (int i = 0 ; i < slices.size() - 1 ; i ++) {
            writeSlice(slices.get(i), robot);
            ActionUtil.clickButton(robot, KeyEvent.VK_ENTER, 1, Simulator.DELAY);
            ActionUtil.clickButton(robot, KeyEvent.VK_SHIFT, KeyEvent.VK_TAB, 10, Simulator.DELAY);
        }
        writeSlice(slices.get(slices.size() - 1), robot);
    }

    private void writeSlice(String slice, Robot robot) {
        System.out.printf("\033[33m>> %s\n\33[0m", slice);
        IInput input = new StringInput(slice);
        int spaceCount = 0;
        while (input.available()) {
            int b = input.read();
            if (b < 0) {
                input.retract();
                System.err.println("skipping non ascii char: " + input.readUtfChar());
                actions['?'].write(b,robot);
                continue;
            }
            if (b == ' ') {
                spaceCount++;
                if (spaceCount == 4) {
                    ActionUtil.clickButton(robot, KeyEvent.VK_TAB, 1, Simulator.DELAY);
                    spaceCount = 0;
                }
            } else {
                if (spaceCount > 0) {
                    ActionUtil.clickButton(robot, KeyEvent.VK_SPACE, spaceCount, Simulator.DELAY);
                    spaceCount = 0;
                }
                actions[b].write(b, robot);
            }
        }
    }
    
    private interface ICharWriter {
        void write(int ascii, Robot robot);
    }

    private static final ICharWriter NULL = (i, robot) -> {};

    private static final ICharWriter DEFAULT =
            (i, robot) -> ActionUtil.clickButton(robot, i, 1, Simulator.DELAY >> 2);

    private static final ICharWriter UPPER = (i, r) ->
            ActionUtil.clickButton(r, KeyEvent.VK_SHIFT,(i - 'A') + KeyEvent.VK_A, 1, Simulator.DELAY >> 2);

    private static final ICharWriter LOWER =
            (i, r) -> ActionUtil.clickButton(r, (i - 'a') + KeyEvent.VK_A, 1, Simulator.DELAY >> 2);

    private static ICharWriter[] actions = new ICharWriter[]{
            NULL,                  /* 00 (NUL) */
            NULL,                  /* 01 (SOH) */
            NULL,                  /* 02 (STX) */
            NULL,                  /* 03 (ETX) */
            NULL,                  /* 04 (EOT) */
            NULL,                  /* 05 (ENQ) */
            NULL,                  /* 06 (ACK) */
            NULL,                  /* 07 (BEL) */
            NULL,                  /* 08 (BS)  */
            NULL,                  /* 09 (HT)  */
            NULL,                  /* 0A (LF)  */
            NULL,                  /* 0B (VT)  */
            NULL,                   /* 0C (FF)  */
            NULL,            /* 0D (CR)  */
            NULL,                  /* 0E (SI)  */
            NULL,                  /* 0F (SO)  */
            NULL,                  /* 10 (DLE) */
            NULL,                  /* 11 (DC1) */
            NULL,                  /* 12 (DC2) */
            NULL,                  /* 13 (DC3) */
            NULL,                  /* 14 (DC4) */
            NULL,                  /* 15 (NAK) */
            NULL,                  /* 16 (SYN) */
            NULL,                  /* 17 (ETB) */
            NULL,                  /* 18 (CAN) */
            NULL,                  /* 19 (EM)  */
            NULL,                  /* 1A (SUB) */
            NULL,                  /* 1B (ESC) */
            NULL,                  /* 1C (FS)  */
            NULL,                  /* 1D (GS)  */
            NULL,                  /* 1E (RS)  */
            NULL,                  /* 1F (US)  */
            DEFAULT,               /* 20 SPACE */
            shifted(KeyEvent.VK_1),
            shifted(KeyEvent.VK_QUOTE),
            shifted(KeyEvent.VK_3),
            shifted(KeyEvent.VK_4),
            shifted(KeyEvent.VK_5),
            shifted(KeyEvent.VK_7),
            normal(KeyEvent.VK_QUOTE),
            shifted(KeyEvent.VK_9),
            shifted(KeyEvent.VK_0),
            shifted(KeyEvent.VK_8),
            shifted(KeyEvent.VK_EQUALS),
            normal(KeyEvent.VK_COMMA),
            normal(KeyEvent.VK_MINUS),
            normal(KeyEvent.VK_PERIOD),
            normal(KeyEvent.VK_SLASH),
            DEFAULT,            /* 30 0     */
            DEFAULT,            /* 31 1     */
            DEFAULT,            /* 32 2     */
            DEFAULT,            /* 33 3     */
            DEFAULT,            /* 34 4     */
            DEFAULT,            /* 35 5     */
            DEFAULT,            /* 36 6     */
            DEFAULT,            /* 37 7     */
            DEFAULT,            /* 38 8     */
            DEFAULT,            /* 39 9     */
            shifted(KeyEvent.VK_SEMICOLON),
            normal(KeyEvent.VK_SEMICOLON),
            shifted(KeyEvent.VK_COMMA),
            normal(KeyEvent.VK_EQUALS),
            shifted(KeyEvent.VK_PERIOD),
            shifted(KeyEvent.VK_SLASH),
            shifted(KeyEvent.VK_2),
            UPPER,           /* 41 A     */
            UPPER,           /* 42 B     */
            UPPER,           /* 43 C     */
            UPPER,           /* 44 D     */
            UPPER,           /* 45 E     */
            UPPER,           /* 46 F     */
            UPPER,               /* 47 G     */
            UPPER,               /* 48 H     */
            UPPER,               /* 49 I     */
            UPPER,               /* 4A J     */
            UPPER,               /* 4B K     */
            UPPER,               /* 4C L     */
            UPPER,               /* 4D M     */
            UPPER,               /* 4E N     */
            UPPER,               /* 4F O     */
            UPPER,               /* 50 P     */
            UPPER,               /* 51 Q     */
            UPPER,               /* 52 R     */
            UPPER,               /* 53 S     */
            UPPER,               /* 54 T     */
            UPPER,               /* 55 U     */
            UPPER,               /* 56 V     */
            UPPER,               /* 57 W     */
            UPPER,               /* 58 X     */
            UPPER,               /* 59 Y     */
            UPPER,               /* 5A Z     */
            normal(KeyEvent.VK_OPEN_BRACKET),                  /* 5B [     */
            normal(KeyEvent.VK_BACK_SLASH),                  /* 5C \     */
            normal(KeyEvent.VK_CLOSE_BRACKET),                  /* 5D ]     */
            shifted(KeyEvent.VK_6),                  /* 5E ^     */
            shifted(KeyEvent.VK_MINUS),            /* 5F _     */
            normal(KeyEvent.VK_BACK_QUOTE),                  /* 60 `     */
            LOWER,           /* 61 a     */
            LOWER,           /* 62 b     */
            LOWER,           /* 63 c     */
            LOWER,           /* 64 d     */
            LOWER,           /* 65 e     */
            LOWER,           /* 66 f     */
            LOWER,               /* 67 g     */
            LOWER,               /* 68 h     */
            LOWER,               /* 69 i     */
            LOWER,               /* 6A j     */
            LOWER,               /* 6B k     */
            LOWER,               /* 6C l     */
            LOWER,               /* 6D m     */
            LOWER,               /* 6E n     */
            LOWER,               /* 6F o     */
            LOWER,               /* 70 p     */
            LOWER,               /* 71 q     */
            LOWER,               /* 72 r     */
            LOWER,               /* 73 s     */
            LOWER,               /* 74 t     */
            LOWER,               /* 75 u     */
            LOWER,               /* 76 v     */
            LOWER,               /* 77 w     */
            LOWER,               /* 78 x     */
            LOWER,               /* 79 y     */
            LOWER,               /* 7A z     */
            shifted(KeyEvent.VK_OPEN_BRACKET),                  /* 7B {     */
            shifted(KeyEvent.VK_BACK_SLASH),                  /* 7C |     */
            shifted(KeyEvent.VK_CLOSE_BRACKET),                  /* 7D }     */
            shifted(KeyEvent.VK_BACK_QUOTE),                  /* 7E ~     */
            NULL,                  /* 7F (DEL) */
    };
    
    private static ICharWriter shifted(int keyCode) {
        return (i, r) -> ActionUtil.clickButton(r,KeyEvent.VK_SHIFT , keyCode, 1, Simulator.DELAY >> 2);
    }
    
    private static ICharWriter normal(int keyCode) {
        return (i, r) -> ActionUtil.clickButton(r, keyCode, 1, Simulator.DELAY >> 2);
    }

    @Override
    public String toString() {
        return String.format("writing %d slices", slices.size());
    }
}
