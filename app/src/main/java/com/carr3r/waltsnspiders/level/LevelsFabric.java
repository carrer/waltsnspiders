package com.carr3r.waltsnspiders.level;

/**
 * Created by Neto on 28/10/2015.
 */
public class LevelsFabric {

    public static LevelSettings getLevel(int level) {
        switch (level) {
            case 0:
                return new Level00();
            case 1:
                return new Level01();
            case 2:
                return new Level02();
            case 3:
                return new Level03();
            case 4:
                return new Level04();
            case 5:
                return new Level05();
            case 6:
                return new Level06();
            case 7:
                return new Level07();
            case 8:
                return new Level08();
            default:
                return new Level09();
        }
    }

}
