package com.carr3r.waltsnspiders;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neto on 10/17/2015.
 */
public class SoundController {

    private static final String TAG = "SoundController";
    private static SoundController instance;
    private Map<Integer, Integer> sounds = new HashMap<Integer, Integer>();

    public static int coin = R.raw.coin;
    public static int background_menu = R.raw.background_menu;
    public static int background_quiz = R.raw.background_quiz;
    public static int background_gameover = R.raw.background_gameover;
    public static int background_play = R.raw.background_play;
    public static int achievement = R.raw.achievement;
    public static int collect = R.raw.collect;
    public static int wrong = R.raw.wrong;
    public static int beaten = R.raw.beaten;
    private SoundPool soundPool;
    private boolean enabled = true;
    private int backgroundTrack = 0;

    protected int lastSoundtrack;
    protected MediaPlayer mPlayer;


    private SoundController() {
        initSounds();
    }

    public static SoundController getInstance() {
        if (instance == null)
            instance = new SoundController();

        return instance;
    }

    /**
     * Populate the SoundPool
     */
    public void initSounds() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sounds.put(coin, soundPool.load(Environment.getInstance().getContext(), R.raw.coin, 1));
        sounds.put(wrong, soundPool.load(Environment.getInstance().getContext(), R.raw.wrong, 1));
        sounds.put(achievement, soundPool.load(Environment.getInstance().getContext(), R.raw.achievement, 1));
        sounds.put(beaten, soundPool.load(Environment.getInstance().getContext(), R.raw.beaten, 1));
        sounds.put(collect, soundPool.load(Environment.getInstance().getContext(), R.raw.collect, 1));
    }

    public void stopBackgroundSound() {
        if (mPlayer != null && mPlayer.isPlaying())
            mPlayer.stop();
    }

    public void playBackgroundSound(int soundID) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            if (soundID == lastSoundtrack)
                return;

            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(Environment.getInstance().getContext(), soundID);
        mPlayer.setLooping(true);
        mPlayer.start();
        lastSoundtrack = soundID;
    }

    public void playSound(int soundID) {
        if (!enabled)
            return;

        soundPool.play(sounds.get(soundID), 0.5f, 0.5f, 1, 0, 1f);
    }

    public void stopAll() {
        if (soundPool != null)
            soundPool.autoPause();
        if (mPlayer != null) {
            mPlayer.pause();
            backgroundTrack = mPlayer.getCurrentPosition();
        }
        enabled = false;
    }

    public void restoreSound() {
        if (soundPool != null)
            soundPool.autoResume();
        if (mPlayer != null) {
            if (backgroundTrack > 0)
                mPlayer.seekTo(backgroundTrack);
            mPlayer.start();
        }
        enabled = true;
    }
}
