package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.carr3r.waltsnspiders.Environment;

/**
 * Created by Neto on 01/11/2015.
 */
public class Arrow extends SpriteImage {

    public Arrow() {
        super(4, 0);
        loadResources();
    }

    @Override
    public Bitmap[] getLeftward() {
        return null;
    }

    @Override
    public Bitmap[] getRightward() {
        return null;
    }

    @Override
    public Bitmap[] getUpward() {
        return null;
    }

    @Override
    public Bitmap[] getDownward() {
        return null;
    }

    @Override
    public int getFrameIndex() {
        long rightNow = System.currentTimeMillis();
        if (rightNow - diff > 250) {
            diff = rightNow;
            index++;
            if (index >= frames)
                index = 0;
        }
        return index;
    }

    public Bitmap getBitmap() { return null; }

    @Override
    public void loadResources() {
    }

}
