package stupidcoder.compile;

$1$
import stupidcoder.runtime.*;
import stupidcoder.util.ArrayUtil;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

import java.util.*;

public class EnvBuilder {
    $2$
    public static final EnvBuilder INSTANCE = new EnvBuilder();
    private final Env env = Env.INSTANCE;
    private final Map<String, Integer> nameToFuncId = new HashMap<>();
    private final List<Function> functions = new ArrayList<>();
    private final Stack<List<Integer>> breakStack = new Stack<>();
    private final Stack<List<Integer>> continueStack = new Stack<>();
    private final Stack<Integer> marks = new Stack<>();

    private Map<String, Field> nameToFields = new HashMap<>();
    private List<Byte> tempOpCodes;
    private int writePos = 0;
    private int maxFuncId = 0;
    private int maxVarId = 0;
    private int curFunc;
    private String curFuncName;
    private boolean hasReturnVal;
    $$
    $10$

    public EnvBuilder() {
        $40$
        registerNativeFunction("println", new FuncPrintln());
        registerNativeFunction("rand", new FuncRand());
        $$
    }

    public static Env load(String scriptPath) {
        $41$
        try {
            new SyntaxAnalyzer().run(new Lexer(CompilerInput.fromFile(scriptPath)));
            Compiler.run(scriptPath);
        } catch (CompileException e) {
            e.printStackTrace();
        }
        return Env.INSTANCE;
        $$
    }

    public int getCodeSize() {
        return tempOpCodes.size();
    }

    public Field getField(String name) {
        return nameToFields.get(name);
    }

    private void registerNativeFunction(String name, Function f) {
        $11$
        nameToFuncId.compute(name, (funcName, funcId) -> {
            $12$
            if (funcId == null) {
                functions.add(f);
                funcId = maxFuncId++;
            } else {
                functions.set(funcId, f);
            }
            return funcId;
            $$
        });
        $$
    }

    public void enterLoop() {
        $13$
        breakStack.push(new ArrayList<>());
        continueStack.push(new ArrayList<>());
        $$
    }

    public void leaveLoop(int condBegin, int loopEnd) {
        $14$
        for (int pos : breakStack.pop()) {
            writePos = pos;
            writeInt(loopEnd);
        }
        for (int pos : continueStack.pop()) {
            writePos = pos;
            writeInt(condBegin);
        }
        moveToEnd();
        $$
    }

    public void pushBreak() {
        $15$
        writeCode(Codes.GOTO);
        breakStack.peek().add(writePos);
        writeInt(0);
        $$
    }

    public void pushContinue() {
        $16$
        writeCode(Codes.GOTO);
        continueStack.peek().add(writePos);
        writeInt(0);
        $$
    }

    public void markAndSkip(int count) {
        $17$
        marks.push(writePos);
        for (int i = 0; i < count; i++) {
            tempOpCodes.add((byte) 0);
        }
        writePos += count;
        $$
    }

    public void mark() {
        $18$
        marks.push(writePos);
        $$
    }

    public int popMark() {
        $19$
        return marks.pop();
        $$
    }

    public void retract() {
        $20$
        writePos = marks.pop();
        $$
    }

    public void moveToEnd() {
        $21$
        writePos = tempOpCodes.size();
        $$
    }

    public Field defField(String name, int type) {
        $22$
        Field f = new Field(name, type, maxVarId++, false);
        nameToFields.put(name, f);
        return f;
        $$
    }

    public Field defArrField(String name, int depth) {
        $23$
        Field f = new Field(name, depth, maxVarId++, true);
        nameToFields.put(name, f);
        return f;
        $$
    }

    public void beginFunction(String name, boolean hasReturnVal) {
        $24$
        curFuncName = name;
        tempOpCodes = new ArrayList<>();
        nameToFields = new HashMap<>();
        maxVarId = 0;
        writePos = 0;
        this.hasReturnVal = hasReturnVal;
        nameToFuncId.compute(name, (n, funcId) -> {
            $25$
            if (funcId == null) {
                curFunc = maxFuncId++;
                return curFunc;
            }
            curFunc = funcId;
            return curFunc;
            $$
        });
        $$
    }

    public void endFunction(int argCount) {
        $30$
        if (tempOpCodes.get(tempOpCodes.size() - 1) != Codes.RETURN) {
            writeReturn();
        }
        int len = tempOpCodes.size();
        byte[] opCodes = new byte[len];
        for (int i = 0; i < len; i ++) {
            opCodes[i] = tempOpCodes.get(i);
        }
        ArrayUtil.resize(functions, curFunc + 1);
        functions.set(curFunc, new FuncDefined(curFuncName, opCodes, maxVarId, argCount, hasReturnVal));
        printByteCodes();
        System.out.println();
        $$
    }

    public void finish() {
        $31$
        env.functions = new Function[maxFuncId];
        env.mainFunc = functions.get(nameToFuncId.get("main"));
        nameToFuncId.forEach((name, funcId) -> env.functions[funcId] = functions.get(funcId));
        $$
    }
    $$
    $3$

    public void writeConstInt(int val) {
        writeCode(Codes.CONST_I);
        writeInt(val);
    }

    public void writeDuplicate() {
        writeCode(Codes.DUP);
    }

    public void writeAdd() {
        writeCode(Codes.ADD);
    }

    public void writeMinus() {
        writeCode(Codes.MINUS);
    }

    public void writeStore(int constPoolId) {
        writeCode(Codes.STORE);
        writeInt(constPoolId);
    }

