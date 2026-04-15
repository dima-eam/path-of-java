package org.eam.games.poj.world;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Immutable representation of a tile coordinates in the world, measured in tiles (not pixels) from top-left corner.
 * Value is absolute regardless how many tiles the screen fits, and normally has value between 0 and world size, e.g.
 * 1000x1000 tiles.
 */
@AllArgsConstructor
@Value
public class Cell {

    private final int xTile;
    private final int yTile;

    /**
     * Checks if current cell is between 0 and given boundaries.
     */
    public boolean within(int width, int height) {
        return xTile >= 0 && xTile < width && yTile >= 0 && yTile < height;
    }

}
