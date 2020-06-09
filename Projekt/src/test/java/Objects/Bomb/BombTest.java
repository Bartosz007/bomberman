package Objects.Bomb;

import org.junit.Assert;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {

    @org.junit.jupiter.api.Test
    void czyBombaWybuchnie() {
        Bomb bomb = new Bomb(new Dimension(1,1),1,"blue","/blue/paleta.png",null);
        int n =149;
        for(int i =0;i<n;i++){
            bomb.checkState();
        }
        //bomba wybuchnie dopiero w 150 iteracji
        Assert.assertFalse(bomb.checkState());
    }
}