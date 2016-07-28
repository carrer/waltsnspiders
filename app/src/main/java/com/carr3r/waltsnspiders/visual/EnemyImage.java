package com.carr3r.waltsnspiders.visual;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;

/**
 * Created by Neto on 01/11/2015.
 */
public class EnemyImage extends SpriteImage {

    protected static String TAG = "EnemyImage";
    protected Bitmap up[];
    protected Bitmap down[];
    protected Bitmap left[];
    protected Bitmap right[];
    protected int enemyIndex;

    public EnemyImage(int index) {
        super(4, 0);
        this.enemyIndex = index;
        loadResources();
    }

    @Override
    public int getFrameIndex() {
        long rightNow = System.currentTimeMillis();
        if (rightNow - diff > 100) {
            diff = rightNow;
            index++;
        }
        if (index >= frames) {
            switch (index) {
                case 4:
                    return 2;
                case 5:
                    return 1;
                default:
                    index = 0;
                    break;
            }
        }
        return index;
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
        up = new Bitmap[4];
        down = new Bitmap[4];
        left = new Bitmap[4];
        right = new Bitmap[4];
        Resources r = Environment.getInstance().getContext().getResources();
        float b = Environment.getInstance().getGameCanvasBlock();
        String p = Environment.getInstance().getContext().getPackageName();
        up[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_up_a", "drawable", p)), b, true);
        up[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_up_b", "drawable", p)), b, true);
        up[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_up_c", "drawable", p)), b, true);
        up[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_up_d", "drawable", p)), b, true);
        down[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_down_a", "drawable", p)), b, true);
        down[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_down_b", "drawable", p)), b, true);
        down[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_down_c", "drawable", p)), b, true);
        down[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_down_d", "drawable", p)), b, true);
        left[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_left_a", "drawable", p)), b, true);
        left[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_left_b", "drawable", p)), b, true);
        left[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_left_c", "drawable", p)), b, true);
        left[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_left_d", "drawable", p)), b, true);
        right[0] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_right_a", "drawable", p)), b, true);
        right[1] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_right_b", "drawable", p)), b, true);
        right[2] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_right_c", "drawable", p)), b, true);
        right[3] = GameView.scaleDown(BitmapFactory.decodeResource(r, r.getIdentifier("enemy" + enemyIndex + "_right_d", "drawable", p)), b, true);
    }

}
