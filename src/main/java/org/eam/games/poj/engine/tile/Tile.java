package org.eam.games.poj.engine.tile;

import java.awt.Image;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.eam.games.poj.drawable.WithImage;

/**
 * Simple representation of a tile, as its image and some properties for interaction with it.
 */
@ToString(exclude = {"tileImage"})
@AllArgsConstructor
public class Tile implements WithImage {

    @Getter
    private final Image tileImage;
    private final boolean isSolid;

    public Tile(String filename, boolean isSolid) {
        this.isSolid = isSolid;

        tileImage = fromResource(filename);
    }

    public Tile(Path path, boolean isSolid) {
        this.isSolid = isSolid;

        tileImage = fromPath(path);
    }

    public boolean isSolid() {
        return isSolid;
    }

}
