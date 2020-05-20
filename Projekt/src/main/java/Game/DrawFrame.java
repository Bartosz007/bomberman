package Game;

import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.DamageArea;
import Objects.Hero;
import Objects.Bomb.Bomb;
import Settings.KEY;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    private Hero player_one;
    private Hero player_two;

    private boolean[] player_one_moves;
    private boolean[] player_two_moves;

    private boolean is_player_two_here = false;//będzie to w konstruktorze



    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.80)); // to się zmieni
        this.heigh = (int)(size.height *(0.80));
        setPreferredSize(new Dimension(5*size.width/6,size.height));

        requestFocusInWindow();
        setFocusable(true);
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


        player_one = new Hero(new Dimension(1,1),"blue_bomberman", "/blue/niebieski.png",3,1,2,2);
     //   player_one.setKeys(KEY.W,KEY.A,KEY.S,KEY.D,KEY.SPACE);

      //  player_two = new Hero(new Dimension(10,1),"green_bomberman","/blue/niebieski.png",3,1,2,2);
     //   keyStatus.putAll(player_two.setKeys(KEY.UP,KEY.LEFT,KEY.DOWN,KEY.RIGHT,KEY.ENTER));

        game_heros.add(player_one);
      //  game_heros.add(player_two);


        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
                repaint();
            }
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


        for (GameObject obj: game_heros){
            obj.draw(g2d);
        }

        for (GameObject obj: bombList){
            obj.draw(g2d);
        }

        for (DamageArea obj: damageAreas){
            obj.draw(g2d);
        }

        for (GameObject obj: powerUps){
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
        if(keycode==KEY.W){
            player_one_moves[0] = true;
        }
        if(keycode==KEY.A){
            player_one_moves[1] = true;
        }
        if(keycode==KEY.S){
            player_one_moves[2] = true;
        }
        if(keycode==KEY.D){
            player_one_moves[3] = true;
        }
        if(keycode==KEY.SPACE && player_one.getBombs() > 0){
            Dimension player_pos = player_one.getBlock_position();
            for(Bomb b: bombList){
                if(player_pos.equals(b.getBlock_position())) //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                    return;
            }
            player_one.setBombs(player_one.getBombs()-1);
        //    player_one_moves[4] = true;
            Bomb bomb = new Bomb(player_pos, player_one.getBomb_power(),"blue","/blue/dynamit.png",player_one);
        //    game_objects.add(bomb);
            bombList.add(bomb);
            System.out.println("bomba podłożona");

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
            if(keycode==KEY.ENTER){
                Dimension player_pos = player_two.getBlock_position();
                for(Bomb b: bombList){
                    if(player_pos.equals(b.getBlock_position())) //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                        return;
                }
                player_two.setBombs(player_two.getBombs()-1);
                //    player_one_moves[4] = true;
                Bomb bomb = new Bomb(player_pos, player_two.getBomb_power(),"blue","/blue/dynamit.png",player_two);
                //    game_objects.add(bomb);
                bombList.add(bomb);
                System.out.println("bomba podłożona");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if(keycode==KEY.W){
            player_one_moves[0] = false;
        }
        if(keycode==KEY.A){
            player_one_moves[1] = false;
        }
        if(keycode==KEY.S){
            player_one_moves[2] = false;
        }
        if(keycode==KEY.D){
            player_one_moves[3] = false;
        }
        if(keycode==KEY.SPACE){
            player_one_moves[4] = false;
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
                player_two_moves[4] = false;
            }
        }

    }

    protected void calculate(){
        //pole ma strukturę siatki, otoczone jest blokami typu wall- czyli nieziszczalne i nieprzechodząca

        //liczenie pozycji postaci i kolizji //TODO - Bartosz - przerobić nieco ten system dwóch graczy - ujednolicić
        player_one.calculate(player_one_moves,board.getBoard());

        if(is_player_two_here) {
            player_two.calculate(player_two_moves,board.getBoard());
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

                dm = new DamageArea(bomb.getBlock_position(),bomb.getPower(),bomb.getColor(),bomb.getOwner().getName(),board);
                damageAreas.add(dm);
                a++;
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
        for(Hero obj: game_heros){
            obj.checkDamage(damageAreas);
        }




        for (GameObject obj: powerUps){
          //  obj.draw(g2d);
        }


    }




}
