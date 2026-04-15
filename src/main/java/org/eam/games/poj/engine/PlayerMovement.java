package org.eam.games.poj.engine;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.actor.Direction;
import org.eam.games.poj.actor.WithStats;
import org.eam.games.poj.engine.tile.Tile;
import org.eam.games.poj.world.World;

/**
 * Encapsulates the current cell of player entity, and controls transition based on direction. Main purpose is movement
 * condition checks and direction for displaying player's entity.
 */
@Log4j2
public class PlayerMovement extends ActorMovement implements WithStats {

    private static final Predicate<Tile> CAN_PASS = tile -> !tile.isSolid();

    private PlayerMovement(int tileSize, Position current, Direction direction, World world) {
        super(tileSize, current, direction, world);
    }

    /**
     * Initial position in given coordinates, face down. Currently, no coordinate checks are done.
     */
    public static PlayerMovement start(int tileSize, World world) {
        return new PlayerMovement(tileSize, Position.from(world.start()), Direction.DOWN, world);
    }

    /**
     * Number of tiles from upper-left corner, X axis.
     */
    public int xTile() {
        return current.getXTile();
    }

    /**
     * Number of tiles from upper-left corner, Y axis.
     */
    public int yTile() {
        return current.getYTile();
    }

    @Override
    public String stats() {
        return "POS: " + current.getXTile() + ", " + current.getYTile() + " | DIR: " + direction;
    }

    /**
     * Changes the direction (always), and location if possible for that direction.
     */
    void move(Direction direction) {
        this.direction = direction;

        Position next = switch (direction) {
            case DOWN -> current.down();
            case UP -> current.up();
            case LEFT -> current.left();
            case RIGHT -> current.right();
        };
        Optional<Tile> tile = world.tileFor(next.getXTile(), next.getYTile());
        tile
            .filter(CAN_PASS)
            .ifPresent(t -> current.moveTo(next));
        log.trace("Moving: direction={}, cell={}, nextTile={}", () -> direction, () -> current, () -> tile);
    }

}
