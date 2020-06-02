package Objects.PowerUp;

import Basic.GameObject;
import Objects.Hero;

import java.awt.*;

public class MoarPower extends GameObject {
    public MoarPower(Dimension block_position) {
        super(block_position,"moar_power","/power_ups/piorun.png");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.image, this.x, this.y, null);
    }

    @Override
    public boolean checkState() {
        return false;
    }

    @Override
    public boolean checkState(Hero obj) {
        if(obj.getBlock_position().equals(block_position)){
            if(obj.getBomb_power() < 8)//TODO - Ola: 8 to maksymalne pole rażenia bomby, prosze ustaw to jaką stała np MAX_POWER
                obj.setBomb_power(obj.getBomb_power()+1);
            obj.setScore(obj.getScore()+75); //TODO Ola - power uP
            return false;
        }
        else {
            return true;
        }
    }
}
