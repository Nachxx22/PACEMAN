package AbstractFactory;

import AbstractFactory.Entity.Enemy.Enemy;
import AbstractFactory.Entity.Object.Object;

public interface AbstractFactory {
    Enemy createEnemy(String enemyType);
    Object createObject(String objectType);
}
