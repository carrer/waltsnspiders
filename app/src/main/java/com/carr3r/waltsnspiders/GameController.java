package com.carr3r.waltsnspiders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.carr3r.waltsnspiders.level.LevelSettings;
import com.carr3r.waltsnspiders.level.LevelsFabric;
import com.carr3r.waltsnspiders.lightbox.LightboxGameOver;
import com.carr3r.waltsnspiders.lightbox.LightboxInstructions;
import com.carr3r.waltsnspiders.lightbox.LightboxMenu;
import com.carr3r.waltsnspiders.lightbox.LightboxPause;
import com.carr3r.waltsnspiders.lightbox.LightboxRanking;
import com.carr3r.waltsnspiders.lightbox.LightboxTrivia;
import com.carr3r.waltsnspiders.lightbox.LightboxWin;
import com.carr3r.waltsnspiders.listeners.ClockListener;
import com.carr3r.waltsnspiders.listeners.InterfaceUpdateListener;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;
import com.carr3r.waltsnspiders.visual.Board;
import com.carr3r.waltsnspiders.visual.Coordinate;
import com.carr3r.waltsnspiders.visual.Dots;
import com.carr3r.waltsnspiders.visual.DrawingUtils;
import com.carr3r.waltsnspiders.visual.Enemy;
import com.carr3r.waltsnspiders.visual.Hero;
import com.carr3r.waltsnspiders.visual.Score;

import java.util.Collections;
import java.util.Vector;

/**
 * Created by Neto on 10/21/2015.
 */
public class GameController implements ClockListener, OnLightboxFinishes {

    public final static String TAG = "GameController";
    public final static int MODE_CHASE = 1;
    public final static int MODE_SCATTER = 2;
    public final static int RUNNING_MODE_STOPPED = 1;
    public final static int RUNNING_MODE_RUNNING = 2;
    public final static int RUNNING_MODE_PAUSED = 3;
    public final static int RUNNING_MODE_OVER = 4;
    private Board board;
    private Hero hero;
    private Enemy enemies[];
    private Dots dots;
    private Environment environment;
    private InterfaceUpdateListener interfaceUpdater;
    private TimeController timeController;
    private Score score;
    private boolean showArrows = false;


    protected int joystickDirection = 0;
    protected boolean userHasClicked = false;

    public void setJoystickDirection(int direction) {
        joystickDirection = direction;
        userHasClicked = true;
    }

    protected Canvas backgroundCanvas;
    protected Bitmap backgroundCanvasBitmap;
    protected Canvas boardCanvas;
    protected Bitmap boardCanvasBitmap;
    protected Paint paint;

    public int getChasingMode() {
        return chasingMode;
    }

    public void setChasingMode(int chasingMode) {
        this.chasingMode = chasingMode;
    }

    public void setRunningMode(int runningMode) {
        this.runningMode = runningMode;
    }

    private int runningMode = RUNNING_MODE_STOPPED;
    private int chasingMode = MODE_SCATTER;
    private LevelSettings level;

    private Quiz quiz;

    public Board getBoard() {
        return board;
    }

    public GameController() {
        Environment.getInstance().setGameController(this);
        board = new Board();
        hero = new Hero(new Coordinate(16, 9), GameMap.POSITION_LEFT);
        enemies = new Enemy[4];
        enemies[0] = new Enemy(1);
        enemies[1] = new Enemy(2);
        enemies[2] = new Enemy(3);
        enemies[3] = new Enemy(4);
        environment = Environment.getInstance();
        timeController = new TimeController();
        timeController.addListener(this);
        score = new Score(true);
        dots = new Dots();

        backgroundCanvasBitmap = Bitmap.createBitmap((int) Environment.getInstance().getScreenWidth(), (int) Environment.getInstance().getScreenHeight(), Bitmap.Config.ARGB_8888);
        backgroundCanvas = new Canvas(backgroundCanvasBitmap);

        boardCanvasBitmap = Bitmap.createBitmap((int) Environment.getInstance().getGameCanvasWidth(), (int) Environment.getInstance().getGameCanvasHeight(), Bitmap.Config.ARGB_8888);
        boardCanvas = new Canvas(boardCanvasBitmap);

        DrawingUtils.renderGrassAllOver(backgroundCanvas);
        paint = new Paint();
    }

    public void reset() {
        score.reset();
    }

