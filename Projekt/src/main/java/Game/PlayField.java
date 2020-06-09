package Game;

import Basic.Field;
import Settings.PLAYER;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PlayField {
    private Field[][] board;
    private Image background,barrel,wall;
    private int field_size = PLAYER.ROZMIAR;//rozmiar pola w pikselach
    public PlayField(Field[][] board) {
        this.board = board;

        try {
            background = ImageIO.read(getClass().getResource("/background/tlo2.png"));
            barrel = ImageIO.read(getClass().getResource("/background/skrzynia.png"));
            wall = ImageIO.read(getClass().getResource("/background/staly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw_field(Graphics2D g2d){
        g2d.drawImage(background,0,0,null);

        for(int i=1;i<14;i++){//nie renderujemy stałych, nie zależnych od mapy- czyli tła planszy i ramy czyli zamiast 15x
            for(int j =1;j<12;j++){
                //ponieważ są 2 typy bloków stałych - wall - niezniszczalna oraz barrel zniszczalna
                //to mozemy te bloki identyfikować po zniszczalności
                if(board[i][j].getName().equals("wall")){
                    g2d.drawImage(wall, i*field_size, j*field_size, null);
                }
                else if(board[i][j].getName().equals("barrel")){
                    g2d.drawImage(barrel, i*field_size, j*field_size, null);
                }
            }
        }
    }

    public void reposition(){
        for( Field []a : board){
            for(Field b : a){
                b.positioning();
            }
        }
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }

    public Field getField(int x, int y){
        return board[x][y];
    }
}
