package AbstractFactory.Entity.Object;

import AbstractFactory.Entity.Object.Object;

import javax.swing.*;

public class StrawberryObject extends Object {
    public StrawberryObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("src/images/strawberry.png").getImage());
    }
}
