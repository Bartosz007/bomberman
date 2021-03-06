package Objects.PowerUp;

import Basic.GameObject;
import Objects.Hero;
import Settings.PLAYER;

import java.awt.*;

public class MoarSpeed extends GameObject  {

    public MoarSpeed(Dimension block_position) {
        super(block_position,"moar_speen","/power_ups/buty.png");
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
            if(obj.getSpeed() < PLAYER.MAX_SPEED)
                obj.setSpeed(obj.getSpeed()+1);
            obj.setScore(obj.getScore()+PLAYER.PU_NISKI);
            return false;
        }
        else {
            return true;
        }
    }
}
