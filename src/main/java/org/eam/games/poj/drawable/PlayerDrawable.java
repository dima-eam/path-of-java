package org.eam.games.poj.drawable;

import lombok.AllArgsConstructor;
import org.eam.games.poj.actor.Actor;
import org.eam.games.poj.engine.PlayerMovement;

/**
 * Controls player drawing logic. Player's character has its current position and fixed step, controlling how many tiles
 * can be passed in one turn, considering the game world scrolling to not move more tiles than expected. Requires
 * player's {@link Actor} reference to determine an image for a direction.
 */
@AllArgsConstructor
public class PlayerDrawable implements Drawable {

    private final Actor actor;
    private final PlayerMovement movement;

    /**
     * @inheritDoc
     */
    @Override
    public void draw(GraphicsContext context) {
        context.process(actor.imageForDirection(movement.direction()),
            movement.xOffset(context.getXOffset()), movement.yOffset(context.getYOffset()));
    }

}
