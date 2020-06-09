package Objects.Bomb;

import Basic.GameObject;
import Game.PlayField;
import Objects.Hero;
import Objects.PowerUp.*;
import Objects.State;
import Settings.BLOCK_TYPE;
import Settings.PLAYER;
import Settings.PROBABILITY;

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
            this.image = ImageIO.read(getClass().getResource("/"+color+"/wybuch.png"));
        } catch (IOException e) {
            System.out.println("Nie wczytało pliku iwybuch.png");
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
            }

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
        else if(wy==1)
            kat = PLAYER.KAT;
        else if(wx==-1)
            kat = PLAYER.KAT*2;
        else if(wy==-1)
            kat = PLAYER.KAT*3;


        double rads = Math.toRadians(kat);

        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = (int) Math.floor(PLAYER.ROZMIAR * cos + PLAYER.ROZMIAR * sin);
        int h = (int) Math.floor(PLAYER.ROZMIAR * cos + PLAYER.ROZMIAR * sin);
        BufferedImage rotatedImage = new BufferedImage(w, h, bf.getType());
        AffineTransform at = new AffineTransform();
        at.translate(w / 2.0f, h / 2.0f);
        at.rotate(rads,0.0f, 0.0f);
        at.translate(-bf.getWidth() / 2.0f, -bf.getHeight() / 2.0f);

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
