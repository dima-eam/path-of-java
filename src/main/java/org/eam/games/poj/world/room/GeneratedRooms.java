package org.eam.games.poj.world.room;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class GeneratedRooms {

    private final int width;
    private final int height;
    private final Coordinates worldStart;
    private final List<Room> rooms;

}
