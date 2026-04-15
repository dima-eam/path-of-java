package org.eam.games.poj.engine;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import org.eam.games.poj.world.Cell;

/**
 * Mutable representation of a tile coordinates in the world, measured in tiles (not pixels) from top-left corner.
 * Allows to simulate movement via changing object state, which is tracked by movement controller.
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class Position {

    @Exclude
    private final Cell start;
    private int xTile;
    private int yTile;

    public Position(Cell cell) {
        start = cell;
        xTile = cell.getXTile();
        yTile = cell.getYTile();
    }

    static Position from(Cell cell) {
        return new Position(cell);
    }

    Position down() {
        return new Position(start, xTile, yTile + 1);
    }

    Position up() {
        return new Position(start, xTile, yTile - 1);
    }

    Position left() {
        return new Position(start, xTile - 1, yTile);
    }

    Position right() {
        return new Position(start, xTile + 1, yTile);
    }

    void moveTo(Position position) {
        xTile = position.xTile;
        yTile = position.yTile;
    }

    int xOffset(int xOffset) {
        return xTile - xOffset;
    }

    int yOffset(int yOffset) {
        return yTile - yOffset;
    }

    public void reset() {
        xTile = start.getXTile();
        yTile = start.getYTile();
    }

}
