package com.carr3r.waltsnspiders;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.appbrain.AppBrain;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends FragmentActivity {

    public final static String TAG = MainActivity.class.getName();
    GameView renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Environment.getInstance().loadEnv(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        renderView = (GameView) findViewById(R.id.render);
        AppBrain.init(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Environment.getInstance().getGameController().backButton();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (renderView != null)
            renderView.resume();
        SoundController.getInstance().restoreSound();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
        SoundController.getInstance().stopAll();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        SoundController.getInstance().stopAll();
    }

}
