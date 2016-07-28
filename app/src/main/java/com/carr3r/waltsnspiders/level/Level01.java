package com.carr3r.waltsnspiders.level;

import com.carr3r.waltsnspiders.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wneto on 26/10/2015.
 */
public class Level01 extends LevelSettings {


    public int getLevel() {
        return 1;
    }

    @Override
    public List<SettingMode> getSettings() {
        List<SettingMode> modes = new ArrayList<>();
        modes.add(new SettingMode(GameController.MODE_SCATTER, 15));
        modes.add(new SettingMode(GameController.MODE_CHASE, 10));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 15));
        modes.add(new SettingMode(GameController.MODE_CHASE, 10));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 15));
        modes.add(new SettingMode(GameController.MODE_CHASE, 10));
        modes.add(new SettingMode(GameController.MODE_SCATTER, 15));
        return modes;
    }

}
