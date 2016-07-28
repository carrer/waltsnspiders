package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;

/**
 * Created by Neto on 01/11/2015.
 */
public class Fountain extends SpriteImage {

    protected Bitmap img[];

    public Fountain() {
        super(5, 0);
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
        float size = Environment.getInstance().getGameCanvasBlock() * 2;
        img = new Bitmap[5];
        img[0] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.fountain1), size, true);
        img[1] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.fountain2), size, true);
        img[2] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.fountain3), size, true);
        img[3] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.fountain4), size, true);
        img[4] = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.fountain5), size, true);
    }

}
