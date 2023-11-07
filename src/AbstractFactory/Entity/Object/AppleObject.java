package AbstractFactory.Entity.Object;

import javax.swing.*;

public class AppleObject extends Object {
    public AppleObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("src/images/apple.png").getImage(),100);
    }
}
