package com.carr3r.waltsnspiders.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.visual.DrawingUtils;

/**
 * Created by Neto on 03/11/2015.
 */
public class GameRenderer {

    protected Canvas backgroundCanvas;
    protected Bitmap backgroundCanvasBitmap;
    protected Canvas boardCanvas;
    protected Bitmap boardCanvasBitmap;
    protected Paint emptyPaint;


    public GameRenderer() {
        backgroundCanvasBitmap = Bitmap.createBitmap((int) Environment.getInstance().getScreenWidth(), (int) Environment.getInstance().getScreenHeight(), Bitmap.Config.ARGB_8888);
        backgroundCanvas = new Canvas(backgroundCanvasBitmap);

        boardCanvasBitmap = Bitmap.createBitmap((int) Environment.getInstance().getGameCanvasWidth(), (int) Environment.getInstance().getGameCanvasHeight(), Bitmap.Config.ARGB_8888);
        boardCanvas = new Canvas(boardCanvasBitmap);

        DrawingUtils.renderGrassAllOver(backgroundCanvas);
        emptyPaint = new Paint();
    }

    public void draw(Canvas canvas) {

    }

}
