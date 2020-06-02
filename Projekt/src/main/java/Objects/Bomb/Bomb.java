package Objects.Bomb;

import Basic.GameObject;
import Objects.Hero;
import Objects.State;

import java.awt.*;
import java.util.Date;

public class Bomb extends GameObject implements State {
    private Hero owner;
    private int power;
    private long death_time;//czas życia bomby(po którym wybucha)
    private String color;
    private Date date;
    private int destination_x;
    private int destination_y;

    private boolean it_flies;
    public Bomb(Dimension block_position,int power, String color, String url, Hero owner) {
        super(block_position, "bomb", url);
        this.owner = owner;
        this.power = power;
        //TODO: Ola, to poniżej jest czas(ms) po którym wybuchnie bomba, dodaj stałą w pliku SETTTINGS i 2500 na nią zamień
        this.death_time =new Date().getTime() + 2500;
        this.color = color;
        this.it_flies = false;

    }

    @Override
    public void draw(Graphics2D g2d) {
        //TODO - Ola - animacja bomby
        //podobnie jak z hero - wytku tutaj nie ma wektora zwrotu

        g2d.drawImage(this.image, this.x, this.y, null);
    }


    @Override
    public boolean checkState() {
        date = new Date();
        return this.death_time >= date.getTime();
    }

    @Override
    public boolean checkState(Hero obj) {
        return false;
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

    public boolean isIt_flies() {
        return it_flies;
    }

    public void let_fly(boolean it_flies, int destination_x, int destination_y) {
        this.it_flies = it_flies;
        this.destination_x = destination_x;
        this.destination_y = destination_y;

    }

    public void fly(){
        int speeed = 3; //// TODO; Ola - proszę dodać speed(prędkość bomb) do stałych
       // int count = 0;
        if(this.destination_x < this.x){
            this.x = this.x - speeed;
            if(Math.abs(this.destination_x - this.x)<2){
                this.x = this.destination_x;
                this.it_flies = false;
                return;
            }
        }
        else if (this.destination_x > this.x){
            this.x = this.x + speeed;
            if(Math.abs(this.destination_x - this.x)<2){
                this.x = this.destination_x;
                this.it_flies = false;
                return;
            }
        }

        if(this.destination_y < this.y){
            this.y = this.y - speeed;
            if(Math.abs(this.destination_y - this.y)<2){
                this.y = this.destination_y;
                this.it_flies = false;
                return;
            }
        }
        else if (this.destination_y > this.y){
            this.y = this.y + speeed;
            if(Math.abs(this.destination_y - this.y)<2){
                this.y = this.destination_y;
                this.it_flies = false;
                return;
            }
        }
        //liczymy blok względem srodka bloku a nie lewego górnego rogu
        this.block_position = new Dimension((this.x+22)/45,(this.y+22)/45);
        //TODO - Ola prosze zamienić w całym projekcie zmienne 45 i 22 na stałe (są to standardowe rozmiary bloku)
        //Ctrl+Shif+F
    }

}
