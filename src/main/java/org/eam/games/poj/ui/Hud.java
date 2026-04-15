package org.eam.games.poj.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eam.games.poj.actor.WithStats;
import org.eam.games.poj.drawable.Drawable;
import org.eam.games.poj.drawable.GraphicsContext;
import org.eam.games.poj.engine.GameState;

/**
 * Represents heads-up display with game statistics etc.
 */
@RequiredArgsConstructor
public class Hud extends KeyAdapter implements Drawable {

    private static final int STAT_WIDTH = 330;
    private static final int STAT_HEIGHT = 55;
    private static final int STAT_SIZE = 15;
    private static final int STAT_POSX = 1800 - STAT_WIDTH;
    private static final int STAT_HERO_POSY = 15;
    private static final int STAT_MONSTER_POSY = STAT_HERO_POSY + STAT_HEIGHT;
    private static final int STAT_BOX_POSX = STAT_POSX - 5;
    private static final int STAT_BOX_POSY = 0;
    private static final int STAT_BOX_WIDTH = STAT_WIDTH + 5;
    private static final int STAT_BOX_HEIGHT = STAT_HEIGHT;
    private static final int GAMEOVER_BOX_POSX = 300;
    private static final int GAMEOVER_BOX_POSY = 270;
    private static final int GAMEOVER_BOX_WIDTH = 460;
    private static final int GAMEOVER_BOX_HEIGHT = 70;
    private static final int GAMEOVER_SIZE = 50;
    private static final int GAMEOVER_POSX = GAMEOVER_BOX_POSX + 5;
    private static final int GAMEOVER_POSY = GAMEOVER_BOX_POSY + GAMEOVER_SIZE;

    private final WithStats playerStats;
    private final WithStats positionStats;
    private final WithStats monsterStats;
    private final GameState gameState;
    private Runnable callback = () -> {
    };

    @Override
    public void draw(GraphicsContext context) {
        context.process(g -> {
            g.setColor(Color.white);
            g.setFont(new Font("Courier", Font.PLAIN, STAT_SIZE));
            g.fillRect(STAT_BOX_POSX, STAT_BOX_POSY, STAT_BOX_WIDTH, STAT_BOX_HEIGHT);
            g.setColor(Color.black);
            g.drawString(playerStats.stats(), STAT_POSX, STAT_HERO_POSY);
            g.drawString(positionStats.stats(), STAT_POSX, STAT_HERO_POSY + 17);
            g.drawString(monsterStats.stats(), STAT_POSX, STAT_HERO_POSY + 32);
        });

        if (gameState.isFinal()) {
            context.process(g -> {
                g.setColor(Color.white);
                g.fillRect(GAMEOVER_BOX_POSX, GAMEOVER_BOX_POSY, GAMEOVER_BOX_WIDTH, GAMEOVER_BOX_HEIGHT);

                g.setColor(Color.RED);
                g.setFont(new Font("Courier", Font.PLAIN, GAMEOVER_SIZE));
                g.drawString("You are Dead!!! Press F to start again", GAMEOVER_POSX, GAMEOVER_POSY);
            });
        }else{

        }
    }

    @SneakyThrows
    public void gameOver(Runnable callback) {
        this.callback = callback;

        gameState.gameOver();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState.isFinal() && e.getKeyCode() == KeyEvent.VK_F) {
            gameState.restart();
            callback.run();
        }
    }

}
