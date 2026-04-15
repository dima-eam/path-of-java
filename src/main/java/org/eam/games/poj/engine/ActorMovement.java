package org.eam.games.poj.engine;

import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eam.games.poj.actor.Direction;
import org.eam.games.poj.world.World;

/**
 * Base class for moving entities. General purpose is drawing actors based on position and direction and
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class ActorMovement {

    private final int tileSize;
    @Getter
    @Nonnull
    protected final Position current;
    @Nonnull
    protected Direction direction;
    @Nonnull
    protected final World world;

    public int xOffset(int xOffset) {
        return current.xOffset(xOffset) * tileSize;
    }

    public int yOffset(int yOffset) {
        return current.yOffset(yOffset) * tileSize;
    }

    /**
     * Current direction of an entity. Allows to determine what sprite to draw, or some interactions based on
     * direction.
     */
    @Nonnull
    public Direction direction() {
        return direction;
    }

}
