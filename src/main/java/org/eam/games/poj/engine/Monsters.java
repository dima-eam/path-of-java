package org.eam.games.poj.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.eam.games.poj.actor.Monster;
import org.eam.games.poj.actor.WithStats;
import org.eam.games.poj.properties.GameProperties;
import org.eam.games.poj.world.World;

public class Monsters implements WithStats {

    private static final int COUNT = 40;


    private final Set<WithStats> stats = new HashSet<>();

    private final GameProperties properties;
    private final World world;
    private final Set<MonsterMovement> monsters;
    private final int monsterAmount;

    public Monsters(GameProperties properties, World world) {
        this.properties = properties;
        this.world = world;

        monsterAmount = COUNT;
        monsters = new HashSet<>(monsterAmount);

        populateMonsters();
    }

    public void react(Position player) {
        monsters.forEach(MonsterMovement::react);
    }

    public void forEach(Consumer<MonsterMovement> process) {
        monsters.forEach(process);
    }

    @Override
    public String stats() {
        return stats.stream()
            .map(WithStats::stats)
            .collect(Collectors.joining(","));
    }

    public void getStronger() {
        Iterator<MonsterMovement> iterator = monsters.iterator();
        iterator.forEachRemaining(m -> {
            if (m.getMonster().dead()) {
                iterator.remove();
                stats.remove(m.getMonster());
            } else {
                m.getMonster().levelUp();
            }
        });
    }

    public void reset() {
        monsters.clear();
        stats.clear();

        populateMonsters();
    }

    public List<Monster> aroundCell(Position position) {
        return monsters.stream()
            .filter(m -> around(m.getCurrent(), (position)))
            .map(MonsterMovement::getMonster)
            .peek(stats::add)
            .collect(Collectors.toList());
    }

    private void populateMonsters() {
        for (int i = 0; i < monsterAmount; i++) {
            monsters.add(new MonsterMovement(properties.getTileSize(), new Monster(), world));
        }
//        Monster keyMonster = new Monster(1);
//        monsters.add(new MonsterMovement(world.findEmptyTile(), keyMonster));
//        BossMonster boss = new BossMonster(2);
//        monsters.add(new MonsterMovement(world.findEmptyTile(), boss));
    }

    private boolean around(Position monster, Position position) {
        if (monster.equals(position)) {
            return true;
        }
        return Math.abs(monster.getXTile() - position.getXTile()) < 2
            && Math.abs(monster.getYTile() - position.getYTile()) < 2;
    }

}
