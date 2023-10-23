package Entitys;

import Entitys.EntitysFather.Enemy;
import Entitys.EntitysFather.Objeto;
import Entitys.EntitysSon.Enemys.*;
import Entitys.EntitysSon.Objetos.*;

// Interfaz abstracta para la fábrica
public interface EntityAbstractFactory {
    public Enemy createEnemy();
    public Objeto createObjeto();
}

// Fábrica concreta 1
 class ConcreteFactory1 implements EntityAbstractFactory {
    public Enemy createEnemy() {
        return new Shadow();
    }
    public Objeto createObjeto() {
        return new Fresa();
    }

}

// Fábrica concreta 2
class ConcreteFactory2 implements EntityAbstractFactory {
    public Enemy createEnemy() {
        return new Speedy();
    }
    public Objeto createObjeto() {
        return new Pastilla();
    }
}

// Fábrica concreta 3
 class ConcreteFactory3 implements EntityAbstractFactory {
    public Enemy createEnemy() {
        return new Bashful();
    }
    public Objeto createObjeto() {
        return new Pastilla2();
    }
}

// Fábrica concreta 4
class ConcreteFactory4 implements EntityAbstractFactory {
    public Enemy createEnemy() {
        return new Pokey();
    }
    public Objeto createObjeto() {
        return new Cereza();
    }
}

// Clase Juego que solicita los productos
 class Juego {
    private EntityAbstractFactory factory;
    public Juego(EntityAbstractFactory factory) {
        this.factory = factory;
    }
    public void jugar() {
        Enemy enemy = factory.createEnemy();
        Objeto objeto = factory.createObjeto();
        // Lógica del juego
    }
}