package org.eam.games.poj.engine.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Parses a single image file with all the tiles, based on tiles metadata, provided in form of {@link TileMetadata}
 * instance, mapped e.g. from YAML file.
 * <p>
 * Note that implementation assumes that {@link TilesetMetadata} instance contains tiles metadata ordered row by row,
 * starting from upper left corner of the image.
 */
public class TilesetFromFile implements Tileset {

    private final Map<TileType, List<Tile>> tileset = new HashMap<>();

    public TilesetFromFile(String metadataFile, Function<String, TileType> typeFromString) {
        TilesetMetadata metadata = TilesetMetadataFactory.fromFile(metadataFile);

        for (TileView<TileType> tileView : metadata.tiles(typeFromString)) {
            TileType type = tileView.type();
            tileset.putIfAbsent(type, new ArrayList<>());
            tileset.get(type).add(tileView.toTile(true));
        }
    }

    @Override
    public Map<TileType, List<Tile>> tileset() {
        return tileset;
    }

}
