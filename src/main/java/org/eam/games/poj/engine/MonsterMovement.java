package org.eam.games.poj.engine;

import java.awt.Image;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eam.games.poj.actor.Direction;
import org.eam.games.poj.actor.Monster;
import org.eam.games.poj.engine.tile.Tile;
import org.eam.games.poj.world.World;

@Getter
public class MonsterMovement extends ActorMovement {

    @Nonnull
    private final Monster monster;

    MonsterMovement(int tileSize, @Nonnull Monster monster, @Nonnull World world) {
        super(tileSize, Position.from(world.walkableCell()), Direction.DOWN, world);

        this.monster = Objects.requireNonNull(monster, "Monster is null");
    }

    public Image image() {
        return monster.imageForDirection(direction);
    }

    public void react() {
        Directional next;
        do {
            next = next();
        } while (world.tileFor(next.position.getXTile(), next.position.getYTile())
            .map(Tile::isSolid)
            .orElse(true));

        current.moveTo(next.position);
        direction = next.direction;
    }

    private Directional next() {
        return switch (Dice.rollDice(5)) {
            case 1 -> new Directional(current.up(), Direction.UP);
            case 2 -> new Directional(current.down(), Direction.DOWN);
            case 3 -> new Directional(current.left(), Direction.LEFT);
            case 4 -> new Directional(current.right(), Direction.RIGHT);
            default -> new Directional(current, direction);
        };
    }

    @AllArgsConstructor
    private static class Directional {

        private final Position position;
        private final Direction direction;

    }

}
