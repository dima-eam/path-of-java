package org.eam.games.poj.drawable;

import org.eam.games.poj.properties.GameProperties;
import org.eam.games.poj.world.World;

/**
 * Connects game world instance to rendering context. On every update redraws only the visible part of the game world,
 * based on given {@link GraphicsContext}.
 */
public class WorldDrawable implements Drawable {

    private final GameProperties properties;
    private final World world;

    public WorldDrawable(GameProperties properties, World world) {
        this.properties = properties;
        this.world = world;
    }

    @Override
    public void draw(GraphicsContext context) {
        for (int i = 0; i <= properties.getWidthInTiles(); i++) {
            for (int j = 0; j <= properties.getHeightInTiles(); j++) {
                extracted(context, i, j);
            }
        }
    }

    private void extracted(GraphicsContext context, int x, int y) {
        world.tileFor(x + context.getXOffset(), y + context.getYOffset())
            .ifPresent(t -> context.process(t.getTileImage(),
                x * properties.getTileSize(),
                y * properties.getTileSize()));
    }

}
