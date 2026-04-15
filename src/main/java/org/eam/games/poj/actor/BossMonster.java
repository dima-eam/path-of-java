package org.eam.games.poj.actor;

import java.awt.Image;
import java.util.Map;

public class BossMonster extends Monster {

    private static final String BOSS_IMAGE = "/tiles/boss.png";

    private final Map<Direction, Image> imagesByDirection = Map.of(
        Direction.RIGHT, fromResource(BOSS_IMAGE),
        Direction.LEFT, fromResource(BOSS_IMAGE),
        Direction.UP, fromResource(BOSS_IMAGE),
        Direction.DOWN, fromResource(BOSS_IMAGE)
    );

    public BossMonster() {
        super();
//        maxHealthPoint = 2 * level * rollDice() + rollDice();
//        defendPoint = (int) Math.ceil(level / 2.0 * rollDice() + rollDice() / 2.0);
//        strikePoint = level * rollDice() + getLevel();
//        healthPoint = maxHealthPoint;
    }

    @Override
    Map<Direction, Image> imagesByDirection() {
        return imagesByDirection;
    }

}
