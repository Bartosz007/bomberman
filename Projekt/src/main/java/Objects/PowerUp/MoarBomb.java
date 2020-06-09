package Objects.PowerUp;

import Basic.GameObject;
import Objects.Hero;
import Settings.PLAYER;

import java.awt.*;

public class MoarBomb extends GameObject {
    public MoarBomb(Dimension block_position) {
        super(block_position, "moar_bomb","/power_ups/dynamit.png");
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
            obj.setBombs(obj.getBombs()+1);
            obj.setScore(obj.getScore()+PLAYER.PU_WYSOKI);
            return false;
        }
        else {
            return true;
        }
    }
}
