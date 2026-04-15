package org.eam.games.poj.engine.tile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;

/**
 * Parses a single folder file with all the tiles images, extracting metadata based on naming convention.
 */
public class TilesetFromFolder implements OrientedTileset {

    private final Map<TileType, List<Tile>> tileset = new HashMap<>();

    @SneakyThrows
    public TilesetFromFolder(String tileFolder) {
        Files.list(Path.of(getClass().getResource(tileFolder).toURI()))
            .forEach(this::extractTile);
    }

    private void extractTile(Path path) {
        Tile tile = new Tile(path, true);
        String full = path.toString();
        int begin = 1 + full.lastIndexOf("-");
        int end = full.lastIndexOf(".");
        String suffix = full.substring(begin, end);
        Orientation orientation = Orientation.from(suffix);

        tileset.putIfAbsent(orientation, new ArrayList<>());
        tileset.get(orientation).add(tile);
    }

    @Override
    public Map<TileType, List<Tile>> tileset() {
        return tileset;
    }

}
