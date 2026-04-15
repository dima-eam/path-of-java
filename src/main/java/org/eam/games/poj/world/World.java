package org.eam.games.poj.world;

import java.util.Optional;
import org.eam.games.poj.engine.tile.Tile;

/**
 * Defines a set of methods for any game world implementation, focused on work with {@link Tile} and {@link Cell}
 * instances for interaction, placement etc.
 */
public interface World {

    /**
     * Returns a walkable cell (the one where a monster/player can stay/walk), primarily for placing entities. There is
     * no restrictions on choosing the cell, it might be random. "Walkability" is controlled by {@link Tile#isSolid()}
     * property, and should be false.
     */
    Cell walkableCell();

    /**
     * Optional tile for world generation, building a path for a monster, or for other checks. Tries to find a tile for
     * given cell. Might be empty if the world is not rectangular, or given coordinates have no tile.
     */
    Optional<Tile> tileForCell(Cell newCell);

    /**
     * Same as above but for coordinates, e.g. when calculating offset for a camera.
     */
    default Optional<Tile> tileFor(int xTile, int yTile) {
        return tileForCell(new Cell(xTile, yTile));
    }

    int widthInTiles();

    int heightInTiles();

    /**
     * Default start position is random walkable cell.
     */
    default Cell start() {
        return walkableCell();
    }

}