    public void initLevel() {
        runningMode = RUNNING_MODE_STOPPED;
        chasingMode = MODE_SCATTER;
        hero.resetPosition();
        hero.setVisible(true);
        hero.dropJewel();
        enemies[0].resetPosition();
        enemies[1].resetPosition();
        enemies[2].resetPosition();
        enemies[3].resetPosition();
        level = LevelsFabric.getLevel(score.getLevel());
        Vector<Coordinate> homes = new Vector<Coordinate>();
        homes.add(new Coordinate(7, 4));
        homes.add(new Coordinate(22, 15));
        homes.add(new Coordinate(22, 4));
        homes.add(new Coordinate(7, 15));
        Collections.shuffle(homes);
        enemies[0].setHome(homes.remove(0));
        enemies[1].setHome(homes.remove(0));
        enemies[2].setHome(homes.remove(0));
        enemies[3].setHome(homes.remove(0));
        timeController.resetTime();
        quiz = new Quiz(level.getLevel());
        dots.reset();
        timeController.resetTime();
        joystickDirection = 0;

    }

    public void fakeIt() {
        runningMode = RUNNING_MODE_RUNNING;
        chasingMode = MODE_SCATTER;
        hero.setVisible(false);
        enemies[0].resetPosition();
        enemies[1].resetPosition();
        enemies[2].resetPosition();
        enemies[3].resetPosition();
        level = LevelsFabric.getLevel(0);
        Vector<Coordinate> homes = new Vector<Coordinate>();
        homes.add(new Coordinate(1, 1));
        homes.add(new Coordinate(GameMap.COLUMNS - 2, 1));
        homes.add(new Coordinate(1, GameMap.ROWS - 2));
        homes.add(new Coordinate(GameMap.COLUMNS - 2, GameMap.ROWS - 2));
        Collections.shuffle(homes);
        enemies[0].setHome(homes.remove(0));
        enemies[1].setHome(homes.remove(0));
        enemies[2].setHome(homes.remove(0));
        enemies[3].setHome(homes.remove(0));
        timeController.resetTime();
        quiz = new Quiz(level.getLevel());
        dots.reset();
        timeController.resetTime();
        joystickDirection = 0;
    }

    public void tick() {

        if (getRunningMode() != RUNNING_MODE_RUNNING)
            return;

        if (hero.isVisible()) {

            if (hero.hasChangedPosition())
                verifyNewColisions();

            if (enemies[0].touches(hero) ||
                    enemies[1].touches(hero) ||
                    enemies[2].touches(hero) ||
                    enemies[3].touches(hero))
                gameOver();
        }
        timeController.tick();
        handlePacman();
        handleEnemy(enemies[0]);
        handleEnemy(enemies[1]);
        handleEnemy(enemies[2]);
        handleEnemy(enemies[3]);
    }

    private void verifyNewColisions() {
        Coordinate coordinate = hero.getCoordinate();

        int dotType = dots.whichItemAt(coordinate);
        if (dotType == 0) {
            dots.clearPosition(coordinate);
            eat();
        } else if (dotType == 4) {
            if (!hero.isCarrying()) {
                SoundController.getInstance().playSound(SoundController.collect);
                hero.dragJewel(dots.getAnswerAt(coordinate));
                dots.clearPosition(coordinate);
            }
        } else if (dotType == 5) {
            if (hero.isCarrying()) {
                int carryingJewel = hero.getJewel();
                hero.dropJewel();
                if (quiz.getRightAnswer() == carryingJewel)
                    won();
                else
                    penalt();
            }
        }
    }

    public void handlePacman() {
        if (joystickDirection != 0) {
            if (hero.canMoveToward(joystickDirection)) {
                hero.setOrientation(joystickDirection);
                joystickDirection = 0;
                hero.move();
                return;
            }
        }

        if (!hero.isBlockCentered())
            hero.move();
        else {

            if (hero.canMoveForward())
                hero.move();
        }
    }

    public void handleEnemy(Enemy enemy) {
        if (enemy.isBlockCentered()) {
            if (GameMap.isIntersection(enemy.getCoordinate())) {
                enemy.findPath(hero, enemies);
            } else if (!enemy.canMoveForward()) {
                enemy.turnSideway();
            }
        }
        enemy.move();
    }

    public void tickSecond() {
        int newMode;
        if (level != null)
            newMode = level.whichMode(timeController.getElapsed());
        else
            newMode = MODE_SCATTER;

        setChasingMode(newMode);

    }

