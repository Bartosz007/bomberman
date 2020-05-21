package Objects.PowerUp;

import Basic.GameObject;
import Objects.Hero;

import java.awt.*;

public class MoarKick extends GameObject {


    public MoarKick(Dimension block_position) {
        super(block_position,"moar_hand","/power_ups/kop.png");
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
        return false;
    }
}
