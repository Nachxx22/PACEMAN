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

    public Enemy(Integer x, Integer y, Integer speed, Image image,Integer dx, Integer dy) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = image;
        this.dx=dx;
        this.dy=dy;
    }

    public abstract void move();

    public void draw(Graphics g,Integer x, Integer y) {
        g.drawImage(image, x, y, null);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getSpeed() {
        return speed;
    }
    public void  setX(Integer e) {
        this.x=e;
    }
    public void  setY(Integer e) {
        this.y=e;
    }
    public Integer getdX() {
        return dx;
    }

    public Integer getdY() {
        return dy;
    }

    public void  setdX(Integer e) {
        this.dx=e;
    }
    public void  setdY(Integer e) {
        this.dy=e;
    }

    public String getImageName() {
        return image.toString();
    }
}

