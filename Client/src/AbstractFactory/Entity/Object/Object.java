package Client.src.AbstractFactory.Entity.Object;
import java.awt.*;

public abstract class Object {
    protected Integer x;
    protected Integer y;
    protected Image image;
    protected Integer score;

    public Object(Integer x, Integer y, Image image, Integer score) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.score = score;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
    public void setX(Integer e) {
        this.x=e;
    }
    public void setY(Integer e) {
        this.y=e;
    }

    public Integer getScore() {
        return score;
    }

    public String getImageName() {
        return image.toString();
    }
}

