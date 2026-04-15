package org.eam.games.poj.engine;

import java.util.Random;

public final class Dice {

    private static final Random RANDOM = new Random();

    public static int rollDice() {
        return RANDOM.nextInt(6) + 1;
    }

    public static int rollDice(int sides) {
        return RANDOM.nextInt(sides) + 1;
    }

}
