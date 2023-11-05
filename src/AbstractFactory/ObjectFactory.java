package AbstractFactory;

import AbstractFactory.Entity.Enemy.*;
import AbstractFactory.Entity.Object.*;
import AbstractFactory.Entity.Object.Object;
import AbstractFactory.Entity.Object.OrangeObject;
import AbstractFactory.Entity.Object.StrawberryObject;

import java.awt.*;

public class ObjectFactory implements AbstractFactory {

    @Override
    public Enemy createEnemy(String enemyType) {
        throw new UnsupportedOperationException("ObjectFactory does not create enemies.");
    }
    @Override
    public  Object createObject(String objectType) {
        switch (objectType) {
            case "Apple":
                return new AppleObject(0,0);
            case "Cherry":
                return new CherryObject(0,0);
            case "Strawberry":
                return new StrawberryObject(0,0);
            case "Orange":
                return new OrangeObject(0,0);
            default:
                throw new IllegalArgumentException("Invalid object type: " + objectType);
        }
    }
}
