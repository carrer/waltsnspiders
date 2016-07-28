package com.carr3r.waltsnspiders.visual;

/**
 * Created by wneto on 14/10/2015.
 */
public class Coordinate {

    private float x;
    private float y;

    public Coordinate(float vX, float vY) {
        x = vX;
        y = vY;
    }

    public void setX(float vX) {
        x = vX;
    }

    public void setY(float vY) {
        y = vY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getRoundX() {
        return (int) x;
    }

    public int getRoundY() {
        return (int) y;
    }

    public void incY(float v) {
        this.y += v;
    }

    public void incX(float v) {
        this.x += v;
    }

    public String toString() {
        return "(" + getRoundX() + "," + getRoundY() + ")";
    }

    public boolean equals(Coordinate ref) {
        return getX() == ref.getX() && getY() == ref.getY();
    }

    public boolean equalsTrunc(Coordinate ref) {
        return getRoundX() == ref.getRoundX() && getRoundY() == ref.getRoundY();
    }

    public Coordinate clone() {
        return new Coordinate(getX(), getY());
    }

    public int getHash() {
        return (((int) x) << 8 | ((int) y));
    }
}
