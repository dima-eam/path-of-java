package org.eam.games.poj.engine;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameState {


    private final AtomicBoolean gameOver = new AtomicBoolean();

    public boolean isFinal() {
        return gameOver.get();
    }

    public void gameOver() {
        gameOver.set(true);
    }

    public void restart() {
        gameOver.set(false);
    }
}
