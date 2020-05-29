package Game;

import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.DamageArea;
import Objects.Hero;
import Objects.Bomb.Bomb;
import Objects.PowerUp.MoarBomb;
import Objects.PowerUp.MoarHand;
import Objects.PowerUp.MoarPower;
import Objects.PowerUp.MoarSpeed;
import Settings.BLOCK_TYPE;
import Settings.KEY;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DrawFrame extends JPanel implements KeyListener {
    private int width,heigh;
    private int x = 0;
    private int y = 0;

    private Timer timer;
    private Dimension size;
 //   private Field[][] board;
    private PlayField board;


    /*
    wzorzec projektowy: Abstract Factory(10 strona), tworzymy listy obiektów abstrakcyjnych typu GameObject,
    do niego dodajemy klasy takie jak Hero, Bomb, MoarBomb, MoarHand, które dziedziczą po GameObject,
    jest też wspólna metoda draw, napisywana przez każdą klase i wywoływana wspólnie w paintComponent

     */
    private List<Hero> game_heros; //gracze
    private List<GameObject> powerUps;      //power upy

    private List<DamageArea> damageAreas;   //bloki obrażeń
    private List<Bomb> bombList;            //bomby
    private Bomb actual_bomb;
    private Hero player_one;
    private Hero player_two;

    private boolean[] player_one_moves;
    private boolean[] player_two_moves;

    private boolean is_player_one_here = true;
    private boolean is_player_two_here = false;//będzie to w konstruktorze



    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.80)); // to się zmieni
        this.heigh = (int)(size.height *(0.80));
        setPreferredSize(new Dimension(5*size.width/6,size.height));

        setFocusable(true);
        setRequestFocusEnabled(true);
        requestFocus();
        requestFocusInWindow();

        addKeyListener(this);

        Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
        board  =  new PlayField(g.fromJson(Data.fields_data,Field[][].class));
        board.reposition();//trzeba jeszcze przypisać dokładne współrzędne a to wyżej nie ładuje konstruktora

        game_heros = new ArrayList<>();//pojemnik na postacie

        bombList = new ArrayList<>();//bomby
        powerUps = new ArrayList<>();//pwoer upy
        damageAreas =  new ArrayList<>();//bloki obrażeń

        player_one_moves = new boolean[]{false,false,false,false,false};
        player_two_moves = new boolean[]{false,false,false,false,false};


        player_one = new Hero(new Dimension(1,1),"blue_bomberman", "/blue/niebieski.png");
        player_one.setPlayer(board.getBoard(),bombList,5,3,2,2);


       // player_two = new Hero(new Dimension(10,1),"green_bomberman","/blue/niebieski.png");
       // player_two.setPlayer(board.getBoard(),bombList,4,3,2,1);

        powerUps.add(new MoarHand(new Dimension(3,1)));
        game_heros.add(player_one);
       // game_heros.add(player_two);


        timer = new Timer(15, e -> {
            calculate();
            repaint();
        });
        timer.start();

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g.create();

        g2d.setColor(Color.magenta);//rysoanie ramki
        g2d.drawRect(0,0,width,heigh);

        board.draw_field(g2d);//rysowanie planszy
     //  g2d.setColor(Color.magenta);
      //  g2d.fillRect(x,y,30,30);

        for (GameObject obj: powerUps){
            obj.draw(g2d);
        }

        for (GameObject obj: game_heros){
            obj.draw(g2d);
        }

        for (GameObject obj: bombList){
            obj.draw(g2d);
        }

        for (DamageArea obj: damageAreas){
            obj.draw(g2d);
        }

        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) { //miałem wersję zrobioną na pętlach, mapach i listach, ale wydajnościowo stało to słabo
        int keycode = e.getKeyCode();
        //0-up,1-left,2-down,3-right, 4-bomb
        if(is_player_one_here) {
            if (keycode == KEY.W) {
                player_one_moves[0] = true;
            }
            if (keycode == KEY.A) {
                player_one_moves[1] = true;
            }
            if (keycode == KEY.S) {
                player_one_moves[2] = true;
            }
            if (keycode == KEY.D) {
                player_one_moves[3] = true;
            }
            if (keycode == KEY.SPACE && player_one.getBombs() > 0) {
                for (Bomb b : bombList) {
                    if (player_one.getBlock_position().equals(b.getBlock_position())) {  //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                        if(player_one.isMove_bomb() && !player_one.isBomb_in_hand()){
                            player_one.setPicked_bomb(b);
                            player_one.setBomb_in_hand(true);
                        }
                        return;
                    }

                }
                player_one.setBombs(player_one.getBombs() - 1);
                //    player_one_moves[4] = true;
                Bomb bomb = new Bomb(player_one.getBlock_position(), player_one.getBomb_power(), "blue", "/blue/dynamit.png", player_one);
                //    game_objects.add(bomb);
                bombList.add(bomb);

            }
        }

        if(is_player_two_here){
            if(keycode==KEY.UP){
                player_two_moves[0] = true;
            }
            if(keycode==KEY.LEFT){
                player_two_moves[1] = true;
            }
            if(keycode==KEY.DOWN){
                player_two_moves[2] = true;
            }
            if(keycode==KEY.RIGHT){
                player_two_moves[3] = true;
            }
            if(keycode==KEY.ENTER && player_two.getBombs() > 0){
                for(Bomb b: bombList){
                    if(player_two.getBlock_position().equals(b.getBlock_position())) { //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                        if(player_two.isMove_bomb() && !player_two.isBomb_in_hand()){
                            player_two.setPicked_bomb(b);
                            player_two.setBomb_in_hand(true);
                        }
                        return;
                    }
                }
                player_two.setBombs(player_two.getBombs()-1);
                System.out.println(player_two.getBombs());
                Bomb bomb = new Bomb(player_two.getBlock_position(), player_two.getBomb_power(),"blue","/blue/dynamit.png",player_two);

                bombList.add(bomb);
                System.out.println("bomba podłożona");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if(is_player_one_here) {
            if (keycode == KEY.W) {
                player_one_moves[0] = false;
            }
            if (keycode == KEY.A) {
                player_one_moves[1] = false;
            }
            if (keycode == KEY.S) {
                player_one_moves[2] = false;
            }
            if (keycode == KEY.D) {
                player_one_moves[3] = false;
            }
            if (keycode == KEY.SPACE) {
                if(player_one.isBomb_in_hand()){
                    throw_bomb(player_one);
                }
            }
        }

        if(is_player_two_here){
            if(keycode==KEY.UP){
                player_two_moves[0] = false;
            }
            if(keycode==KEY.LEFT){
                player_two_moves[1] = false;
            }
            if(keycode==KEY.DOWN){
                player_two_moves[2] = false;
            }
            if(keycode==KEY.RIGHT){
                player_two_moves[3] = false;
            }
            if(keycode==KEY.ENTER){
                if(player_two.isBomb_in_hand()){
                    throw_bomb(player_two);
                }
            }
        }

    }

    protected void calculate(){
        //pole ma strukturę siatki, otoczone jest blokami typu wall- czyli nieziszczalne i nieprzechodząca

        //liczenie pozycji postaci i kolizji //TODO - Bartosz - przerobić nieco ten system dwóch graczy - ujednolicić

        if(game_heros.size()==0){
            System.out.println("KONIEC GRYYYYY");
        }

        if(is_player_one_here) {
            player_one.calculate(player_one_moves);
        }

        if(is_player_two_here) {
            player_two.calculate(player_two_moves);
        }

        //obliczanie wybuchu bomb
        Bomb bomb;
        DamageArea dm;
        int a = 0;
        for(int i = 0;i<bombList.size()-a;i++){ // uniwersalna funkcja sprawdzająca wybuchy bomb - dla wszystkich postaci
            bomb = bombList.get(i);
            if(!bomb.checkState()){
                bombList.remove(bomb);
                bomb.getOwner().setBombs(bomb.getOwner().getBombs()+1);

                dm = new DamageArea(bomb.getBlock_position(),bomb.getPower(),bomb.getColor(),bomb.getOwner().getName(),board,powerUps);
                damageAreas.add(dm);
                a++;

                return;
            }
            if(bomb.isIt_flies()){
                bomb.fly();
            }
        }


        //obliczanie obszaru wybuchu
        a = 0;
        for(int i = 0;i<damageAreas.size()-a;i++) { // uniwersalna funkcja sprawdzająca wybuchy bomb - dla wszystkich postaci
            dm = damageAreas.get(i);
            if(!dm.checkState()){
                damageAreas.remove(dm);
                a++;
            }
        }


        //sprawdzanie obrażeń
        Hero hero;
        a = 0;
        for(int i = 0;i<game_heros.size()-a;i++) {
            hero  = game_heros.get(i);
            hero.checkDamage(damageAreas);
            if(!hero.checkState()){
                game_heros.remove(hero);
                a++;
            }
        }


        //wyłapywanie power-upów
        a = 0;
        GameObject obj;
        for(int i = 0;i<powerUps.size()-a;i++){
            obj = powerUps.get(i);
            for(Hero h: game_heros){
                if(!obj.checkState(h)){
                    powerUps.remove(obj);
                    a++;
                }
            }
        }

    }

    private void throw_bomb(Hero hero){
        Bomb b = hero.getPicked_bomb();
        hero.setPicked_bomb(null);
        hero.setBomb_in_hand(false);
        int x = b.getBlock_position().width;
        int y = b.getBlock_position().height;
        int vector_x = hero.getVector_x();
        int vector_y = hero.getVector_y();
        int d = 0;

        Field field = board.getField(x,y);

        while(d < 3 ){

            if(board.getField(x ,y).getType() == BLOCK_TYPE.FLOOR){
                field = board.getField(x ,y );
                x = x + vector_x;
                y = y + vector_y;
            }
            d++;
        }
        b.let_fly(true,field.getX(),field.getY());
    }
}
