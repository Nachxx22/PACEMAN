package Client.src.AbstractFactory;


import Client.src.AbstractFactory.Entity.Enemy.*;
import Client.src.AbstractFactory.Entity.Object.Object;


public class EnemyFactory implements AbstractFactory {
    @Override
    public  Enemy createEnemy(String enemyType) {
        switch (enemyType) {
            case "Shadow":
                return new ShadowEnemy(0,0);
            case "Speedy":
                return new SpeedyEnemy(0,0);
            case "Bashful":
                return new BashfulEnemy(0,0);
            case "Pokey":
                return new PinkyEnemy(0,0);
            default:
                throw new IllegalArgumentException("Invalid enemy type: " + enemyType);
        }
    }

    @Override
    public Object createObject(String objectType) {
        throw new UnsupportedOperationException("EnemyFactory does not create objects.");
    }
}

