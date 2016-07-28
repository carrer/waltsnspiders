package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameMap;

/**
 * Created by wneto on 14/10/2015.
 */
public class Hero extends MovingUnit {
    public static final String TAG = "Hero";
    protected SpriteImage img;
    protected SpriteImage carry;
    protected int jewel = 0;
    protected boolean carrying = false;

    protected float b;
    protected float half;

    public Hero(Coordinate initialCoordinate, int initialOrientation) {
        super(initialCoordinate, initialOrientation);
        b = Environment.getInstance().getGameCanvasBlock();
        half = b / 2;
        if (Environment.getInstance().getContext() != null) {
            img = new HeroImage();
            img.setOrientation(initialOrientation);
        }
        setOrientation(initialOrientation);
    }


    public boolean isCarrying() {
        return carrying;
    }

    public int getJewel() {
        return jewel;
    }

    public void dropJewel() {
        if (carrying) {
            carrying = false;
            carry = null;
            img.loadResources();
            jewel = 0;
        }
    }

    public void dragJewel(int jewelIndex) {
        jewel = jewelIndex;
        switch (jewelIndex) {
            case 1:
                carry = new JewelA(half);
                break;
            case 2:
                carry = new JewelB(half);
                break;
            case 3:
                carry = new JewelC(half);
                break;
            case 4:
                carry = new JewelD(half);
                break;
        }
        carrying = true;
    }

    @Override
    public Bitmap getCurrentBitmap() {

        if (carrying) {
            Canvas tmp = new Canvas(img.getBitmap());
            switch (orientation) {
                case GameMap.POSITION_RIGHT:
                    tmp.drawBitmap(carry.getBitmap(), half, half, paint);
                    break;
                case GameMap.POSITION_LEFT:
                    tmp.drawBitmap(carry.getBitmap(), 0, half, paint);
                    break;
                case GameMap.POSITION_UP:
                    tmp.drawBitmap(carry.getBitmap(), 0, 0, paint);
                    break;
                case GameMap.POSITION_DOWN:
                    tmp.drawBitmap(carry.getBitmap(), 0, half, paint);
                    break;
            }
        }
        return img.getBitmap();
    }

    @Override
    public void rotateBitmap(int angle) {
        switch (angle) {
            default:
                img.setOrientation(GameMap.POSITION_RIGHT);
                break;
            case 90:
                img.setOrientation(GameMap.POSITION_DOWN);
                break;
            case 180:
                img.setOrientation(GameMap.POSITION_LEFT);
                break;
            case 270:
                img.setOrientation(GameMap.POSITION_UP);
                break;
        }
    }
}
