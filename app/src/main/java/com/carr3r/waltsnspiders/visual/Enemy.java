package com.carr3r.waltsnspiders.visual;

import android.graphics.Bitmap;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameController;
import com.carr3r.waltsnspiders.GameMap;

/**
 * Created by wneto on 14/10/2015.
 */
public class Enemy extends MovingUnit {
    public static final String TAG = "Enemy";

    private Coordinate home;
    private int enemyIndex;
    private String name;
    private SpriteImage img;

    public Enemy(int enemyIndex) {
        super(new Coordinate(0, 0), 0);
        this.enemyIndex = enemyIndex;
        if (Environment.getInstance().getContext() != null) {
            img = new EnemyImage(enemyIndex);
        }

        switch (enemyIndex) {
            default:
                name = "Blinky";
                setInitialCoordinateAndOrientation(new Coordinate(7, 4), GameMap.POSITION_DOWN);
                break;
            case 2:
                name = "Pinky";
                setInitialCoordinateAndOrientation(new Coordinate(22, 15), GameMap.POSITION_UP);
                break;
            case 3:
                name = "Inky";
                setInitialCoordinateAndOrientation(new Coordinate(22, 4), GameMap.POSITION_LEFT);
                break;
            case 4:
                name = "Clyde";
                setInitialCoordinateAndOrientation(new Coordinate(7, 15), GameMap.POSITION_RIGHT);
                break;
        }
    }

    public int getEnemyIndex() {
        return enemyIndex;
    }

    public void setHome(Coordinate newHome) {
        home = newHome;
    }

    @Override
    public Bitmap getCurrentBitmap() {
        return img.getBitmap();
    }

    @Override
    public boolean canMoveToward(int targetSide) {
        if (targetSide == GameMap.getOpposite(getOrientation()))
            return false;
        return super.canMoveToward(targetSide);
    }

    @Override
    public void rotateBitmap(int angle) {
        switch (angle) {
            default:
                img.setOrientation(GameMap.POSITION_RIGHT);
                break;
            case 90:
                img.setOrientation(GameMap.POSITION_DOWN);
                break;
            case 180:
                img.setOrientation(GameMap.POSITION_LEFT);
                break;
            case 270:
                img.setOrientation(GameMap.POSITION_UP);
                break;
        }
    }

    public Coordinate getChasingCoordinate(Hero hero, Enemy enemies[]) {
        Coordinate ref;
        int deltaX, deltaY;
        switch (enemyIndex) {
            case 1:
                return hero.getCoordinate();
            case 2:
                ref = hero.getCoordinate().clone();
                switch (hero.getOrientation()) {
                    case GameMap.POSITION_DOWN:
                        ref.incY(4);
                        break;
                    case GameMap.POSITION_UP:
                        ref.incY(-4);
                        break;
                    case GameMap.POSITION_LEFT:
                        ref.incX(-4);
                        break;
                    case GameMap.POSITION_RIGHT:
                        ref.incX(4);
                        break;
                }
                return ref;
            case 3:
                ref = hero.getCoordinate().clone();
                switch (hero.getOrientation()) {
                    case GameMap.POSITION_DOWN:
                        ref.incY(2);
                        break;
                    case GameMap.POSITION_UP:
                        ref.incY(-2);
                        break;
                    case GameMap.POSITION_LEFT:
                        ref.incX(-2);
                        break;
                    case GameMap.POSITION_RIGHT:
                        ref.incX(2);
                        break;
                }

                deltaX = ref.getRoundX() - enemies[0].getCoordinate().getRoundX();
                deltaY = ref.getRoundY() - enemies[0].getCoordinate().getRoundY();
                ref.incX(deltaX);
                ref.incY(deltaY);
                return ref;
            case 4:
                int diff = Math.abs(hero.getCoordinate().getRoundX() - getCoordinate().getRoundX()) +
                        Math.abs(hero.getCoordinate().getRoundY() - getCoordinate().getRoundY());
                ref = diff >= 8 ? hero.getCoordinate() : home;
                return ref;
        }
        return null;
    }


    public void findPath(Hero hero, Enemy[] enemies) {
        int gameMode = Environment.getInstance().getGameController().getChasingMode();

        Coordinate reference;
        if (gameMode == GameController.MODE_CHASE)
            reference = getChasingCoordinate(hero, enemies);
        else
            reference = home;

        if (reference == null)
            return;

        float deltaX = reference.getRoundX() - getCoordinate().getRoundX();
        float deltaY = reference.getRoundY() - getCoordinate().getRoundY();

        int[] nextOrientation = new int[3];

        if (Math.abs(deltaX) > Math.abs(deltaY)) // horizontal
        {
            nextOrientation[0] = deltaX >= 0 ? GameMap.POSITION_RIGHT : GameMap.POSITION_LEFT;
            nextOrientation[1] = deltaY >= 0 ? GameMap.POSITION_DOWN : GameMap.POSITION_UP;
        } else // vertical
        {
            nextOrientation[0] = deltaY >= 0 ? GameMap.POSITION_DOWN : GameMap.POSITION_UP;
            nextOrientation[1] = deltaX >= 0 ? GameMap.POSITION_RIGHT : GameMap.POSITION_LEFT;
        }

        nextOrientation[2] = GameMap.sortOrientation();
        while (canMoveToward(nextOrientation[2]))
            nextOrientation[2] = GameMap.sortOrientation();

        validateAnyApplyOrientation(nextOrientation);
    }


}
