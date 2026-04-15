package org.eam.games.poj.engine;

import java.awt.Graphics;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.drawable.GraphicsContext;
import org.eam.games.poj.properties.GameProperties;
import org.eam.games.poj.world.Cell;
import org.eam.games.poj.world.World;

/**
 * Encapsulates the transition of camera, centered on a specific "character", usually a hero, by evaluating tiles offsets
 * for scrolling.
 */
@Log4j2
@AllArgsConstructor
public class Camera {

    private final World world;
    private final GameProperties properties;
    private PlayerMovement centerOn;

    /**
     * Evaluates tiles offsets for scrolling.
     */
    public GraphicsContext moved(Graphics graphics) {
        int xOffset = xOffset(centerOn.xTile());
        int yOffset = yOffset(centerOn.yTile());

        log.trace("Camera offsets: xOffset={}, yOffset={}", () -> xOffset, () -> yOffset);

        return GraphicsContext.from(graphics, xOffset, yOffset, properties.getTileSize());
    }

    public int xOffset(int xTile) {
        return offset(xTile, properties.getWidthInTiles() >> 1, world.widthInTiles());
    }

    public int yOffset(int yTile) {
        return offset(yTile, properties.getHeightInTiles() >> 1, world.heightInTiles());
    }

    /**
     * Search for a cell for given coordinates in pixels, e.g. when mouse click happens.
     */
    public Cell fromPixels(int x, int y) {
        return null;
    }

    /**
     * Evaluates if correct coordinate is between 0 and threshold, returns 0 while within threshold. When the coordinate
     * is above the threshold, return the offset, allowing scrolling through the game world.
     */
    private static int offset(int coordinate, int threshold, int boundary) {
        if (coordinate < threshold) {
            return 0;
        }
        if (coordinate < boundary - threshold) {
            return coordinate - threshold;
        }

        return boundary - 2 * threshold;
    }

}
