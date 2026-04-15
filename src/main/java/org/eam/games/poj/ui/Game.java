package org.eam.games.poj.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import org.eam.games.poj.engine.GameTimer;
import org.eam.games.poj.properties.GameProperties;

/**
 * Game instance, derived from {@link JFrame}, adds game {@link Display} to the container to be displayed in borderless
 * full-screen mode, and enables other systems, such as input listeners.
 */
public class Game extends JFrame {

    public static void run(GameProperties properties, Display display,
        Iterable<KeyAdapter> keyAdapters, Iterable<MouseAdapter> mouseAdapters) {
        Game game = new Game();
        game.add(display);
        keyAdapters.forEach(game::addKeyListener);
        mouseAdapters.forEach(game::addMouseListener);

        game.setSize(properties.getScreenSize());
        game.setUndecorated(true);
        game.setVisible(true);

        GameTimer.start(display);
    }

}
