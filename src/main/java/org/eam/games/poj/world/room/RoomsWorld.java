package org.eam.games.poj.world.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.engine.tile.OrientedTiles;
import org.eam.games.poj.engine.tile.OrientedTileset.Orientation;
import org.eam.games.poj.engine.tile.Tile;
import org.eam.games.poj.engine.tile.TilesetFromFile;
import org.eam.games.poj.world.Cell;
import org.eam.games.poj.world.World;

/**
 * Represents game world as a set of randomly generated rooms. Generation uses its own coordinate system, where (0, 0)
 * is the central room of the world, and every new room is generated in one of four possible directions. After
 * generation is done, coordinates are transformed into rectangular grid, starting from top-left corner.
 */
@Log4j2
public final class RoomsWorld implements World {

    private static final Random RANDOM = new Random();

    private static final Tile FILL = new Tile("/tiles/fill-dark.png", true);

    private static final OrientedTiles TILES = new OrientedTiles(
        new TilesetFromFile("/tiles/tilesets/walls.yml", Orientation::from));
    //    private final Tiles tiles = new Tiles(new TilesetFromFolder("/tiles/wall"));

    /**
     * Each element (sub-list) emulates one column of tiles. Allows to access tiles by passing xTile and yTile
     * coordinates, respectively
     */
    private final List<List<Tile>> layout = new ArrayList<>();

    private final int widthInTiles;
    private final int heightInTiles;
    private final Cell start;

    public RoomsWorld() {
        Rooms generator = new Rooms(TILES);
        GeneratedRooms rooms = generator.generate();

        widthInTiles = rooms.getWidth();
        heightInTiles = rooms.getHeight();
        start = new Cell(Math.abs(rooms.getWorldStart().getX()) + 1,
            Math.abs(rooms.getWorldStart().getY()) + 1);

        fillLayout(rooms);
    }

    @Override
    public Optional<Tile> tileForCell(Cell newCell) {
        if (newCell.within(widthInTiles, heightInTiles)) {
            return Optional.of(layout.get(newCell.getXTile()).get(newCell.getYTile()));
        }

        return Optional.empty();
    }

    @Override
    public Cell walkableCell() {
        Cell cell;
        do {
            int x = RANDOM.nextInt(widthInTiles);
            int y = RANDOM.nextInt(heightInTiles);
            cell = new Cell(x, y);
        } while (tileForCell(cell)
            .orElse(FILL)
            .isSolid());

        return cell;
    }

    @Override
    public int widthInTiles() {
        return widthInTiles;
    }

    @Override
    public int heightInTiles() {
        return heightInTiles;
    }

    @Override
    public Cell start() {
        return start;
    }

    private void fillLayout(GeneratedRooms rooms) {
        // fill the whole world with background tile
        for (int x = 0; x < widthInTiles; x++) {
            List<Tile> col = new ArrayList<>();
            for (int y = 0; y < heightInTiles; y++) {
                col.add(FILL);
            }
            layout.add(col);
        }

        // populate layout with shifting every coordinate down and right, to make upper-left room/cell to be at (0,0)
        for (Room room : rooms.getRooms()) {
            room.toLayout(rooms.getWorldStart(), layout);
        }
    }

}
