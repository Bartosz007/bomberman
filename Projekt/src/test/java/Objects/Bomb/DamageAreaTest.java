package Objects.Bomb;

import Basic.Field;
import Game.PlayField;
import Objects.Hero;
import Settings.BLOCK_TYPE;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

class DamageAreaTest {

    @Test
    void rozmiarWybuchu(){
        int xpor[] = new int[]{90, 45, 0, 135, 180, 90, 90, 90, 90};
        int ypor[] = new int[]{90, 45, 0, 135, 180, 90, 90, 90, 90};

        Bomb bomb = new Bomb(new Dimension(1,1),2,"blue","/blue/paleta.png",null);
        int n =149;
        for(int i =0;i<n;i++){
            bomb.checkState();
        }
        Field[][] plansza = new Field[5][5];
        for(int i =0;i<5;i++){
            for(int j=0;j<5;j++){
                plansza[i][j]=new Field(i,j);
                plansza[i][j].setType(BLOCK_TYPE.FLOOR);
            }
        }

        PlayField board  = new PlayField(plansza);
        Hero hero = new Hero(new Dimension(1,1),"blue","/blue/paleta.png");
        DamageArea damageArea = new DamageArea(new Dimension(2,2),2,"blue",hero,board,null);
        List<DamageBlock> bloki = damageArea.getList();

        int dl = bloki.size();
        int xes[] = new int[dl];
        int yes[] = new int[dl];

        for(int i =0;i<bloki.size();i++){
            xes[i]=bloki.get(i).getX();
            yes[i]=bloki.get(i).getX();
        }

        //bomba nie ma ograniczenia więc będzie bloki.size()=4*moc bomby +1 = 4*2+1
        Assert.assertArrayEquals(xes,xpor);
        Assert.assertArrayEquals(yes,ypor);
    }

}