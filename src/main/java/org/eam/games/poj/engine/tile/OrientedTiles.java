package org.eam.games.poj.engine.tile;

import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.eam.games.poj.engine.tile.OrientedTileset.Orientation;

/**
 * Randomized access to various tiles by direction.
 */
@AllArgsConstructor
public class OrientedTiles {

    private static final Random RANDOM = new Random();

    private final Tileset tileset;

    public Tile nextTile(Orientation orientation) {
        List<Tile> tiles = tileset.tileset().get(orientation);
        if (tiles.size() == 1) {
            return tiles.get(0);
        }

        return tiles.get(RANDOM.nextInt(2));
    }

}
