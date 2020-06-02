package Objects.PowerUp;

import Basic.GameObject;
import Objects.Hero;

import java.awt.*;

public class MoarHand extends GameObject {
    public MoarHand(Dimension block_position) {
        super(block_position,"moar_kick","/power_ups/reka.png");
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
            obj.setMove_bomb(true);
            obj.setScore(obj.getScore()+100); //TODO Ola - kolejny powerup
            return false;
        }
        else {
            return true;
        }
    }
}
