package AbstractFactory.Entity.Enemy;

import javax.swing.*;

public class PinkyEnemy extends Enemy {
    public PinkyEnemy(Integer x, Integer y) {
        super(x, y, 1, new ImageIcon("src/images/Pinky.gif").getImage());
    }

    @Override
    public void move() {
        // Move randomly, but slower than the other enemies
    }
}
