package AbstractFactory.Entity.Object;
import java.awt.*;
import javax.swing.ImageIcon;

public abstract class Object {
    protected Integer x;
    protected Integer y;
    protected Image image;

    public Object(Integer x, Integer y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
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

    public String getImageName() {
        return image.toString();
    }
}

