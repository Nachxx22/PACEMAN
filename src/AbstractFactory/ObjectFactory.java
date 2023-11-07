package AbstractFactory;

import AbstractFactory.Entity.Enemy.*;
import AbstractFactory.Entity.Object.*;
import AbstractFactory.Entity.Object.Object;
import AbstractFactory.Entity.Object.PastillaObject;
import AbstractFactory.Entity.Object.StrawberryObject;

import java.util.Random;

public class ObjectFactory implements AbstractFactory {
    Random random = new Random();
    Integer x = random.nextInt(356);
    Integer y = random.nextInt(356);

    @Override
    public Enemy createEnemy(String enemyType) {
        throw new UnsupportedOperationException("ObjectFactory does not create enemies.");
    }
    @Override
    public  Object createObject(String objectType) {

        switch (objectType) {
            case "Apple":
                System.out.println("creando objeto"+"Apple");
                return new AppleObject(x,y);
            case "Cherry":
                System.out.println("creando objeto"+"Cherry");
                return new CherryObject(x,y);
            case "Strawberry":
                System.out.println("creando objeto"+"Strawberry");
                return new StrawberryObject(x,y);
            case "Pastilla":
                System.out.println("creando objeto"+"Pastilla");
                return new PastillaObject(x,y);
            default:
                throw new IllegalArgumentException("Invalid object type: " + objectType);
        }
    }
}
