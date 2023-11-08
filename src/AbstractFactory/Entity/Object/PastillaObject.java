package src.AbstractFactory.Entity.Object;

import javax.swing.*;

public class PastillaObject extends Object {
    public PastillaObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("src/images/pastilla.png").getImage(),500);
    }
}
