package org.eam.games.poj.ui;

import com.google.common.base.Stopwatch;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.swing.JPanel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.drawable.Drawable;
import org.eam.games.poj.drawable.GraphicsContext;
import org.eam.games.poj.drawable.MonstersDrawable;
import org.eam.games.poj.engine.Camera;

/**
 * Controls all graphics in the game, utilizing {@link javax.swing.JComponent#paintComponent(Graphics)} functionality,
 * passing {@link Graphics} instances to every drawable object to update.
 */
@Log4j2
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Display extends JPanel {

    @Nonnull
    private final Camera camera;
    @Nonnull
    private final Drawable world;
    @Nonnull
    private final Drawable hero;
    @Nonnull
    private final Drawable hud;
    @Nonnull
    private final Drawable monsters;

    public static Display from(@Nonnull Camera camera,
        @Nonnull Drawable worldDrawable,
        @Nonnull Drawable drawHero,
        @Nonnull Hud hud,
        @Nonnull MonstersDrawable monstersDrawable) {
        Display display = new Display(camera, worldDrawable, drawHero, hud, monstersDrawable);
        display.setDoubleBuffered(true);
        display.setBackground(Color.BLACK);

        return display;
    }

    /**
     * Initially paints the whole game, and repaints by timer.
     */
    @Override
    public void paintComponent(Graphics g) {
        Stopwatch started = Stopwatch.createStarted();
        super.paintComponent(g);
        draw(g);

        // baseline was 0-1 millis
        log.trace("World painted: elapsedMs={}", () -> started.elapsed(TimeUnit.MILLISECONDS));
    }

    /**
     * Updates positions and sprites of registered drawables.
     */
    private void draw(Graphics graphics) {
        GraphicsContext context = camera.moved(graphics);

        world.draw(context); // todo make a collection?
        hero.draw(context);
        monsters.draw(context);
        // HUD must be last, to draw it on top
        hud.draw(context);
    }

}
