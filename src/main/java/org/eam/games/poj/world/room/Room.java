package org.eam.games.poj.world.room;

import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.HORIZONTAL;
import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.LOWER_LEFT;
import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.LOWER_RIGHT;
import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.UPPER_LEFT;
import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.UPPER_RIGHT;
import static org.eam.games.poj.engine.tile.OrientedTileset.Orientation.VERTICAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.eam.games.poj.engine.tile.Tile;
import org.eam.games.poj.engine.tile.OrientedTiles;

/**
 * Set of tiles representing a single room, with walls, corners, and floor. Each room has a location and size, defined
 * by upper-left corner coordinates (global), plus width and height.
 */
@RequiredArgsConstructor
class Room {

    private static final Random RANDOM = new Random();
    private static final Tile FLOOR = new Tile("/tiles/floor/floor.png", false);

    private final Map<Coordinates, Tile> layout = new HashMap<>();

    private final int xCell;
    private final int yCell;
    private final int width;
    private final int height;

    public static Room empty(Coordinates coordinates) {
        return new Room(coordinates.getX(), coordinates.getY(), 0, 0);
    }

    /**
     * Generates a room as walls, then corners, then floor, starting from given coordinates. Height and width are random
     * in some short interval of values, like 4-8 tiles.
     */
    static Room room(int xCell, int yCell, OrientedTiles tiles) {
        int width = RANDOM.nextInt(6, 9);
        int height = RANDOM.nextInt(6, 9);
        Room room = new Room(xCell, yCell, width, height);

        for (int x = xCell; x < xCell + width; x++) {
            room.layout.put(new Coordinates(x, yCell), tiles.nextTile(HORIZONTAL));
            room.layout.put(new Coordinates(x, yCell + height - 1), tiles.nextTile(HORIZONTAL));
        }

        for (int y = yCell; y < yCell + height; y++) {
            room.layout.put(new Coordinates(xCell, y), tiles.nextTile(VERTICAL));
            room.layout.put(new Coordinates(xCell + width - 1, y), tiles.nextTile(VERTICAL));
        }

        room.layout.put(new Coordinates(xCell, yCell), tiles.nextTile(UPPER_LEFT));
        room.layout.put(new Coordinates(xCell, yCell + height - 1), tiles.nextTile(LOWER_LEFT));
        room.layout.put(new Coordinates(xCell + width - 1, yCell), tiles.nextTile(UPPER_RIGHT));
        room.layout.put(new Coordinates(xCell + width - 1, yCell + height - 1), tiles.nextTile(LOWER_RIGHT));

        for (int x = xCell + 1; x < xCell + width - 1; x++) {
            for (int y = yCell + 1; y < yCell + height - 1; y++) {
                room.layout.put(new Coordinates(x, y), FLOOR);
            }
        }

        return room;
    }

    /**
     * Generates a passage between two room as another room with some special properties, like no tiles in walls.
     */
    public static Room verticalPassage(int x, int y, int length, OrientedTiles tiles) {
        Room room = new Room(x, y, 3, length);
        for (int i = 0; i < room.height; i++) {
            room.layout.put(new Coordinates(x, y + i), tiles.nextTile(VERTICAL));
            room.layout.put(new Coordinates(x + 1, y + i), FLOOR);
            room.layout.put(new Coordinates(x + room.width - 1, y + i), tiles.nextTile(VERTICAL));
        }
        room.layout.put(new Coordinates(x, y), tiles.nextTile(UPPER_RIGHT));
        room.layout.put(new Coordinates(x + 2, y), tiles.nextTile(UPPER_LEFT));
        room.layout.put(new Coordinates(x, y + length - 1), tiles.nextTile(LOWER_RIGHT));
        room.layout.put(new Coordinates(x + 2, y + length - 1), tiles.nextTile(LOWER_LEFT));

        return room;
    }

    public static Room horizontalPassage(int x, int y, int length, OrientedTiles tiles) {
        Room room = new Room(x, y, length, 3);
        for (int i = 0; i < room.width; i++) {
            room.layout.put(new Coordinates(x + i, y), tiles.nextTile(HORIZONTAL));
            room.layout.put(new Coordinates(x + i, y + 1), FLOOR);
            room.layout.put(new Coordinates(x + i, y + room.height - 1), tiles.nextTile(HORIZONTAL));
        }
        room.layout.put(new Coordinates(x, y), tiles.nextTile(LOWER_LEFT));
        room.layout.put(new Coordinates(x, y + 2), tiles.nextTile(UPPER_LEFT));
        room.layout.put(new Coordinates(x + length - 1, y), tiles.nextTile(LOWER_RIGHT));
        room.layout.put(new Coordinates(x + length - 1, y + 2), tiles.nextTile(UPPER_RIGHT));

        return room;
    }

    Coordinates startCell() {
        return new Coordinates(xCell, yCell);
    }

    Coordinates endCell() {
        return new Coordinates(xCell + width - 1, yCell + height - 1);
    }

    void toLayout(Coordinates worldStart, List<List<Tile>> worldLayout) {
        layout.forEach((cell, tile) -> worldLayout.get(cell.getX() + Math.abs(worldStart.getX()))
            .set(cell.getY() + Math.abs(worldStart.getY()), tile));
    }

}
