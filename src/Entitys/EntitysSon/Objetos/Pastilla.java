package Entitys.EntitysSon.Objetos;

import Entitys.EntitysFather.Objeto;

public class Pastilla extends Objeto{
    int figura;

    public void Powerup(){
        System.out.println("Powerup Pastilla");
    }

    public int sumPoints(){return 100;}
}
