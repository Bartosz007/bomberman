package Objects.Bomb;

import Basic.GameObject;
import Game.PlayField;
import Objects.Hero;
import Objects.PowerUp.*;
import Objects.State;
import Settings.BLOCK_TYPE;
import Settings.PLAYER;
import Settings.PROBABILITY;
import javafx.scene.transform.Rotate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DamageArea implements State {//możliwe że to szablon Builder(str18)
    private int power;
    private long death_time;
    private String color;
    private Dimension blockPosition;
    private PlayField board;
    private Date date ;
    private BufferedImage image;
    private Hero owner;
    private List <DamageBlock> list;
    private List<GameObject> powerUps;
    public DamageArea(Dimension blockPosition, int power, String color, Hero owner, PlayField board, List<GameObject> powerUps) {
        this.blockPosition = blockPosition;
        this.power = power;
        this.color = color;
        this.board = board;
        this.powerUps = powerUps;
        this.owner = owner;
        this.death_time = new Date().getTime() + PLAYER.WYBUCH_WIDOK;
        this.list = new ArrayList<>();

        try {
            this.image = ImageIO.read(getClass().getResource("/"+color+"/niebieskiwybuch.png"));
        } catch (IOException e) {
            System.out.println("Nie wczytało pliku /niebieskiwybuch.png");
        }

        calculate_area();
    }

    @Override
    public boolean checkState() {
        date = new Date();
        return this.death_time >= date.getTime();
    }

    @Override
    public boolean checkState(Hero obj) {
        return false;
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
            list.add(new DamageBlock(new Dimension(x,y),wycinanie(1,vector_x,vector_y)));
            board.getField(x,y).setName("empty");
            board.getField(x,y).setType(BLOCK_TYPE.FLOOR);

            Random generator = new Random();
            switch ( new Random().nextInt() % 4){
                case 0:
                    if(PROBABILITY.MOAR_BOMB >generator.nextFloat())
                        this.powerUps.add(new MoarBomb(new Dimension(x,y)));
                    break;
                case 1:
                    if(PROBABILITY.MOAR_KICK >generator.nextFloat())
                        this.powerUps.add(new MoarHand(new Dimension(x,y)));
                    break;
                case 2:
                    if(PROBABILITY.MOAR_POWER >generator.nextFloat())
                        this.powerUps.add(new MoarPower(new Dimension(x,y)));
                    break;
                case 3:
                    if(PROBABILITY.MOAR_SPEED >generator.nextFloat())
                        this.powerUps.add(new MoarSpeed(new Dimension(x,y)));
                    break;
            } //TODO prawdopobieństwo wypadania lootu

        }
        else if(deep == 0){
            list.add(new DamageBlock(new Dimension(x,y),wycinanie(1,vector_x,vector_y)));
            board.getField(x,y).setName("empty");
            board.getField(x,y).setType(BLOCK_TYPE.FLOOR);
        }else{
            list.add(new DamageBlock(new Dimension(x,y),wycinanie(0,vector_x,vector_y)));
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

        list.add(new DamageBlock(this.blockPosition,wycinanie(2,0,0)));
        reqursive_check(x-1,y,-1,0,power-1);//lewo
        reqursive_check(x+1,y,1,0,power-1);//prawo
        reqursive_check(x,y-1,0,-1,power-1);//góra
        reqursive_check(x,y+1,0,1,power-1);//dół

    }

    private BufferedImage wycinanie(int kolumna, int wx, int wy){
        BufferedImage bf = this.image.getSubimage(kolumna*PLAYER.ROZMIAR,0,PLAYER.ROZMIAR,PLAYER.ROZMIAR);
        int kat = 0;

        if(wx==1)
            kat = 0;
        else if(wx==-1)//TODO posegreguj
            kat = 180;
        else if(wy==-1)
            kat = 270;
        else if(wy==1)
            kat = 90;


        double rads = Math.toRadians(kat);

        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = (int) Math.floor(PLAYER.ROZMIAR * cos + PLAYER.ROZMIAR * sin);
        int h = (int) Math.floor(PLAYER.ROZMIAR * cos + PLAYER.ROZMIAR * sin);
        BufferedImage rotatedImage = new BufferedImage(w, h, bf.getType());
        AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-bf.getWidth() / 2, -bf.getHeight() / 2);

        AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(bf,rotatedImage);

        return rotatedImage;
    }


    public List<DamageBlock> getList() {
        return list;
    }

    public Hero getOwner() {
        return owner;
    }
}
