package com.stupidcoder.coder.core;

public class Pos {
    public int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(Pos other) {
        this(other.x, other.y);
    }

    public Pos set(Pos other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public Pos set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Pos shift(Pos delta) {
        this.x += delta.x;
        this.y += delta.y;
        return this;
    }

    public Pos shift(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos p) {
            return x == p.x && y == p.y;
        }
        return false;
    }
}
