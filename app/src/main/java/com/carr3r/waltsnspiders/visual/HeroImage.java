package com.carr3r.waltsnspiders.visual;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;

/**
 * Created by Neto on 01/11/2015.
 */
public class HeroImage extends SpriteImage {

    protected Bitmap up[];
    protected Bitmap down[];
    protected Bitmap left[];
    protected Bitmap right[];

    public HeroImage() {
        super(8, 0);
        loadResources();
    }

    @Override
    public Bitmap[] getLeftward() {
        return left;
    }

    @Override
    public Bitmap[] getRightward() {
        return right;
    }

    @Override
    public Bitmap[] getUpward() {
        return up;
    }

    @Override
    public Bitmap[] getDownward() {
        return down;
    }

    @Override
    public void loadResources() {
        up = new Bitmap[8];
        down = new Bitmap[8];
        left = new Bitmap[8];
        right = new Bitmap[8];
        Resources r = Environment.getInstance().getContext().getResources();
        float b = Environment.getInstance().getGameCanvasBlock();

        up[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up1), b, true);
        up[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up2), b, true);
        up[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up3), b, true);
        up[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up4), b, true);
        up[4] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up5), b, true);
        up[5] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up6), b, true);
        up[6] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up7), b, true);
        up[7] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_up8), b, true);
        down[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down1), b, true);
        down[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down2), b, true);
        down[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down3), b, true);
        down[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down4), b, true);
        down[4] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down5), b, true);
        down[5] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down6), b, true);
        down[6] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down7), b, true);
        down[7] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_down8), b, true);
        left[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left1), b, true);
        left[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left2), b, true);
        left[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left3), b, true);
        left[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left4), b, true);
        left[4] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left5), b, true);
        left[5] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left6), b, true);
        left[6] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left7), b, true);
        left[7] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_left8), b, true);
        right[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right1), b, true);
        right[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right2), b, true);
        right[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right3), b, true);
        right[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right4), b, true);
        right[4] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right5), b, true);
        right[5] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right6), b, true);
        right[6] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right7), b, true);
        right[7] = GameView.scaleDown(BitmapFactory.decodeResource(r, R.drawable.walts_right8), b, true);
    }

}
