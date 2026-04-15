package org.eam.games.poj.actor;

import static org.eam.games.poj.engine.Dice.rollDice;

import java.awt.Image;
import java.util.Map;
import org.eam.games.poj.engine.tile.HeroTiles;

/**
 * Game character, controlled by player.
 */
public class Player extends Actor {

    private static final HeroTiles TILES = new HeroTiles();

    public Player() { // todo externalize
        super(20 + 3 * rollDice(), 2 * rollDice(), 5 + rollDice());
    }

    @Override
    Map<Direction, Image> imagesByDirection() {
        return TILES.imagesByDirection();
    }

    @Override
    public String stats() {
        return "Hero(" + level + ")" + // todo hero name input
            " | HP: " + healthPoint + "/" + maxHealthPoint +
            " | ATK: " + strikePoint +
            " | DEF: " + defendPoint;
    }

    @Override
    public void reset() {
        super.reset();

        maxHealthPoint = 20 + 3 * rollDice();
        healthPoint = maxHealthPoint;
        defendPoint = 2 * rollDice();
        strikePoint = 5 + rollDice();
    }

}


