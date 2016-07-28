package com.carr3r.waltsnspiders.tests;

import com.carr3r.waltsnspiders.GameMap;
import com.carr3r.waltsnspiders.visual.Coordinate;
import com.carr3r.waltsnspiders.visual.Enemy;
import com.carr3r.waltsnspiders.visual.Hero;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class Maps {

    @Test
    public void getOpposite() {
        assertEquals(GameMap.POSITION_DOWN, GameMap.getOpposite(GameMap.POSITION_UP));
        assertEquals(GameMap.POSITION_UP, GameMap.getOpposite(GameMap.POSITION_DOWN));
        assertEquals(GameMap.POSITION_RIGHT, GameMap.getOpposite(GameMap.POSITION_LEFT));
        assertEquals(GameMap.POSITION_LEFT, GameMap.getOpposite(GameMap.POSITION_RIGHT));
    }

    @Test
    public void findPath() {
        Hero hero = new Hero(new Coordinate(2, 2), GameMap.POSITION_RIGHT);
        Enemy enemies[] = new Enemy[4];
        Coordinate target;
        enemies[0] = new Enemy(1);
        enemies[0].setCoordinate(new Coordinate(1, 1));
        enemies[1] = new Enemy(2);
        enemies[2] = new Enemy(3);
        enemies[3] = new Enemy(4);

        target = enemies[0].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy1 exact", hero.getCoordinate().toString(), target.toString());

        target = enemies[1].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy2 easy", new Coordinate(6, 2).toString(), target.toString());

        hero.setOrientation(GameMap.POSITION_UP);
        target = enemies[1].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy2 overflow", new Coordinate(2, -2).toString(), target.toString());

        hero.setOrientation(GameMap.POSITION_RIGHT);
        target = enemies[2].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy3 easy", new Coordinate(7, 3).toString(), target.toString());

        hero.setOrientation(GameMap.POSITION_DOWN);
        hero.getCoordinate().setX(0);
        target = enemies[2].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy3 negative x", new Coordinate(-1, 7).toString(), target.toString());

        hero.setOrientation(GameMap.POSITION_UP);
        hero.getCoordinate().setX(0);
        hero.getCoordinate().setY(0);
        target = enemies[2].getChasingCoordinate(hero, enemies);
        assertEquals("Enemy3 negative x y", new Coordinate(-1, -5).toString(), target.toString());
    }

}
