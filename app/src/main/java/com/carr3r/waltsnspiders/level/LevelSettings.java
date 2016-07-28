package com.carr3r.waltsnspiders.level;

import com.carr3r.waltsnspiders.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wneto on 26/10/2015.
 */
public abstract class LevelSettings {

    protected class SettingMode {
        protected int MODE;
        protected int duration;

        public SettingMode(int m, int d) {
            setMODE(m);
            setDuration(d);
        }

        public int getMODE() {
            return MODE;
        }

        public void setMODE(int MODE) {
            this.MODE = MODE;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    protected class TimeFrame {
        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getStop() {
            return stop;
        }

        public void setStop(int stop) {
            this.stop = stop;
        }

        protected int start;
        protected int stop;

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        protected int mode;

        public TimeFrame(int start, int stop, int mode) {
            setStart(start);
            setStop(stop);
            setMode(mode);
        }

    }

    public abstract int getLevel();

    public abstract List<SettingMode> getSettings();

    protected List<TimeFrame> frames;

    public LevelSettings() {
        proc(getSettings());
    }

    protected void proc(List<SettingMode> settings) {
        frames = new ArrayList<TimeFrame>();
        int clock = 0;
        for (int i = 0; i < settings.size(); i++) {
            SettingMode setting = settings.get(i);
            frames.add(new TimeFrame(clock, clock + setting.getDuration(), setting.getMODE()));
            clock += setting.getDuration();
        }
    }

    public int whichMode(int seconds) {

        if (frames.size() > 0) {
            while (frames.size() > 0 && frames.get(0).getStop() < seconds)
                frames.remove(0);

            if (frames.size() > 0)
                return frames.get(0).getMode();
            else
                return GameController.MODE_CHASE;
        } else
            return GameController.MODE_CHASE;
    }


}
