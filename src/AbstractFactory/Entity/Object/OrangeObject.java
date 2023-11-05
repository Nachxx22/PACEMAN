package AbstractFactory.Entity.Object;

import AbstractFactory.Entity.Object.Object;

import javax.swing.*;

public class OrangeObject extends Object {
    public OrangeObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("src/images/orange.png").getImage());
    }
}
