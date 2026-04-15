package org.eam.games.poj.engine.tile;

import java.util.List;
import java.util.Map;

public interface Tileset {

    Map<TileType, List<Tile>> tileset();

}
