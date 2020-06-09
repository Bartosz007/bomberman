package Additions;

import Game.PlayField;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {
    @org.junit.jupiter.api.Test
    void czyWladowanoDobrzeDane(){
        String mapa = "Mapa0";

        PlayField board = new Loader(mapa).getBoard();

        Assert.assertEquals(board.getBoard().length,15);
        Assert.assertEquals(board.getBoard()[0].length,13);
    }
}