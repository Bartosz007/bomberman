package Objects.Bomb;

import Basic.GameObject;
import Objects.Hero;

import java.awt.*;
import java.util.Date;

public class Bomb extends GameObject implements State{
    private Hero owner;
    private int power;
    private long death_time;//czas życia bomby(po którym wybucha)
    private String color;
    private Date date ;
    public Bomb(Dimension block_position,int power, String color, String url, Hero owner) {
        super(block_position, "bomb", url);
        this.owner = owner;
        this.power = power;
        //TODO: Ola, to poniżej jest czas(ms) po którym wybuchnie bomba, dodaj stałą w pliku SETTTINGS i 2500 na nią zamień
        this.death_time =new Date().getTime() + 2500;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //TODO - Ola - animacja bomby
        //podobnie jak z hero - wytku tutaj nie ma wektora zwrotu

        g2d.drawImage(this.image, this.x, this.y, null);
    }


 //   @Override
    public boolean checkState() {
        date = new Date();
        if(this.death_time<date.getTime()){
            return false;
        }
        else{
            return true;
        }
    }

    public Hero getOwner() {
        return owner;
    }

    public String getColor() {
        return color;
    }

    public int getPower() {
        return power;
    }
}
