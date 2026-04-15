//package org.eam.games.wanderer.world;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//import lombok.extern.log4j.Log4j2;
//import org.eam.games.wanderer.engine.tile.Tile;
//
///**
// * Represents game world (or, "labyrinth"), randomly generated at instantiation from two possible tiles: wall, and
// * grass. Generated structure is typically bigger than one screen, so drawing system must take care of not drawing
// * images, which are beyond the screen.
// */
//@Log4j2
//public final class MazeWorld implements World {
//
//    private static final Random RANDOM = new Random();
//    private static final Tile WALL = new Tile("/tiles/wall.jpg", true);
//    private static final Tile GRASS = new Tile("/tiles/fill-dark.png", false);
//
//    /**
//     * Each element (sub-list) emulates one row of tiles. Allows to access tiles by passing xTile and yTile coordinates,
//     * respectively
//     */
//    private final List<List<Tile>> layout = new ArrayList<>();
//
//    private final int widthInTiles;
//    private final int heightInTiles;
//
//    public MazeWorld(int widthInTiles, int heightInTiles) {
//        this.widthInTiles = widthInTiles;
//        this.heightInTiles = heightInTiles;
//
//        generate();
//    }
//
//    /**
//     * Returns an optional tile for world generation, building a path for a monster, or for other checks.
//     */
//    @Override
//    public Optional<Tile> getTile(int xTile, int yTile) {
//        if (xTile < 0 || xTile >= widthInTiles || yTile < 0 || yTile >= heightInTiles) {
//            return Optional.empty();
//        } else {
//            return Optional.of(layout.get(xTile).get(yTile));
//        }
//    }
//
//    @Override
//    public Cell emptyCell() {
//        int x;
//        int y;
//
//        do {
//            x = RANDOM.nextInt(widthInTiles);
//            y = RANDOM.nextInt(heightInTiles);
//        } while (getTile(x, y).orElse(WALL).isSolid());
//
//        return new Cell(x, y);
//    }
//
//    @Override
//    public int xOffset(int xTile) {
//        throw new IllegalStateException();
//    }
//
//    @Override
//    public int yOffset(int yTile) {
//        throw new IllegalStateException();
//    }
//
//    /**
//     * Generation logic first fill the entire world with walls, and then "carves" the maze with grass tiles. Starting
//     * position (0, 0) id always a grass tile. Carving logic is recursive, starts from tile (0, 0), and then replaces
//     * adjacent tiles with grass until maze border is reached.
//     */
//    private void generate() {
//        for (int x = 0; x < widthInTiles; x++) {
//            List<Tile> row = new ArrayList<>();
//            for (int y = 0; y < heightInTiles; y++) {
//                row.add(WALL);
//            }
//            layout.add(row);
//        }
//
//        layout.get(0).set(0, GRASS);
//        carve(0, 0);
//    }
//
//    private void carve(int x, int y) {
//        int[] moveX = {1, -1, 0, 0};
//        int[] moveY = {0, 0, 1, -1};
//
//        int dir = RANDOM.nextInt(4);
//        int count = 0;
//
//        while (count < 4) {
//            int x1 = x + moveX[dir];
//            int y1 = y + moveY[dir];
//            int x2 = x1 + moveX[dir];
//            int y2 = y1 + moveY[dir];
//            if (getTile(x2, y2).orElse(GRASS).isSolid()) {
//                layout.get(x1).set(y1, GRASS);
//                layout.get(x2).set(y2, GRASS);
//                carve(x2, y2);
//            } else {
//                dir = (dir + 1) % 4;
//                count += 1;
//            }
//        }
//    }
//
//}
//
