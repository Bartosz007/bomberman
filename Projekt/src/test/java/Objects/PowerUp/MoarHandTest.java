package Objects.PowerUp;

import Objects.Hero;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoarHandTest {

    @Test
    void czyPodniesiePowerUpa() {
        Hero hero = new Hero(new Dimension(1,1),"blue","/blue/paleta.png");
        MoarHand moarHand = new MoarHand(new Dimension(1,1));
        Assert.assertFalse(moarHand.checkState(hero));
    }
}