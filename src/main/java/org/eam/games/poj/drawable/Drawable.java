package org.eam.games.poj.drawable;

import java.awt.Graphics;

/**
 * Main interface for any drawable entity in the game. Utilizes AWT {@link Graphics} context.
 */
@FunctionalInterface
public interface Drawable {

    /**
     * Passes the graphics context to draw/redraw an entity.
     */
    void draw(GraphicsContext context);

}
