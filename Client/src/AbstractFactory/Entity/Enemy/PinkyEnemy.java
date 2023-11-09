package Client.src.AbstractFactory.Entity.Enemy;

import javax.swing.*;

public class PinkyEnemy extends Enemy {
    public PinkyEnemy(Integer x, Integer y) {
        super(x, y, 6, new ImageIcon("Client/src/images/Pinky.gif").getImage(),0,0);
    }

    @Override
    public void move() {
        // Move randomly, but slower than the other enemies
    }
}
