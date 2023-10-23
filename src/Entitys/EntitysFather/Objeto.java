package Entitys.EntitysFather;

public class Objeto {
    public Integer posX;
    public Integer posY;

    public void Colision(){}
    public void setPosition(Integer posX,Integer posY){
        this.posX = posX;
        this.posY = posY;
        System.out.println("New position: " );
        System.out.println("X"+this.posX);
        System.out.println("Y"+this.posY);
    }
    public Integer getPosY(){
        Integer data = this.posY;
        return data;}

    public Integer getPosX(){
        Integer data = this.posX;
        return data;}

    public void Ubicacionrandom(){}
    public void Powerup(){}
    public int sumPoints(){return 10;}

}