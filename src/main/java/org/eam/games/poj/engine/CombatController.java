package org.eam.games.poj.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.actor.Actor;
import org.eam.games.poj.actor.Monster;
import org.eam.games.poj.ui.Hud;

@Log4j2
@AllArgsConstructor
public class CombatController extends MouseAdapter {

    private final Position heroPosition;
    private final Actor hero;
    private final Monsters monsters;
    private final Hud hud;

    @Override
    public void mouseClicked(MouseEvent e) {
        monsters.aroundCell(heroPosition)
            .forEach(m -> combatRound(hero, m));
    }

    private void combatRound(Actor hero, Monster monster) {
        log.info("Combat round: hero={}, monster={}", hero, monster);
        hero.attack(monster);
        if (monster.dead()) {
            log.info("Monster killed, getting stronger");
            hero.levelUp();
            monsters.getStronger();

            return;
        }

        monster.attack(hero);
        if (hero.dead()) {
            log.info("Game over");
            hud.gameOver(this::reset);
        }
    }

    private void reset() {
        hero.reset();
        monsters.reset();
        heroPosition.reset();
    }

}
