package com.carr3r.waltsnspiders.visual;

/**
 * Created by wneto on 14/10/2015.
 */
public class Position {

    private float x;
    private float y;

    public Position(float vX, float vY) {
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

    public void incY(float v) {
        y += v;
    }

    public void incX(float v) {
        x += v;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Position clone() {
        return new Position(getX(), getY());
    }

}
