package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;


/**
 * Created by Neto on 01/11/2015.
 */
public class JewelB extends SpriteImage {

    protected Bitmap img[];
    protected float size;

    public JewelB(float renderSize) {
        super(4, 0);
        size = renderSize;
        loadResources();

    }

    @Override
    public Bitmap[] getLeftward() {
        return img;
    }

    @Override
    public Bitmap[] getRightward() {
        return img;
    }

    @Override
    public Bitmap[] getUpward() {
        return img;
    }

    @Override
    public Bitmap[] getDownward() {
        return img;
    }

    @Override
    public void loadResources() {
        img = new Bitmap[4];
        img[0] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.jewel2_p1), size, true);
        img[1] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.jewel2_p2), size, true);
        img[2] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.jewel2_p3), size, true);
        img[3] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.jewel2_p4), size, true);

    }

}
