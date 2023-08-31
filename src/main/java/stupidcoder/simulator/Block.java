package stupidcoder.simulator;

import java.util.ArrayList;
import java.util.List;

public class Block{
    protected int userId;
    protected final Pos start = new Pos(0,0);
    protected int width, height;
    protected final List<Block> children = new ArrayList<>();
    protected Block parent;
    protected int childId;

    private Block() {
    }

    private Block(int userId, int width, int height) {
        this.userId = userId;
        this.width = width;
        this.height = height;
    }

    private Block(int userId, int width, int height, int x,int offsetY) {
        this.userId = userId;
        this.width = width;
        this.height = height;
        this.start.set(x, offsetY);
    }

    private Block(Block other) {
        this.userId = other.userId;
        this.start.set(other.start);
        this.width = other.width;
        this.height = other.height;
        this.childId = other.childId;
    }

    public static Block of() {
        return new Block();
    }

    public static Block of(int userId) {
        return new Block(userId, 0, 0);
    }

    public static Block of(Block other) {
        return new Block(other);
    }

    public static Block of(int userId, int width, int height) {
        return new Block(userId, width, height);
    }

    public static Block of(int userId, int width, int height,int x, int offsetY) {
        return new Block(userId, width, height,x, offsetY);
    }

    public void shift(int begin, int shiftY) {
        for (int i = begin ; i < children.size() ; i ++) {
            Block child = children.get(i);
            child.start.shift(0, shiftY);
            child.shift(0, shiftY);
        }
    }

    public void createChild(Block child) {
        child.childId = children.size();
        child.parent = this;
        children.add(child);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)[%d, %d]", start.x, start.y, width, height);
    }
}
