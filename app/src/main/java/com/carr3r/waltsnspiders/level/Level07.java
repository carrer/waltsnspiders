package com.carr3r.waltsnspiders.level;

import com.carr3r.waltsnspiders.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wneto on 09/11/2015.
 */
public class Level07 extends LevelSettings {

    public int getLevel() {
        return 7;
    }

    @Override
    public List<SettingMode> getSettings() {
        List<SettingMode> modes = new ArrayList<>();
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        modes.add(new SettingMode(GameController.MODE_CHASE, 15));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        modes.add(new SettingMode(GameController.MODE_CHASE, 15));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 5));
        modes.add(new SettingMode(GameController.MODE_CHASE, 15));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 5));
        return modes;
    }
}
