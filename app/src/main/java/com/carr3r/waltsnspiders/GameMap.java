package com.carr3r.waltsnspiders;

import com.carr3r.waltsnspiders.visual.Coordinate;

import java.util.Random;

/**
 * Created by Neto on 10/18/2015.
 */
public class GameMap {

    public static final String TAG = "GameMap";
    public static final int POSITION_RIGHT = 1;
    public static final int POSITION_DOWN = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_UP = 4;
    private static final int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 1, 2, 3, 3, 3, 3, 2, 1, 2, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 2, 1, 2, 3, 5, 5, 3, 2, 1, 2, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 2, 1, 2, 3, 5, 5, 3, 2, 1, 2, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 1, 2, 3, 3, 3, 3, 2, 1, 2, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
    };
    public static final float ROWS = map.length;
    public static final float COLUMNS = map[0].length;
    private int[][] myMap;

    public GameMap() {
        myMap = new int[(int) GameMap.ROWS][(int) GameMap.COLUMNS];
        for (int x = 0; x < GameMap.COLUMNS; x++)
            for (int y = 0; y < GameMap.ROWS; y++)
                myMap[y][x] = map[y][x];
    }

    public static int getDValue(Coordinate coordinate) {
        return map[coordinate.getRoundY()][coordinate.getRoundX()];
    }

    public int getValue(Coordinate coordinate) {
        return myMap[coordinate.getRoundY()][coordinate.getRoundX()];
    }

    public void setValue(Coordinate coordinate, int value) {
        myMap[coordinate.getRoundY()][coordinate.getRoundX()] = value;
    }


    public static boolean isCoordinateAvailable(Coordinate coordinate, boolean forEnemy) {
        if (forEnemy)
            return map[coordinate.getRoundY()][coordinate.getRoundX()] != 1 && map[coordinate.getRoundY()][coordinate.getRoundX()] != 3;
        else
            return map[coordinate.getRoundY()][coordinate.getRoundX()] != 1;
    }

    public static int getOpposite(int orientation) {
        switch (orientation) {
            case POSITION_DOWN:
                return POSITION_UP;
            case POSITION_UP:
                return POSITION_DOWN;
            case POSITION_RIGHT:
                return POSITION_LEFT;
            case POSITION_LEFT:
                return POSITION_RIGHT;
            default:
                return 0;
        }
    }

    public static boolean isIntersection(Coordinate coordinate) {
        int options = 0;
        options += isCoordinateAvailable(new Coordinate(coordinate.getX() - 1, coordinate.getY()), true) ? 1 : 0;
        options += isCoordinateAvailable(new Coordinate(coordinate.getX() + 1, coordinate.getY()), true) ? 1 : 0;
        options += isCoordinateAvailable(new Coordinate(coordinate.getX(), coordinate.getY() - 1), true) ? 1 : 0;
        options += isCoordinateAvailable(new Coordinate(coordinate.getX(), coordinate.getY() + 1), true) ? 1 : 0;
        return options >= 3;
    }


    public static int sortOrientation() {
        Random rn = new Random();
        return rn.nextInt(4) + 1;
    }

    public static String orientationToString(int orientation) {
        switch (orientation) {
            case POSITION_RIGHT:
                return "POSITION_RIGHT";
            case POSITION_LEFT:
                return "POSITION_LEFT";
            case POSITION_UP:
                return "POSITION_UP";
            case POSITION_DOWN:
                return "POSITION_DOWN";
        }
        return null;
    }

}