    public void writeLoad(int constPoolId) {
        writeCode(Codes.LOAD);
        writeInt(constPoolId);
    }

    public void writeInvoke(String funcName) {
        writeCode(Codes.INVOKE);
        writeInt(nameToFuncId.computeIfAbsent(funcName, name -> maxFuncId++));
    }

    public void writeReturn() {
        writeCode(Codes.RETURN);
    }

    public void writeGoTo(int target) {
        writeCode(Codes.GOTO);
        writeInt(target);
    }

    public void writeIfLessEquals(int target) {
        writeCode(Codes.IF_LESS_EQUALS);
        writeInt(target);
    }

    public void writeIfGreaterEquals(int target) {
        writeCode(Codes.IF_GREATER_EQUALS);
        writeInt(target);
    }

    public void writeIfEquals(int target) {
        writeCode(Codes.IF_EQUALS);
        writeInt(target);
    }

    public void writeArrElement() {
        writeCode(Codes.ARR_ELEMENT);
    }

    public void writeArrSetConst() {
        writeCode(Codes.ARR_SET_CONST);
    }

    public void writeArrSet() {
        writeCode(Codes.ARR_SET);
    }

    public void writeMultiNewArray(int dimensionCount) {
        writeCode(Codes.MULTI_NEW_ARRAY);
        writeInt(dimensionCount);
    }

    public void writeNewArrayInt(int size) {
        writeCode(Codes.NEW_ARRAY_I);
        writeInt(size);
    }

    public void writeNewArrayArray(int size) {
        writeCode(Codes.NEW_ARRAY_A);
        writeInt(size);
    }

    public void writeMultiply() {
        writeCode(Codes.MULTIPLY);
    }

    public void writeDivide() {
        writeCode(Codes.DIVIDE);
    }

    private void writeCode(byte code) {
        if (writePos == tempOpCodes.size()) {
            tempOpCodes.add(writePos++, code);
        } else {
            tempOpCodes.set(writePos++, code);
        }
    }

    private void writeInt(int val) {
        if (writePos == tempOpCodes.size()) {
            tempOpCodes.add(writePos++, (byte)(val >> 24));
            tempOpCodes.add(writePos++, (byte)(val >> 16));
            tempOpCodes.add(writePos++, (byte)(val >> 8));
            tempOpCodes.add(writePos++, (byte)val);
        } else {
            tempOpCodes.set(writePos++, (byte)(val >> 24));
            tempOpCodes.set(writePos++, (byte)(val >> 16));
            tempOpCodes.set(writePos++, (byte)(val >> 8));
            tempOpCodes.set(writePos++, (byte)val);
        }
    }
    $$
    $4$

    private void printByteCodes() {
        $5$
        int pos = 0;
        while (pos < tempOpCodes.size()) {
            $6$
            switch (tempOpCodes.get(pos)) {
                $7$
                case Codes.CONST_I -> {
                    print(pos++, "const_i");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.DUP -> {
                    print(pos++, "dup");
                    System.out.println();
                }
                case Codes.LOAD -> {
                    print(pos++, "load");
                    System.out.println("#" + readInt(pos));
                    pos += 4;
                }
                case Codes.STORE -> {
                    print(pos++, "store");
                    System.out.println("#" + readInt(pos));
                    pos += 4;
                }
                case Codes.INVOKE -> {
                    print(pos++, "invoke");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.ADD -> {
                    print(pos++, "add");
                    System.out.println();
                }
                case Codes.MULTI_NEW_ARRAY -> {
                    print(pos++, "multi_new_arr");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.NEW_ARRAY_I -> {
                    print(pos++, "new_arr_i");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.ARR_SET_CONST -> {
                    print(pos++, "arr_set_const");
                    System.out.println();
                }
                case Codes.ARR_SET -> {
                    print(pos++, "arr_set");
                    System.out.println();
                }
                case Codes.NEW_ARRAY_A -> {
                    print(pos++, "new_arr_a");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.GOTO -> {
                    print(pos++, "goto");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.ARR_ELEMENT -> {
                    print(pos++, "arr_element");
                    System.out.println();
                }
                case Codes.RETURN -> {
                    print(pos++, "return");
                    System.out.println();
                }
                case Codes.IF_LESS_EQUALS -> {
                    print(pos++, "if_le");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.IF_GREATER_EQUALS -> {
                    print(pos++, "if_ge");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.IF_EQUALS -> {
                    print(pos++, "if_eq");
                    System.out.println(readInt(pos));
                    pos += 4;
                }
                case Codes.MINUS -> {
                    print(pos++, "minus");
                    System.out.println();
                }
                case Codes.MULTIPLY -> {
                    print(pos++, "multiply");
                    System.out.println();
                }
                case Codes.DIVIDE -> {
                    print(pos++, "divide");
                    System.out.println();
                }
                $$
            }
            $$
        }
        $$
    }

    private void print(int pos, String code) {
        String prefix = pos + ":";
        System.out.print(prefix + " ".repeat(4 - prefix.length())
                + code + " ".repeat(15 - code.length()));
    }

    private int readInt(int pos) {
        return (tempOpCodes.get(pos++) & 0xFF) << 24 |
                (tempOpCodes.get(pos++) & 0xFF) << 16 |
                (tempOpCodes.get(pos++) & 0xFF) << 8 |
                (tempOpCodes.get(pos) & 0xFF);
    }
    $$
}
$$