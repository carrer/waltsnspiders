package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;

/**
 * Created by wneto on 03/11/2015.
 */
public class Decoration {

    private Bitmap tree;
    private Paint defaultPaint;
    private Paint blackPaint;
    private Paint framePaint;

    private Position treeLeftSide;
    private Position treeRightSide;
    private Position pointsLabel;
    private Position levelLabel;
    private Position pointsValue;
    private Position levelValue;
    private RectF leftFrame;
    private RectF rightFrame;
    private String levelLabelString;
    private String pointsLabelString;

    public Decoration() {
        Environment env = Environment.getInstance();
        float b = env.getGameCanvasBlock();
        float margin = b / 3;

        defaultPaint = new Paint();
        framePaint = new Paint();
        framePaint.setStrokeWidth(2);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setColor(Color.rgb(44, 99, 18));

        Typeface plain = Typeface.createFromAsset(env.getContext().getAssets(), "fonts/anpasia.ttf");
        blackPaint = new Paint();
        blackPaint.setTypeface(plain);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(22);
        blackPaint.setTextAlign(Paint.Align.CENTER);
        blackPaint.setStrokeWidth(1);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        tree = GameView.scaleDown(BitmapFactory.decodeResource(env.getContext().getResources(), R.drawable.tree), Math.round(b * 3), true);
        levelLabelString = env.getContext().getString(R.string.level);
        pointsLabelString = env.getContext().getString(R.string.points);

        treeLeftSide = Board.coordinateToPosition(new Coordinate(2, 8));
        treeLeftSide.incX(margin);
        treeLeftSide.incY(b / 2);

        treeRightSide = Board.coordinateToPosition(new Coordinate(25, 8));
        treeRightSide.incX(margin);
        treeRightSide.incY(b / 2);

        /*
        Position pos = Board.coordinateToPosition(new Coordinate(6, 17));
        leftFrame = new RectF();
        leftFrame.set(pos.getX() + margin, pos.getY() + margin, pos.getX() + b * 4 - margin, pos.getY() + b * 3 - margin);

        pos = Board.coordinateToPosition(new Coordinate(20, 17));
        rightFrame = new RectF();
        rightFrame.set(pos.getX() + margin, pos.getY() + margin, pos.getX() + b * 4 - margin, pos.getY() + b * 3 - margin);
        */

        levelLabel = Board.coordinateToPosition(new Coordinate(15, 5));
        levelLabel.incY(margin);

        pointsLabel = Board.coordinateToPosition(new Coordinate(15, 14));
        pointsLabel.incY(margin);

    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(tree, treeLeftSide.getX(), treeLeftSide.getY(), defaultPaint);
        canvas.drawBitmap(tree, treeRightSide.getX(), treeRightSide.getY(), defaultPaint);

//        canvas.drawRoundRect(leftFrame, 8, 8, framePaint);
//        canvas.drawRoundRect(rightFrame, 8, 8, framePaint);

        canvas.drawText(levelLabelString, levelLabel.getX(), levelLabel.getY(), blackPaint);
        canvas.drawText(pointsLabelString, pointsLabel.getX(), pointsLabel.getY(), blackPaint);
    }

}
