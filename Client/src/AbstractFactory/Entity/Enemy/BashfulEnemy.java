package Client.src.AbstractFactory.Entity.Enemy;

import javax.swing.*;

public class BashfulEnemy extends Enemy {
    public BashfulEnemy(Integer x, Integer y) {
        super(x, y, 2, new ImageIcon("Client/src/images/Bashful.gif").getImage(),0,0);
    }

    @Override
    public void move() {
        // Move towards a point calculated based on Pacman's position and the position of another enemy
    }
}
