package com.carr3r.waltsnspiders.visual;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.GameMap;
import com.carr3r.waltsnspiders.GameView;
import com.carr3r.waltsnspiders.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wneto on 14/10/2015.
 */
public class Board {

    public static final String TAG = "Board";

    private Bitmap boardCanvas;
    private Paint paint;
    private GameMap map;
    private int mapping[][];
    private Map<Integer, Bitmap> tiles;

    public Board() {
        map = new GameMap();
        boardCanvas = Bitmap.createBitmap((int) Environment.getInstance().getGameCanvasWidth(), (int) Environment.getInstance().getGameCanvasHeight(), Bitmap.Config.ARGB_8888);
        paint = new Paint();
        doMapping();
        drawBoard(new Canvas(boardCanvas));
    }

    public static Position coordinateToPosition(Coordinate coordinate) {
        float block = Environment.getInstance().getGameCanvasBlock();
        return new Position(coordinate.getRoundX() * block, coordinate.getRoundY() * block);
    }

    public static Coordinate positionToCoordinate(Position position) {
        float block = Environment.getInstance().getGameCanvasBlock();
        return new Coordinate((int) position.getX() / block, (int) position.getY() / block);
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(boardCanvas, 0, 0, paint);
    }

    public void doMapping() {
        mapping = new int[(int) GameMap.ROWS][(int) GameMap.COLUMNS];
        int fence_topLeft = R.drawable.fence_topleft;
        int fence_topRight = R.drawable.fence_topright;
        int fence_top = R.drawable.fence_top;
        int fence_left = R.drawable.fence_left;
        int fence_right = R.drawable.fence_right;
        int fence_bottom = R.drawable.fence_bottom;
        int fence_horizontal = R.drawable.fence_horizontal;
        int fence_vertical = R.drawable.fence_vertical;
        int fence_bottomLeft = R.drawable.fence_bottomleft;
        int fence_bottomRight = R.drawable.fence_bottomright;
        int grass = R.drawable.grass;
        int wall = R.drawable.wall;
        int safe = R.drawable.safe;

        Environment env = Environment.getInstance();
        float block = env.getGameCanvasBlock() * 1.01f;
        Resources resources = env.getContext().getResources();

        tiles = new HashMap<Integer, Bitmap>();
        tiles.put(fence_topLeft, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_topLeft), block, true));
        tiles.put(fence_topRight, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_topRight), block, true));
        tiles.put(fence_bottomLeft, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_bottomLeft), block, true));
        tiles.put(fence_bottomRight, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_bottomRight), block, true));
        tiles.put(fence_top, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_top), block, true));
        tiles.put(fence_bottom, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_bottom), block, true));
        tiles.put(fence_left, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_left), block, true));
        tiles.put(fence_right, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_right), block, true));
        tiles.put(wall, GameView.scaleDown(BitmapFactory.decodeResource(resources, wall), block, true));
        tiles.put(safe, GameView.scaleDown(BitmapFactory.decodeResource(resources, safe), block, true));
        tiles.put(fence_horizontal, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_horizontal), block, true));
        tiles.put(fence_vertical, GameView.scaleDown(BitmapFactory.decodeResource(resources, fence_vertical), block, true));
        tiles.put(grass, GameView.scaleDown(BitmapFactory.decodeResource(resources, grass), block, true));

        Coordinate ref = new Coordinate(0, 0);
        for (int x = 0; x < GameMap.COLUMNS; x++)
            for (int y = 0; y < GameMap.ROWS; y++) {
                ref.setX(x);
                ref.setY(y);
                int image;

                if (!GameMap.isCoordinateAvailable(ref, true)) {
                    if (GameMap.isCoordinateAvailable(ref, false))
                        image = safe;
                    else
                        image = wall;
                } else {
                    int tile = 0;
                    boolean isLeftBlocked = GameMap.getDValue(new Coordinate(ref.getX() - 1, ref.getY())) == 1;
                    boolean isRightBlocked = GameMap.getDValue(new Coordinate(ref.getX() + 1, ref.getY())) == 1;
                    boolean isTopBlocked = GameMap.getDValue(new Coordinate(ref.getX(), ref.getY() - 1)) == 1;
                    boolean isBottomBlocked = GameMap.getDValue(new Coordinate(ref.getX(), ref.getY() + 1)) == 1;

                    if (isTopBlocked)
                        tile += 'T';
                    if (isBottomBlocked)
                        tile += 'B';
                    if (isLeftBlocked)
                        tile += 'L';
                    if (isRightBlocked)
                        tile += 'R';

                    switch (tile) {
                        case 'T':
                            image = fence_top;
                            break;
                        case 'B':
                            image = fence_bottom;
                            break;
                        case 'T' + 'B':
                            image = fence_horizontal;
                            break;
                        case 'T' + 'L':
                            image = fence_topLeft;
                            break;
                        case 'T' + 'R':
                            image = fence_topRight;
                            break;
                        case 'B' + 'R':
                            image = fence_bottomRight;
                            break;
                        case 'B' + 'L':
                            image = fence_bottomLeft;
                            break;
                        case 'L' + 'R':
                            image = fence_vertical;
                            break;
                        case 'L':
                            image = fence_left;
                            break;
                        case 'R':
                            image = fence_right;
                            break;
                        default:
                            image = grass;
                    }
                }
                mapping[y][x] = image;
            }
    }


    public void drawBoard(Canvas canvas) {
        Environment env = Environment.getInstance();

        Coordinate ref = new Coordinate(0, 0);
        for (int x = 0; x < GameMap.COLUMNS; x++)
            for (int y = 0; y < GameMap.ROWS; y++) {
                ref.setX(x);
                ref.setY(y);
                Position pos = coordinateToPosition(ref);
                canvas.drawBitmap(tiles.get(mapping[y][x]), pos.getX(), pos.getY(), paint);
            }
        Decoration decoration = new Decoration();
        decoration.render(canvas);

    }

}
