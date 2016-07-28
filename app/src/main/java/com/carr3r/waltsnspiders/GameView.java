package com.carr3r.waltsnspiders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.carr3r.waltsnspiders.lightbox.LightboxGameOver;
import com.carr3r.waltsnspiders.lightbox.LightboxInstructions;
import com.carr3r.waltsnspiders.lightbox.LightboxMenu;
import com.carr3r.waltsnspiders.lightbox.LightboxPause;
import com.carr3r.waltsnspiders.lightbox.LightboxTrivia;
import com.carr3r.waltsnspiders.lightbox.LightboxWin;
import com.carr3r.waltsnspiders.listeners.InterfaceUpdateListener;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback, InterfaceUpdateListener {

    public static final int SECOND_INTERVAL = 1000;
    public static final String TAG = "GameView";
    private SurfaceHolder holder;
    private MySurfaceViewThread mySurfaceViewThread;
    private boolean hasSurface;
    private GameController controller;
    private static MainActivity mainActivity;
    private boolean renderHalf = false;

    public static Boolean preventRendering = false;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        hasSurface = false;
        mySurfaceViewThread = new MySurfaceViewThread();
        controller = new GameController();
        controller.setUpdateListener(this);
        mainActivity = (MainActivity) getContext();
        onUpdateRequest(new UpdateMessage(R.layout.lightbox_menu));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Environment env = Environment.getInstance();
            float m = env.getScreenHeight() / env.getScreenWidth();
            float y1 = event.getX() * m;
            float y2 = env.getScreenHeight() - y1;

            if (y1 > event.getY()) {
                if (y2 > event.getY())
                    controller.setJoystickDirection(GameMap.POSITION_UP);
                else
                    controller.setJoystickDirection(GameMap.POSITION_RIGHT);
            } else {
                if (y2 > event.getY())
                    controller.setJoystickDirection(GameMap.POSITION_LEFT);
                else
                    controller.setJoystickDirection(GameMap.POSITION_DOWN);
            }
        }
        return true;
    }

    public void resume() {
        // Create and start the graphics update thread.
        if (mySurfaceViewThread == null) {
            mySurfaceViewThread = new MySurfaceViewThread();

            if (hasSurface == true)
                mySurfaceViewThread.start();
        }
        preventRendering = false;
    }

    public void pause() {
        SoundController.getInstance().stopAll();
        // Kill the graphics update thread
//        controller.pause();
        if (mySurfaceViewThread != null) {
            mySurfaceViewThread.requestExitAndWait();
            mySurfaceViewThread = null;
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        this.setLayoutParams(new FrameLayout.LayoutParams((int) Environment.getInstance().getScreenWidth(), (int) Environment.getInstance().getScreenHeight()));
//        this.setLayoutParams(new LinearLayout.LayoutParams((int) 100, (int) 100));
        hasSurface = true;
        if (mySurfaceViewThread != null)
            mySurfaceViewThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        pause();
    }

    public void surfaceChanged(SurfaceHolder holder, int format,
                               int w, int h) {
        resume();
    }

    @Override
    public void onUpdateRequest(final UpdateMessage data) {

        if (preventRendering)
            return;

        switch (data.getComponent()) {
            case R.layout.lightbox_menu:
                controller.fakeIt();
                LightboxMenu lightboxMenuFragment = new LightboxMenu();
                lightboxMenuFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxMenuFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;
            case R.layout.lightbox_pause:
                LightboxPause lightboxPauseFragment = new LightboxPause();
                lightboxPauseFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxPauseFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;
            case R.layout.lightbox_instructions:
                LightboxInstructions lightboxInstructionsFragment = new LightboxInstructions();
                lightboxInstructionsFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxInstructionsFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;
            case R.layout.lightbox_trivia:
                renderCanvas();
                Quiz quiz = controller.getQuiz();
                LightboxTrivia lightboxTriviaFragment = new LightboxTrivia(quiz);
                lightboxTriviaFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxTriviaFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;
            case R.layout.lightbox_game_over:
                LightboxGameOver lightboxGameOverFragment = new LightboxGameOver(controller.getScore());
                lightboxGameOverFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxGameOverFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;

            case R.layout.lightbox_win:
                LightboxWin lightboxWinFragment = new LightboxWin();
                lightboxWinFragment.setOnFinishListener(controller);
                // Show Alert DialogFragment
                lightboxWinFragment.show(mainActivity.getSupportFragmentManager(), null);
                break;
            case GameController.RUNNING_MODE_PAUSED:
//                resetDrawn();
                break;
        }
    }

    public void renderCanvas() {
        if (preventRendering)
            return;

        controller.tick();

        if (hasSurface && holder != null) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                synchronized (holder) {
                    controller.render(canvas);
//                    DrawingUtils.crossScreen(canvas);
                }
                holder.unlockCanvasAndPost(canvas);

            }
        }
    }


    class MySurfaceViewThread extends Thread {

        // desired fps
        private final static int MAX_FPS = 25;
        // the frame period
        private final static int FRAME_PERIOD = 1000 / MAX_FPS;

        private boolean done;

        MySurfaceViewThread() {
            super();
            done = false;
        }

        @Override
        public void run() {

            long beginTime;        // the time when the cycle begun
            long timeDiff;        // the time it took for the cycle to execute
            int sleepTime;        // ms to sleep (<0 if we're behind)

            // Repeat the drawing loop until the thread is stopped.
            while (!done) {

                beginTime = System.currentTimeMillis();

                renderCanvas();

                timeDiff = System.currentTimeMillis() - beginTime;
                //b calculate sleep time
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void requestExitAndWait() {
            // Mark this thread as complete and combine into
            // the main application thread.
            done = true;
            try {
                join();
            } catch (InterruptedException ex) {
            }

        }

    }
}