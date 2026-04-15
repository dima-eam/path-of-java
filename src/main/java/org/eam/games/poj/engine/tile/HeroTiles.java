package org.eam.games.poj.engine.tile;

import java.awt.Image;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.eam.games.poj.actor.Direction;

@AllArgsConstructor
public class HeroTiles {

    private final Tileset tileset = new TilesetFromFile("/tiles/tilesets/hero.yml", ActorTileset.Direction::from);
    private final Map<Direction, Image> imagesByDirection = Map.of(
        Direction.RIGHT, tileset.tileset().get(ActorTileset.Direction.RIGHT).get(0).getTileImage(),
        Direction.LEFT, tileset.tileset().get(ActorTileset.Direction.LEFT).get(0).getTileImage(),
        Direction.UP, tileset.tileset().get(ActorTileset.Direction.UP).get(0).getTileImage(),
        Direction.DOWN, tileset.tileset().get(ActorTileset.Direction.DOWN).get(0).getTileImage()
    );

    public Map<Direction, Image> imagesByDirection() {
        return imagesByDirection;
    }

}
