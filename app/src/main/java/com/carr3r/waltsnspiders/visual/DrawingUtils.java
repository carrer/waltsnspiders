package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameMap;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wneto on 28/10/2015.
 */
public class DrawingUtils {

    protected static Map<Integer, Paint> paints;
    protected static Map<Integer, Path> triangles;
    protected static Arrow arrows;
    protected static Paint arrowPaint;

    public static void drawCross(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);
        canvas.drawLine(0, 0, Environment.getInstance().getScreenWidth(), Environment.getInstance().getScreenHeight(), p);
        canvas.drawLine(0, Environment.getInstance().getScreenHeight(), Environment.getInstance().getScreenWidth(), 0, p);
    }

    public static void loadTriangles()
    {
        if (paints == null) {
            paints = new HashMap<Integer, Paint>();
            Position bottomRight = new Position(Environment.getInstance().getScreenWidth(), Environment.getInstance().getScreenHeight());
            Position middle = new Position(bottomRight.getX() / 2, bottomRight.getY() / 2);

            Shader shader = new LinearGradient(middle.getX(), 0, middle.getX(), middle.getY() / 2, Color.argb(50, 0xFF, 0xFF, 0xFF), Color.TRANSPARENT, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            paint.setStyle(Paint.Style.FILL);
            paints.put(GameMap.POSITION_UP, paint);

            shader = new LinearGradient(middle.getX(), bottomRight.getY(), middle.getX(), middle.getY() * 1.5f, Color.argb(50, 0xFF, 0xFF, 0xFF), Color.TRANSPARENT, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setShader(shader);
            paint.setStyle(Paint.Style.FILL);
            paints.put(GameMap.POSITION_DOWN, paint);

            shader = new LinearGradient(0, middle.getY(), middle.getX() / 2, middle.getY(), Color.argb(50, 0xFF, 0xFF, 0xFF), Color.TRANSPARENT, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setShader(shader);
            paint.setStyle(Paint.Style.FILL);
            paints.put(GameMap.POSITION_LEFT, paint);

            shader = new LinearGradient(bottomRight.getX(), middle.getY(), middle.getX() * 1.5f, middle.getY(), Color.argb(50, 0xFF, 0xFF, 0xFF), Color.TRANSPARENT, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setShader(shader);
            paint.setStyle(Paint.Style.FILL);
            paints.put(GameMap.POSITION_RIGHT, paint);

            triangles = new HashMap<Integer, Path>();
            Path aux = new Path();
            aux.moveTo(middle.getX(), bottomRight.getY());
            aux.lineTo(0, bottomRight.getY());
            aux.lineTo(middle.getX(), middle.getY());
            aux.lineTo(bottomRight.getX(), bottomRight.getY());
            aux.close();
            triangles.put(GameMap.POSITION_DOWN, aux);

            aux = new Path();
            aux.moveTo(0, 0);
            aux.lineTo(middle.getX(), middle.getY());
            aux.lineTo(bottomRight.getX(), 0);
            aux.close();
            triangles.put(GameMap.POSITION_UP, aux);

            aux = new Path();
            aux.moveTo(0, 0);
            aux.lineTo(middle.getX(), middle.getY());
            aux.lineTo(0, bottomRight.getY());
            aux.close();
            triangles.put(GameMap.POSITION_LEFT, aux);

            aux = new Path();
            aux.moveTo(bottomRight.getX(), 0);
            aux.lineTo(middle.getX(), middle.getY());
            aux.lineTo(bottomRight.getX(), bottomRight.getY());
            aux.close();
            triangles.put(GameMap.POSITION_RIGHT, aux);
        }
    }

    public static void crossScreen(Canvas canvas, int direction) {
        if (direction == 0)
            return;

        loadTriangles();
        canvas.drawPath(triangles.get(direction), paints.get(direction));
    }

    public static void renderPause(Canvas canvas) {
        Environment env = Environment.getInstance();
        Typeface plain = Typeface.createFromAsset(env.getContext().getAssets(), "fonts/atari.ttf");
        Paint paint = new Paint();
        paint.setTypeface(plain);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(72);
        paint.setColor(Color.WHITE);
        String paused = env.getContext().getString(R.string.board_paused);
        canvas.drawText(paused, env.getGameCanvasWidth() / 2, env.getGameCanvasHeight() / 2, paint);
    }

    public static void renderGameOver(Canvas canvas) {
        Environment env = Environment.getInstance();
        Typeface plain = Typeface.createFromAsset(env.getContext().getAssets(), "fonts/atari.ttf");
        Paint paint = new Paint();
        paint.setTypeface(plain);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(36);
        paint.setColor(Color.WHITE);
        String game_over = env.getContext().getString(R.string.board_game_over);
        canvas.drawText(game_over, env.getGameCanvasWidth() / 2, env.getGameCanvasHeight() / 2, paint);
    }

    public static void renderGrassAllOver(Canvas canvas) {
        float bNormal = Environment.getInstance().getGameCanvasBlock();
        float bLarge = bNormal * 1.01f;
        Bitmap grass = GameView.scaleDown(BitmapFactory.decodeResource(Environment.getInstance().getContext().getResources(), R.drawable.wall), bLarge, true);
        Paint defaultPaint = new Paint();
        for (float x = 0; x < Environment.getInstance().getScreenWidth(); x += bNormal) {
            for (float y = 0; y < Environment.getInstance().getScreenHeight(); y += bNormal) {
                canvas.drawBitmap(grass, x, y, defaultPaint);
            }
        }
    }

    public static int randomInt(int min, int max) {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void drawArrows(Canvas canvas) {

        if (arrows == null) {
            arrows = new Arrow();
            arrowPaint = new Paint();
            arrowPaint.setColor(Color.argb(50, 0xFF, 0xFF, 0xFF));
            arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        loadTriangles();
        canvas.drawPath(triangles.get(arrows.getFrameIndex()+1), arrowPaint);
    }
}
