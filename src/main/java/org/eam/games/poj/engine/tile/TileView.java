package org.eam.games.poj.engine.tile;

import java.awt.image.BufferedImage;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class TileView<T extends TileType> {

    private final BufferedImage image;
    private final TileMetadata metadata;
    private final Function<String, T> typeSupplier;

    static <T extends TileType> TileView<T> from(BufferedImage image, TileMetadata metadata,
        Function<String, T> typeFromString) {
        return new TileView<>(image, metadata, typeFromString);
    }

    T type() {
        return typeSupplier.apply(metadata.getDescriptor());
    }

    Tile toTile(boolean solid) {
        return new Tile(image, true); // todo solidity from metadata
    }

}
