package com.carr3r.waltsnspiders;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.WindowManager;

import com.carr3r.waltsnspiders.visual.Score;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wneto on 13/10/2015.
 */
public class Environment {

    public static final String TAG = "Environment";
    private static Environment instance = new Environment();
    private float screenWidth = 1;
    private float screenHeight = 1;
    private float gameCanvasStep = 1;
    private float gameCanvasBlock = 1;
    private float gameCanvasWidth = 1;
    private float gameCanvasHeigth = 1;
    private Context context;
    private GameController gameController;
    private Boolean shouldShowInstruction;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private Locale locale;

    public static Environment getInstance() {
        return instance;
    }

    private Environment() {
    }

    public void loadEnv(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        this.screenWidth = display.getWidth();
        this.screenHeight = display.getHeight();

        this.gameCanvasStep = screenHeight / ((GameMap.ROWS - 1) * 10f);
        this.gameCanvasBlock = gameCanvasStep * 10f;
        this.gameCanvasWidth = gameCanvasBlock * GameMap.COLUMNS;

        if (gameCanvasWidth > screenWidth) {
            gameCanvasStep = screenWidth / ((GameMap.COLUMNS - 1) * 10f);
            this.gameCanvasBlock = gameCanvasStep * 10f;
            this.gameCanvasWidth = gameCanvasBlock * GameMap.COLUMNS;
        }
        this.gameCanvasHeigth = gameCanvasBlock * GameMap.ROWS;

        this.context = context;
        locale = context.getApplicationContext().getResources().getConfiguration().locale;
    }

    public float getGameCanvasWidth() {
        return gameCanvasWidth;
    }

    public float getGameCanvasHeight() {
        return gameCanvasHeigth;
    }

    public float getGameCanvasStep() {
        return gameCanvasStep;
    }

    public float getGameCanvasBlock() {
        return gameCanvasBlock;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public Context getContext() {
        return context;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController newController) {
        gameController = newController;
    }

    public Score getHighestScore() {
        SharedPreferences settings = context.getSharedPreferences("score", 0);
        Score result = new Score(false);
        if (settings.contains("level") && settings.contains("points")) {
            result.setLevel(Integer.parseInt(settings.getString("level", "1")));
            result.setPoints(Integer.parseInt(settings.getString("points", "0")));
        }
        return result;

    }

    public void registerHighestScore(Score score) {
        if (score == null) {
            SharedPreferences settings = context.getSharedPreferences("score", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("level", "1");
            editor.putString("points", "0");
            editor.commit();
            return;
        }
        if (getHighestScore().getPoints() < score.getPoints()) {
            SharedPreferences settings = context.getSharedPreferences("score", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("level", String.valueOf(score.getLevel()));
            editor.putString("points", String.valueOf(score.getPoints()));
            editor.commit();
        }
    }

    public Map<String, String> getFacebookData() {
        SharedPreferences settings = context.getSharedPreferences("facebook", 0);
        Map<String, String> out = new HashMap<String, String>();
        out.put("accessToken", settings.getString("accessToken", ""));
        out.put("userId", settings.getString("userId", ""));
        out.put("name", settings.getString("name", ""));
        return out;
    }

    public void disableInstructions() {
        SharedPreferences settings = context.getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("instructions", false);
        editor.commit();
        shouldShowInstruction = false;
    }

    public boolean shouldShowInstruction() {
        if (shouldShowInstruction == null) {
            SharedPreferences settings = context.getSharedPreferences("settings", 0);
            shouldShowInstruction = settings.getBoolean("instructions", true);
        }
        return shouldShowInstruction;
    }

    public void registerFacebook(String userId, String token, String name) {
        SharedPreferences settings = context.getSharedPreferences("facebook", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accessToken", token);
        editor.putString("userId", userId);
        editor.putString("name", CloudDatabase.removeAccents(name));
        editor.commit();
    }

}
