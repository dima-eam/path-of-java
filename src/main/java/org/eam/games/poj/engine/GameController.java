package org.eam.games.poj.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import lombok.extern.log4j.Log4j2;

/**
 * Processes key events for the whole game, like exit, menus etc.
 */
@Log4j2
public class GameController extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            default:
        }
    }

    private void nextLevel() {
        Random r = new Random();
//        player.updateLevel();
//        monsterAmount = r.nextInt(5) + INITIAL_MONSTER_AMOUNT;
//        initGame();
    }

}

