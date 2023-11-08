package src.AbstractFactory.Entity.Enemy;




import javax.swing.*;

public class SpeedyEnemy extends Enemy {
    public SpeedyEnemy(Integer x, Integer y) {
        super(x, y, 8, new ImageIcon("src/images/Speedy.gif").getImage(),0,0);
    }

    @Override
    public void move() {
        // Move towards Pacman, but faster than Shadow
    }
}
