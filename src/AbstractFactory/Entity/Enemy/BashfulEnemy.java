package AbstractFactory.Entity.Enemy;

import javax.swing.*;

public class BashfulEnemy extends Enemy {
    public BashfulEnemy(Integer x, Integer y) {
        super(x, y, 1, new ImageIcon("src/images/Bashful.gif").getImage());
    }

    @Override
    public void move() {
        // Move towards a point calculated based on Pacman's position and the position of another enemy
    }
}
