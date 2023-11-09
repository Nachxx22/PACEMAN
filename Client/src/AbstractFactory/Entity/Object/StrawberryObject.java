package Client.src.AbstractFactory.Entity.Object;


import javax.swing.*;

public class StrawberryObject extends Object {
    public StrawberryObject(Integer x, Integer y) {
        super(x, y, new ImageIcon("Client/src/images/strawberry.png").getImage(),200);
    }
}
