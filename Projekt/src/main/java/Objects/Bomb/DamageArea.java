package Objects.Bomb;

import Game.PlayField;
import Settings.BLOCK_TYPE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DamageArea implements State {//możliwe że to szablon Builder(str18)
    private int power;
    private long death_time;
    private Dimension blockPosition;
    private String color;
    private PlayField board;
    private Date date ;
    private Image image;// obrazek jest łądowany tutaj w celu ograniczenia liczby czytania pliku
    private List <DamageBlock> list;

    public DamageArea(Dimension blockPosition, int power, String color, String owner, PlayField board) {
        this.blockPosition = blockPosition;
        this.power = power;
        this.color = color;
        this.board = board;
        //TODO: Ola, to poniżej jest czas trwania bomby(ms), dodaj stałą w pliku SETTTINGS i 1000 na nią zamień
        this.death_time = new Date().getTime() + 1000;
        this.list = new ArrayList<>();


      //  System.out.println("/"+color+"/niebieski.png");
        //background = ImageIO.read(getClass().getResource("/background/tlo2.png"));
        try {
            this.image = ImageIO.read(getClass().getResource("/"+color+"/center.png"));
        } catch (IOException e) {
            System.out.println("Nie wczytało pliku /center.png");
        }

        calculate_area();
    }

    @Override
    public boolean checkState() {
        date = new Date();
        if(this.death_time<date.getTime()){
            return false;
        }
        else{
            return true;
        }
    }

    public void draw(Graphics2D g2d){
        for(DamageBlock b : list){
            g2d.drawImage(b.getImage(), b.getX(), b.getY(), null);
        }
    }

    private void reqursive_check(int x, int y, int vector_x, int vector_y, int deep){//x i y to pozycja bloku(na planszy)
        int type = board.getField(x,y).getType();
        if(type == BLOCK_TYPE.WALL){
            return;
        }else if(type == BLOCK_TYPE.BARREL){
            list.add(new DamageBlock(new Dimension(x,y),image));
            list.add(new DamageBlock(new Dimension(x,y),image));
            board.getField(x,y).setName("empty");
            board.getField(x,y).setType(BLOCK_TYPE.FLOOR);
            Random generator = new Random();
            switch ( new Random().nextInt() % 5){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }

        }
        else if(deep == 0){
            list.add(new DamageBlock(new Dimension(x,y),image));
            list.add(new DamageBlock(new Dimension(x,y),image));
            board.getField(x,y).setName("empty");
            board.getField(x,y).setType(BLOCK_TYPE.FLOOR);
        }else{
            list.add(new DamageBlock(new Dimension(x,y),image));
            reqursive_check(x+vector_x,y+vector_y,vector_x,vector_y,deep-1);
        }
    }

    private void calculate_area(){
        //TODO: Ola - Ty to dodasz, jak będziesz poprawiała animację wybuchu
        //tworzy element pola wybuchu, wykonuje się tylko raz podczas inicjacji obiektu DamageArea
        //type 0 - środek wybuchu
        //type 1 - linia wybuchu
        //type 2 - koniec wybuchu
        //rotacja - o ile rotować obrazki - pewnie zależne będzie od x i y
        //ponieżej też będzie funkcja, która tnie obrazek i go rotuje w razie potrzeby
        //będziesz to zmieniać w funkcji reqursive_check
        int x = (int)this.blockPosition.getWidth();
        int y = (int)this.blockPosition.getHeight();

        list.add(new DamageBlock(this.blockPosition,image));
        reqursive_check(x,y,-1,0,power);//lewo
        reqursive_check(x,y,1,0,power);//prawo
        reqursive_check(x,y,0,-1,power);//góra
        reqursive_check(x,y,0,1,power);//dół

    }

    public List<DamageBlock> getList() {
        return list;
    }
}
