package Client.src.AbstractFactory;


import Client.src.AbstractFactory.Entity.Enemy.*;
import Client.src.AbstractFactory.Entity.Object.Object;

public interface AbstractFactory {
    Enemy createEnemy(String enemyType);
    Object createObject(String objectType);
}