    public void newGame() {
        reset();
        prepareStart();
    }

    public void prepareStart() {
        initLevel();
        fireUpdate(new UpdateMessage(R.layout.lightbox_trivia));
    }

    public void showInstructions() {
        fireUpdate(new UpdateMessage(R.layout.lightbox_instructions));
    }

    public void levelUp() {
        score.incLevel();
    }

    public void pause() {
        runningMode = RUNNING_MODE_PAUSED;
        fireUpdate(new UpdateMessage(R.layout.lightbox_pause));
        SoundController.getInstance().stopAll();
    }

    public void eat() {
        score.addPoints(1);
        SoundController.getInstance().playSound(SoundController.coin);
    }

    public void won() {
        score.addPoints(50);
        SoundController.getInstance().playSound(SoundController.achievement);
        runningMode = RUNNING_MODE_STOPPED;
        fireUpdate(new UpdateMessage(R.layout.lightbox_win));
    }

    public void penalt() {
        score.addPoints(-25);
        SoundController.getInstance().playSound(SoundController.wrong);
    }

    public void gameOver() {
        if (getRunningMode() == RUNNING_MODE_OVER)
            return;

        hero.dropJewel();
        runningMode = RUNNING_MODE_OVER;
        SoundController.getInstance().playSound(SoundController.beaten);
        fireUpdate(new UpdateMessage(R.layout.lightbox_game_over));
    }

    public void start() {
        runningMode = RUNNING_MODE_RUNNING;
    }

    public void menu() {
        fireUpdate(new UpdateMessage(R.layout.lightbox_menu));
    }

    public void setShowArrows(boolean newValue)
    {
        showArrows = newValue;
    }

    public boolean shouldShowArrows()
    {
        return showArrows;
    }

    public int getRunningMode() {
        return runningMode;
    }

    public void render(Canvas canvas) {
        board.render(boardCanvas);
        dots.render(boardCanvas);
        score.render(boardCanvas);
        enemies[0].render(boardCanvas);
        enemies[1].render(boardCanvas);
        enemies[2].render(boardCanvas);
        enemies[3].render(boardCanvas);
        if (runningMode == RUNNING_MODE_PAUSED)
            DrawingUtils.renderPause(boardCanvas);
        else if (runningMode == RUNNING_MODE_OVER)
            DrawingUtils.renderGameOver(boardCanvas);
        hero.render(boardCanvas);

        canvas.drawBitmap(backgroundCanvasBitmap, 0, 0, paint);
        canvas.drawBitmap(boardCanvasBitmap, (Environment.getInstance().getScreenWidth() - Environment.getInstance().getGameCanvasWidth()) / 2, -Environment.getInstance().getGameCanvasBlock() / 2, paint);
        if (userHasClicked) {
            DrawingUtils.crossScreen(canvas, joystickDirection);
            userHasClicked = false;
        }
        if (shouldShowArrows())
            DrawingUtils.drawArrows(canvas);
    }

    public void setUpdateListener(InterfaceUpdateListener listener) {
        interfaceUpdater = listener;
    }

    public void fireUpdate(UpdateMessage object) {
        if (interfaceUpdater != null)
            interfaceUpdater.onUpdateRequest(object);
    }

    public Score getScore() {
        return score;
    }


    public Quiz getQuiz() {
        return quiz;
    }

    @Override
    public void onLightBoxFinishes(Class whichLightbox) {

        if (whichLightbox == LightboxTrivia.class) {
            start();
            SoundController.getInstance().restoreSound();
            SoundController.getInstance().playBackgroundSound(SoundController.background_play);
        } else if (whichLightbox == LightboxWin.class) {
            levelUp();
            prepareStart();
        } else if (whichLightbox == LightboxGameOver.class) {
            menu();
        } else if (whichLightbox == LightboxPause.class) {
            SoundController.getInstance().restoreSound();
            SoundController.getInstance().playBackgroundSound(SoundController.background_play);
            start();
        } else if (whichLightbox == LightboxMenu.class) {
            if (Environment.getInstance().shouldShowInstruction())
                showInstructions();
            else
                newGame();
        } else if (whichLightbox == LightboxInstructions.class) {
            newGame();
        } else if (whichLightbox == LightboxRanking.class) {
            menu();
        }

    }

    public void backButton() {
        if (getRunningMode() == RUNNING_MODE_RUNNING)
            pause();
    }
}
