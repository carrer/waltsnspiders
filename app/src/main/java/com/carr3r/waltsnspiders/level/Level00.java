package com.carr3r.waltsnspiders.level;

import com.carr3r.waltsnspiders.GameController;

import java.util.List;

/**
 * Created by wneto on 26/10/2015.
 */
public class Level00 extends LevelSettings {

    public int getLevel() {
        return 0;
    }

    @Override
    protected void proc(List<SettingMode> settings) {
        return;
    }

    @Override
    public List<SettingMode> getSettings() {
        return null;
    }

    @Override
    public int whichMode(int seconds) {
        return seconds % 2 == 0 ? GameController.MODE_CHASE : GameController.MODE_SCATTER;
    }

}
