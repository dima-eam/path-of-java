package org.eam.games.poj.world.room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import lombok.AllArgsConstructor;
import org.eam.games.poj.engine.tile.OrientedTiles;
import org.eam.games.poj.engine.tile.Tileset;

/**
 * Encapsulates rooms generation logic, using provided {@link Tileset}. Generation start from the middle of the world,
 * and recursively adds neighbor rooms until depth limit is reached. The result is a set of rooms connected with narrow
 * corridors. The result is {@link GeneratedRooms} instance with all the rooms and additional data.
 */
@AllArgsConstructor
class Rooms {

    private static final Random RANDOM = new Random();

    private final OrientedTiles tiles;

    /**
     * Creates rooms starting from the middle of the world, going four directions recursively.
     */
    GeneratedRooms generate() {
        Coordinates worldStart = new Coordinates(0, 0);
        Coordinates worldEnd = new Coordinates(0, 0);
        List<Room> rooms = new ArrayList<>(15);
        String startDirection = "S";

        generateNeighbors(Room.empty(worldStart), startDirection, worldStart, worldEnd, rooms, 0);

        int width = 1 + worldEnd.getX() - worldStart.getX();
        int height = 1 + worldEnd.getY() - worldStart.getY();

        return new GeneratedRooms(width, height, worldStart, rooms);
    }

    private void generateNeighbors(Room prevRoom, String direction, Coordinates worldStart, Coordinates worldEnd,
        List<Room> rooms, int depth) {
        if (depth > 15) {
            return;
        }

        Set<String> newDirections = directionsFrom(direction);
        for (String d : newDirections) {
            Coordinates nextRoom = cellForDirection(prevRoom.startCell(), d);
            if (overlaps(nextRoom, rooms)) {
                continue;
            }
            Room room = Room.room(nextRoom.getX(), nextRoom.getY(), tiles);
            rooms.add(room);

            expandStart(worldStart, nextRoom);
            expandEnd(worldEnd, room.endCell());
            if (!d.equals("C")) {
                addPassage(d, prevRoom, room, rooms);
            }

            generateNeighbors(room, d, worldStart, worldEnd, rooms, depth + 1);
        }
    }

    private boolean overlaps(Coordinates nextRoom, List<Room> rooms) {
        if (rooms.isEmpty()) {
            return false;
        }

        int distance = 8;
        return rooms.stream()
            .map(Room::startCell)
            .anyMatch(c -> Math.abs(c.getX() - nextRoom.getX()) < distance
                && Math.abs(c.getY() - nextRoom.getY()) < distance);
    }

    private void addPassage(String direction, Room prevRoom, Room room, List<Room> rooms) {
        switch (direction) {
            case "U" -> addVertical(prevRoom, room, rooms);
            case "D" -> addVertical(room, prevRoom, rooms);
            case "L" -> addHorizontal(prevRoom, room, rooms);
            case "R" -> addHorizontal(room, prevRoom, rooms);
        }
    }

    private Coordinates cellForDirection(Coordinates current, String direction) {
        switch (direction) {
            case "U":
                return new Coordinates(current.getX() + RANDOM.nextInt(-1, 2),
                    current.getY() - RANDOM.nextInt(10, 13));
            case "D":
                return new Coordinates(current.getX() + RANDOM.nextInt(-1, 2), current.getY() + RANDOM.nextInt(10, 13));
            case "L":
                return new Coordinates(current.getX() - RANDOM.nextInt(10, 13),
                    current.getY() + RANDOM.nextInt(-1, 2));
            case "R":
                return new Coordinates(current.getX() + RANDOM.nextInt(10, 13), current.getY() + RANDOM.nextInt(-1, 2));
            case "C":
                return new Coordinates(0, 0);
        }

        throw new IllegalArgumentException("Direction not supported: " + direction);
    }

    private Set<String> directionsFrom(String direction) {
        Set<String> directions = new TreeSet<>(); // to preserve order after shuffling directions
        switch (direction) {
            case "S" -> directions.add("C");
            case "C" -> directions.addAll(shuffle(List.of("U", "D", "L", "R")));
            case "U" -> directions.addAll(pick(List.of("U", "L", "R")));
            case "D" -> directions.addAll(pick(List.of("D", "L", "R")));
            case "L" -> directions.addAll(pick(List.of("U", "L", "D")));
            case "R" -> directions.addAll(pick(List.of("U", "R", "D")));
        }

        return directions;
    }

    private List<String> shuffle(List<String> options) {
        List<String> mutable = new ArrayList<>(options);
        Collections.shuffle(mutable);

        return mutable;
    }

    private Collection<String> pick(List<String> options) {
        return shuffle(options).subList(0, RANDOM.nextInt(1, options.size()));
    }

    private void expandStart(Coordinates worldCell, Coordinates roomCell) {
        int newX = Math.min(worldCell.getX(), roomCell.getX());
        int newY = Math.min(worldCell.getY(), roomCell.getY());

        worldCell.moveTo(newX, newY);
    }

    private void expandEnd(Coordinates worldCell, Coordinates roomCell) {
        int newX = Math.max(worldCell.getX(), roomCell.getX());
        int newY = Math.max(worldCell.getY(), roomCell.getY());

        worldCell.moveTo(newX, newY);
    }

    private void addVertical(Room prevRoom, Room room, List<Room> rooms) {
        int xOffset = 1;
        int x = Math.max(room.startCell().getX(), prevRoom.startCell().getX()) + xOffset;
        int y = room.endCell().getY();
        int length = 1 + prevRoom.startCell().getY() - y;

        rooms.add(Room.verticalPassage(x, y, length, tiles));
    }

    private void addHorizontal(Room prevRoom, Room room, List<Room> rooms) {
        int yOffset = 3;
        int x = room.endCell().getX();
        int y = Math.min(room.endCell().getY(), prevRoom.endCell().getY()) - yOffset;
        int length = 1 + prevRoom.startCell().getX() - x;

        rooms.add(Room.horizontalPassage(x, y, length, tiles));
    }

}
