package org.eam.games.poj.engine.tile;

import java.util.Arrays;
import javax.annotation.Nonnull;

/**
 * Represents a set of tiles for an actor, like player or monster, standing in different positions, e.g. face up. Each
 * tile belongs to one of {@link TileType} below, codified like that:
 * <pre>
 * (L)eft, (R)ight, (D)own, (U)p
 * </pre>
 * This allows to select a tile for a specific direction.
 */
public interface ActorTileset extends Tileset {

    enum Direction implements TileType {

        LEFT("l"),
        RIGHT("r"),
        UP("u"),
        DOWN("d");

        private final String suffix;

        Direction(String suffix) {
            this.suffix = suffix;
        }

        static Direction from(@Nonnull String suffix) {
            return Arrays.stream(values())
                .filter(v -> suffix.matches(v.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Suffix not supported: " + suffix));
        }

    }

}
