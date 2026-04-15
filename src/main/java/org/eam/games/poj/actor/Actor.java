package org.eam.games.poj.actor;

import static org.eam.games.poj.engine.Dice.rollDice;

import java.awt.Image;
import java.util.Map;
import lombok.ToString;
import org.eam.games.poj.drawable.WithImage;

/**
 * Represents any acting ("alive") entity, such as monster, playable character ("hero") etc. Class fields are mutable,
 * to keep the balance between performance and maintainability, because creating new instance after each hit is way too
 * expensive. To keep control, though, no public accessors are used, and mutation happens only via small set of
 * methods.
 */
@ToString
public abstract class Actor implements WithImage, WithStats {

    protected int maxHealthPoint;
    protected int healthPoint;
    protected int defendPoint;
    protected int strikePoint;
    protected int level;

    public Actor(int maxHealthPoint, int defendPoint, int strikePoint) {
        this.maxHealthPoint = maxHealthPoint;
        this.healthPoint = maxHealthPoint;
        this.defendPoint = defendPoint;
        this.strikePoint = strikePoint;
    }

    public void attack(Actor enemy) {
        int strikeValue = calculateStrikeValue();
        if (strikeValue > enemy.defendPoint) {
            enemy.healthPoint -= strikeValue - enemy.defendPoint;
        }
    }

    public boolean dead() {
        return healthPoint <= 0;
    }

    private int calculateStrikeValue() {
        return 2 * rollDice() + this.strikePoint;
    }

    public void levelUp() {
        maxHealthPoint += rollDice();
        healthPoint = Math.min(healthPoint + maxHealthPoint / 2, maxHealthPoint);
        defendPoint += rollDice();
        strikePoint += rollDice();
        level++;
    }

    public Image imageForDirection(Direction direction) {
        return imagesByDirection().get(direction);
    }

    abstract Map<Direction, Image> imagesByDirection();

    public void reset() {
        level = 0;
    }

}
