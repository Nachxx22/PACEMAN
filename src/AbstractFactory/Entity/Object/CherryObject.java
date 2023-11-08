package src.AbstractFactory.Entity.Object;

import javax.swing.*;

public class CherryObject extends Object {
    public CherryObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("src/images/cherry.png").getImage(),300);
    }
}
