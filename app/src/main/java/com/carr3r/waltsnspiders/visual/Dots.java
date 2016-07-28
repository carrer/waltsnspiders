package com.carr3r.waltsnspiders.visual;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameMap;

import java.util.Collections;
import java.util.Vector;

/**
 * Created by Neto on 10/17/2015.
 */
public class Dots {

    public static final String TAG = "Dots";
    private GameMap map;
    private SpriteImage coin;
    private SpriteImage jewel1;
    private SpriteImage jewel2;
    private SpriteImage jewel3;
    private SpriteImage jewel4;
    private SpriteImage fountain;
    private Position fountainPosition;
    private Paint defaultPaint;

    private Coordinate letterAcoordinate;
    private Position letterAPosition;
    private Coordinate letterBcoordinate;
    private Position letterBPosition;
    private Coordinate letterCcoordinate;
    private Position letterCPosition;
    private Coordinate letterDcoordinate;
    private Position letterDPosition;

    public Dots() {
        defaultPaint = new Paint();
        coin = new Coin();
        jewel1 = new JewelA(Environment.getInstance().getGameCanvasBlock());
        jewel2 = new JewelB(Environment.getInstance().getGameCanvasBlock());
        jewel3 = new JewelC(Environment.getInstance().getGameCanvasBlock());
        jewel4 = new JewelD(Environment.getInstance().getGameCanvasBlock());
        fountain = new Fountain();

        map = new GameMap();
        shuffleAnswers();
    }

    public void shuffleAnswers() {
        Vector<Coordinate> letterPositions = new Vector<Coordinate>();
        letterPositions.add(new Coordinate(1, 1));
        letterPositions.add(new Coordinate(1, GameMap.ROWS - 2));
        letterPositions.add(new Coordinate(GameMap.COLUMNS - 2, 1));
        letterPositions.add(new Coordinate(GameMap.COLUMNS - 2, GameMap.ROWS - 2));
        Collections.shuffle(letterPositions);
        letterAcoordinate = letterPositions.remove(0);
        letterBcoordinate = letterPositions.remove(0);
        letterCcoordinate = letterPositions.remove(0);
        letterDcoordinate = letterPositions.remove(0);

        letterAPosition = Board.coordinateToPosition(letterAcoordinate);
        letterAPosition.incY(jewel1.getOffset());
        letterAPosition.incX(jewel1.getOffset());

        letterBPosition = Board.coordinateToPosition(letterBcoordinate);
        letterBPosition.incY(jewel2.getOffset());
        letterBPosition.incX(jewel2.getOffset());

        letterCPosition = Board.coordinateToPosition(letterCcoordinate);
        letterCPosition.incY(jewel3.getOffset());
        letterCPosition.incX(jewel3.getOffset());

        letterDPosition = Board.coordinateToPosition(letterDcoordinate);
        letterDPosition.incY(jewel4.getOffset());
        letterDPosition.incX(jewel4.getOffset());

        fountainPosition = Board.coordinateToPosition(new Coordinate(14, 9));
    }


    public void reset() {
        shuffleAnswers();
        map = new GameMap();
    }

    public void render(Canvas canvas) {
        Position pos;
        for (int x = 0; x < GameMap.COLUMNS; x++)
            for (int y = 0; y < GameMap.ROWS; y++) {
                Coordinate ref = new Coordinate(x, y);
                if (map.getValue(ref) == 0) {
                    pos = Board.coordinateToPosition(ref);
                    canvas.drawBitmap(coin.getBitmap(), pos.getX() + coin.getOffset(), pos.getY() + coin.getOffset(), defaultPaint);
                }
            }

        canvas.drawBitmap(fountain.getBitmap(), fountainPosition.getX(), fountainPosition.getY(), defaultPaint);

        if (map.getValue(letterAcoordinate) != 2) {
            canvas.drawBitmap(jewel1.getBitmap(), letterAPosition.getX(), letterAPosition.getY(), defaultPaint);
        }
        if (map.getValue(letterBcoordinate) != 2) {
            canvas.drawBitmap(jewel2.getBitmap(), letterBPosition.getX(), letterBPosition.getY(), defaultPaint);
        }
        if (map.getValue(letterCcoordinate) != 2) {
            canvas.drawBitmap(jewel3.getBitmap(), letterCPosition.getX(), letterCPosition.getY(), defaultPaint);
        }
        if (map.getValue(letterDcoordinate) != 2) {
            canvas.drawBitmap(jewel4.getBitmap(), letterDPosition.getX(), letterDPosition.getY(), defaultPaint);
        }

    }

    public void clearPosition(Coordinate coordinate) {
        map.setValue(coordinate, 2);
    }

    public int whichItemAt(Coordinate coordinate) {
        return map.getValue(coordinate);
    }

    public int getAnswerAt(Coordinate coordinate) {
        if (coordinate.equalsTrunc(letterAcoordinate))
            return 1;
        if (coordinate.equalsTrunc(letterBcoordinate))
            return 2;
        if (coordinate.equalsTrunc(letterCcoordinate))
            return 3;
        if (coordinate.equalsTrunc(letterDcoordinate))
            return 4;
        return 0;
    }

    public void setAnswerCoordinate(int answer, Coordinate newCoordinate) {
        switch (answer) {
            case 1:
                letterAcoordinate = newCoordinate;
                break;
            case 2:
                letterBcoordinate = newCoordinate;
                break;
            case 3:
                letterCcoordinate = newCoordinate;
                break;
            case 4:
                letterDcoordinate = newCoordinate;
                break;
        }
    }

    public Coordinate getAnswerCoordinate(int letter) {
        switch (letter) {
            case 1:
                return letterAcoordinate;
            case 2:
                return letterBcoordinate;
            case 3:
                return letterCcoordinate;
            case 4:
                return letterDcoordinate;
            default:
                return null;
        }
    }

}
