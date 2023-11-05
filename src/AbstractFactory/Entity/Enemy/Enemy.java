package AbstractFactory.Entity.Enemy;
import java.awt.*;
import javax.swing.ImageIcon;

public abstract class Enemy {
    protected Integer x;
    protected Integer y;
    protected Integer dx;
    protected Integer dy;
    protected Integer speed;
    protected Image image;

    public Enemy(Integer x, Integer y, Integer speed, Image image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = image;
    }

    public abstract void move();

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getImageName() {
        return image.toString();
    }
}

