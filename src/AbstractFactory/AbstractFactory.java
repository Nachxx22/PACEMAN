package src.AbstractFactory;


import src.AbstractFactory.Entity.Enemy.*;
import src.AbstractFactory.Entity.Object.Object;

public interface AbstractFactory {
    Enemy createEnemy(String enemyType);
    Object createObject(String objectType);
}
