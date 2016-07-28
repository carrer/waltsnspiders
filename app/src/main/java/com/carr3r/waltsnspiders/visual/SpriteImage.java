package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;

import com.carr3r.waltsnspiders.GameMap;

/**
 * Created by Neto on 01/11/2015.
 */
public abstract class SpriteImage {

    protected int orientation;
    protected int frames;
    protected int index = 0;
    protected long diff = 0;
    protected float offset = 0;

    public SpriteImage(int frames, int initialOrientation) {
        this.frames = frames;
        this.orientation = initialOrientation;
    }

    public float getOffset() {
        return offset;
    }

    public abstract Bitmap[] getLeftward();

    public abstract Bitmap[] getRightward();

    public abstract Bitmap[] getUpward();

    public abstract Bitmap[] getDownward();

    public int getFrameIndex() {
        long rightNow = System.currentTimeMillis();
        if (rightNow - diff > 100) {
            diff = rightNow;
            index++;
            if (index >= frames)
                index = 0;
        }
        return index;
    }

    public abstract void loadResources();

    public Bitmap getBitmap() {
        switch (orientation) {
            case GameMap.POSITION_RIGHT:
                return getRightward()[getFrameIndex()];
            case GameMap.POSITION_UP:
                return getUpward()[getFrameIndex()];
            case GameMap.POSITION_DOWN:
                return getDownward()[getFrameIndex()];
            default:
                return getLeftward()[getFrameIndex()];
        }
    }

    public void setOrientation(int newOrientation) {
        this.orientation = newOrientation;
    }

}
