package org.eam.games.poj.properties;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Stores application params. todo config file mapping.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class GameProperties {

    private static final int DEFAULT_TILE_SIZE = 64;

    @Nonnull
    private final Dimension screenSize;
    /**
     * Screen width in tiles, according to screen resolution
     */
    private final int widthInTiles;
    /**
     * Screen height in tiles, according to screen resolution
     */
    private final int heightInTiles;
    /**
     * Tiles size. Assumed all tiles are rectangular
     */
    private final int tileSize;

    /**
     * Creates properties instance with default tiles count and tiles size.
     */
    public static GameProperties defaults() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int widthInTiles =  screenSize.width / DEFAULT_TILE_SIZE;
        int heightInTiles =  screenSize.height / DEFAULT_TILE_SIZE;

        return GameProperties.builder()
            .screenSize(screenSize)
            .widthInTiles(widthInTiles)
            .heightInTiles(heightInTiles)
            .tileSize(DEFAULT_TILE_SIZE)
            .build();
    }

}
