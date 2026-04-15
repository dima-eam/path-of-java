package org.eam.games.poj.engine.tile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eam.games.poj.engine.tile.OrientedTileset.Orientation;

/**
 * Represents all the attributes for a single tile, such as position in a file, id, properties, etc.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
class TileMetadata {

    private int id;
    /**
     * Row represents a logical row in a tileset, starting from 0
     */
    private int row;
    /**
     * Col represents a logical column in a tileset, starting from 0
     */
    private int col;
    /**
     * Any logical descriptor of thi tile. Might be type, orientation, etc. Value is bound to particular tileset
     * implementation, e.g. {@link Orientation}
     */
    private String descriptor;

}
