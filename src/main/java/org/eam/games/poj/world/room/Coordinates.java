package org.eam.games.poj.world.room;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Mutable representation of some coordinates, used during world generation.
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
class Coordinates {

    private int x;
    private int y;

    /**
     * Moves current point to a new position.
     */
    void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
