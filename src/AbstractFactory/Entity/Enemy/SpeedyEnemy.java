package AbstractFactory.Entity.Enemy;

import AbstractFactory.Entity.Enemy.Enemy;

import javax.swing.*;

public class SpeedyEnemy extends Enemy {
    public SpeedyEnemy(Integer x, Integer y) {
        super(x, y, 3, new ImageIcon("src/images/Speedy.gif").getImage());
    }

    @Override
    public void move() {
        // Move towards Pacman, but faster than Shadow
    }
}
