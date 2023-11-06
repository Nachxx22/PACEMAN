package AbstractFactory.Entity.Enemy;

import AbstractFactory.Entity.Enemy.Enemy;

import javax.swing.*;

public class ShadowEnemy extends Enemy {
    public ShadowEnemy(Integer x, Integer y) {
        super(x, y, 4, new ImageIcon("src/images/Shadow.gif").getImage(),0,0);
    }

    @Override
    public void move() {
        // Move towards Pacman
    }
}
