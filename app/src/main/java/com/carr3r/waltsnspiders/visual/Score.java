package com.carr3r.waltsnspiders.visual;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.carr3r.waltsnspiders.Environment;

/**
 * Created by Neto on 28/10/2015.
 */
public class Score {

    protected int points = 0;
    protected int level = 1;
    protected Position levelLabel;
    protected Position pointsLabel;
    protected Paint fontPaint;

    public Score(boolean forDrawing) {
        if (forDrawing) {
            Environment env = Environment.getInstance();
            float b = env.getGameCanvasBlock();
            Typeface plain = Typeface.createFromAsset(env.getContext().getAssets(), "fonts/anpasia.ttf");
            fontPaint = new Paint();
            fontPaint.setTypeface(plain);
            fontPaint.setTextAlign(Paint.Align.CENTER);
            fontPaint.setColor(Color.BLACK);
            fontPaint.setTextSize(22);
            fontPaint.setStrokeWidth(1);
            fontPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            pointsLabel = Board.coordinateToPosition(new Coordinate(15, 15));
            pointsLabel.incY(b / 3);

            levelLabel = Board.coordinateToPosition(new Coordinate(15, 6));
            levelLabel.incY(b / 3);
        }
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public void addPoints(int value) {
        points += value;
    }

    public void incLevel() {
        level++;
    }

    public void setLevel(int v) {
        level = v;
    }

    public void setPoints(int v) {
        points = v;
    }

    public int getRanking() {
        return 0;
    }

    public void render(Canvas canvas) {
        canvas.drawText(String.valueOf(getPoints()), pointsLabel.getX(), pointsLabel.getY(), fontPaint);
        canvas.drawText(String.valueOf(getLevel()), levelLabel.getX(), levelLabel.getY(), fontPaint);
    }

    public void reset() {
        points = 0;
        level = 1;
    }
}
