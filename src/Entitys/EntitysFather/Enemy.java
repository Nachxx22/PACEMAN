package Entitys.EntitysFather;

public class Enemy {
    public Integer posX;
    public Integer posY;
    public Integer speed;


    public void Movimiento(){}
    
    public void Colision(){}

    public void setPosition(Integer posx,Integer posy){
        this.posX = posx;
        this.posY = posy;
        System.out.println("New position: " );
        System.out.println("X"+this.posX);
        System.out.println("Y"+this.posY);
    }
    public void Ubicacionrandom(){}

    public Integer getPosY(){
        Integer data = this.posY;
        return data;}

    public Integer getPosX(){
        Integer data = this.posX;
        return data;}

    public void setSpeed(Integer e){
        this.speed = e;
    }

}
