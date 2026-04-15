package org.eam.games.poj.engine.tile;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eam.games.poj.drawable.WithImage;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
class TilesetMetadata implements WithImage {

    @Nonnull
    private String tilesetFilename;
    private int tileSize;
    @Nonnull
    private List<TileMetadata> tiles;

    @SneakyThrows
    List<TileView<TileType>> tiles(Function<String, TileType> typeFromString) {
        BufferedImage tilesetImage = fromResource(tilesetFilename);

        return tiles.stream()
            .map(m -> toTileView(tilesetImage, m, typeFromString))
            .collect(Collectors.toList());
    }

    private TileView<TileType> toTileView(BufferedImage tilesetImage, TileMetadata metadata,
        Function<String, TileType> typeFromString) {
        BufferedImage image = tilesetImage.getSubimage(metadata.getCol() * tileSize, metadata.getRow() * tileSize,
            tileSize, tileSize);
        return TileView.from(image, metadata, typeFromString);
    }

}
