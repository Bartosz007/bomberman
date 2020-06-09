package Objects.PowerUp;

import Additions.Loader;
import Game.PlayField;
import Objects.Bomb.Bomb;
import Objects.Hero;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoarPowerTest {

    @Test
    void czyBombaBedzieSilnijesza() {
        Hero hero = new Hero(new Dimension(1,1),"Niebieski bomberman", "/blue/paleta.png");
        PlayField board = new Loader("Mapa0").getBoard();
        List<Bomb> bombList = new ArrayList<>();
        hero.setPlayer(board.getBoard(),bombList,3,1,2,2);

        Bomb bomb = new Bomb(new Dimension(1,1),hero.getBomb_power(),"blue","/blue/dynamit.png",hero);
        int pre = bomb.getPower();

        MoarPower moarPower = new MoarPower(new Dimension(1,1));

        moarPower.checkState(hero);

        bomb = new Bomb(new Dimension(1,1),hero.getBomb_power(),"blue","/blue/dynamit.png",hero);
        int post = bomb.getPower();

        Assert.assertEquals(pre,2);
        Assert.assertEquals(post,3);
    }
}