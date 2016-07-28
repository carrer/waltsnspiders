package com.carr3r.waltsnspiders.level;

import com.carr3r.waltsnspiders.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wneto on 09/11/2015.
 */
public class Level05 extends LevelSettings {

    public int getLevel() {
        return 5;
    }

    @Override
    public List<SettingMode> getSettings() {
        List<SettingMode> modes = new ArrayList<>();
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        modes.add(new SettingMode(GameController.MODE_CHASE, 13));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        modes.add(new SettingMode(GameController.MODE_CHASE, 13));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        modes.add(new SettingMode(GameController.MODE_CHASE, 13));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 7));
        return modes;
    }
}
