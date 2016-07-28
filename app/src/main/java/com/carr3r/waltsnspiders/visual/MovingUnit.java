package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameMap;

/**
 * Created by wneto on 23/10/2015.
 */
public abstract class MovingUnit {

    public static final String TAG = "MovingUnit";
    protected Coordinate coordinate;
    protected Position position;
    protected int orientation;
    protected float rate = 0.125f;
    protected float step;
    protected float dyingDistance;
    protected float block;
    protected Paint paint;
    protected boolean visible = true;
    protected boolean changedPosition = false;

    public boolean hasChangedPosition() {
        return changedPosition;
    }

    // for reset purposes
    protected Coordinate originalCoordinate;
    protected int originalOrientation;

    public MovingUnit(Coordinate initialCoordinate, int initialOrientation) {
        originalCoordinate = initialCoordinate;
        originalOrientation = initialOrientation;

        coordinate = originalCoordinate.clone();
        orientation = initialOrientation;

        step = Environment.getInstance().getGameCanvasStep() * rate * 10;
        dyingDistance = rate * 4f;
        block = Environment.getInstance().getGameCanvasBlock();
        position = Board.coordinateToPosition(coordinate);
    }

    public void resetPosition() {
        setCoordinate(originalCoordinate.clone());
        setOrientation(originalOrientation);
        position = Board.coordinateToPosition(coordinate);
    }

    public abstract Bitmap getCurrentBitmap();

    public void render(Canvas canvas) {
        if (paint == null)
            paint = new Paint();

        if (isVisible())
            canvas.drawBitmap(getCurrentBitmap(), position.getX(), position.getY(), paint);
    }

    public boolean isPerpendicular(int q) {
        if (getOrientation() == GameMap.POSITION_DOWN || getOrientation() == GameMap.POSITION_UP)
            return q == GameMap.POSITION_LEFT || q == GameMap.POSITION_RIGHT;
        else
            return q == GameMap.POSITION_DOWN || q == GameMap.POSITION_UP;
    }


    public boolean canMoveToward(int targetSide) {
        if (isPerpendicular(targetSide) && !isBlockCentered())
            return false;

        return GameMap.isCoordinateAvailable(getNextCoordinate(targetSide), (getClass() == Enemy.class));
    }

    public boolean canMoveForward() {
        return canMoveToward(getOrientation());
    }

    public void validateAnyApplyOrientation(int newOrientation[]) {
        for (int i = 0; i < newOrientation.length; i++)
            if (canMoveToward(newOrientation[i])) {
                setOrientation(newOrientation[i]);
                return;
            }
    }

    public void turnSideway() {
        int side = getOrientation() == GameMap.POSITION_UP ? GameMap.POSITION_RIGHT : getOrientation() + 1;
        if (GameMap.isCoordinateAvailable(getNextCoordinate(side), true))
            setOrientation(side);
        else
            setOrientation(GameMap.getOpposite(side));
    }

    public Coordinate getNextCoordinate(int reference) {
        Coordinate next = coordinate.clone();
        switch (reference) {
            case GameMap.POSITION_RIGHT:
                next.incX(1);
                break;
            case GameMap.POSITION_LEFT:
                next.incX(-1);
                break;
            case GameMap.POSITION_UP:
                next.incY(-1);
                break;
            case GameMap.POSITION_DOWN:
                next.incY(1);
                break;
        }
        return next;
    }

    public void move() {
        Coordinate old = coordinate.clone();
        switch (orientation) {
            case GameMap.POSITION_RIGHT:
                position.incX(step);
                coordinate.incX(rate);
                break;
            case GameMap.POSITION_LEFT:
                position.incX(-step);
                coordinate.incX(-rate);
                break;
            case GameMap.POSITION_DOWN:
                position.incY(step);
                coordinate.incY(rate);
                break;
            case GameMap.POSITION_UP:
                position.incY(-step);
                coordinate.incY(-rate);
                break;
        }

        changedPosition = !old.equals(coordinate);
    }

    public void setOrientation(int v) {
        orientation = v;
        switch (orientation) {
            case GameMap.POSITION_RIGHT:
                rotateBitmap(0);
                break;
            case GameMap.POSITION_DOWN:
                rotateBitmap(90);
                break;
            case GameMap.POSITION_LEFT:
                rotateBitmap(180);
                break;
            case GameMap.POSITION_UP:
                rotateBitmap(270);
                break;
        }
    }


    public boolean isBlockCentered() {
        double diffX = coordinate.getX() - coordinate.getRoundX();
        double diffY = coordinate.getY() - coordinate.getRoundY();
        return diffX < rate && diffY < rate;
    }

    public boolean touches(MovingUnit other) {
        if (Math.abs(other.getCoordinate().getX() - coordinate.getX()) < rate) {
            return Math.abs(other.getCoordinate().getY() - coordinate.getY()) < dyingDistance;
        } else if (Math.abs(other.getCoordinate().getY() - coordinate.getY()) < rate) {
            return Math.abs(other.getCoordinate().getX() - coordinate.getX()) < dyingDistance;
        } else
            return false;
    }

    public abstract void rotateBitmap(int angle);

    public void setInitialCoordinateAndOrientation(Coordinate newCoordinate, int newOrientation) {
        originalCoordinate = newCoordinate;
        originalOrientation = newOrientation;
        resetPosition();
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.position = Board.coordinateToPosition(coordinate);
    }

    public int getOrientation() {
        return orientation;
    }

    public Position getPosition() {
        return position;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return this.getClass().toString() + " (" + coordinate.getX() + "x" + coordinate.getY() + ") Orientation = " + GameMap.orientationToString(getOrientation());
    }

    public void setVisible(boolean newVisibility) {
        visible = newVisibility;
    }

    public boolean isVisible() {
        return visible;
    }

}