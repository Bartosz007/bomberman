package Game;

import Basic.Field;
import Basic.GameObject;
import Objects.Hero;
import Objects.Bomb;
import Settings.KEY;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DrawFrame extends JPanel implements KeyListener {
    private int width,heigh;
    private int x = 0;
    private int y = 0;

    private Timer timer;
    private Dimension size;
    private Field[][] board;
    private int field_size = 45;//rozmiar pola w pikselach

    private List<GameObject> game_objects;

    private Hero player_one;
    private Hero player_two;

    private boolean[] player_one_moves;
    private boolean[] player_two_moves;

    private boolean is_player_two_here = false;//będzie to w konstruktorze

    private Image background,barrel,wall;
    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.80)); // to się zmieni
        this.heigh = (int)(size.height *(0.80));
        setPreferredSize(new Dimension(5*size.width/6,size.height));

        requestFocusInWindow();
        setFocusable(true);
        addKeyListener(this);

        Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
        board  = g.fromJson(Data.fields_data,Field[][].class);
        for( Field []a : board){
            for(Field b : a){
                b.reposition();
            }
        }
        game_objects = new ArrayList<>();//gracz, power-upy

        player_one_moves = new boolean[]{false,false,false,false,false};
        player_two_moves = new boolean[]{false,false,false,false,false};


        player_one = new Hero(new Dimension(1,1),"blue_bomberman", "/blue/niebieski.png",4,1,2);
     //   player_one.setKeys(KEY.W,KEY.A,KEY.S,KEY.D,KEY.SPACE);

      //  player_two = new Hero(new Dimension(10,1),"green_bomberman","/niebieski/niebieskiStoi.png",3,1,2);
     //   keyStatus.putAll(player_two.setKeys(KEY.UP,KEY.LEFT,KEY.DOWN,KEY.RIGHT,KEY.ENTER));

        game_objects.add(player_one);//polimorfizm

   //     game_objects.add(player_two);//polimorfizm

        load_images();
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

        draw_field(g2d);//rysowanie planszy
     //  g2d.setColor(Color.magenta);
      //  g2d.fillRect(x,y,30,30);

        for (GameObject obj: game_objects){
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
        if(keycode==KEY.SPACE){
            player_one_moves[4] = true;
            Bomb bomb = new Bomb(player_one.getBlock_position(),"bomb", "/background/skrzynia.png",player_one.getName());
            game_objects.add(bomb);
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
                player_two_moves[4] = true;
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

        player_one.calculate(player_one_moves,board);

        if(is_player_two_here) {
            player_two.calculate(player_two_moves,board);
        }

    }

    private void load_images(){//ładujemy obrazki raz dla lepszej optymalizacji
        try {
            background = ImageIO.read(getClass().getResource("/background/tlo2.png"));
            barrel = ImageIO.read(getClass().getResource("/background/skrzynia.png"));
            wall = ImageIO.read(getClass().getResource("/background/staly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
                //Image barrel = ImageIO.read(new File("src/main/resources/skrzynia.png"));
            //    Image wall = ImageIO.read(new File("src/main/resources/elementyTla/", "staly.png"));
    }

    private void draw_field(Graphics2D g2d){

        g2d.drawImage(background,0,0,null);
        for(int i=1;i<14;i++){//nie renderujemy stałych, nie zależnych od mapy- czyli tła planszy i ramy
             for(int j =1;j<12;j++){
                 if(board[i][j].getName().equals("wall")){
                     g2d.drawImage(wall, i*field_size, j*field_size, null);
                 }
                 else if(board[i][j].getName().equals("barrel")){
                     g2d.drawImage(barrel, i*field_size, j*field_size, null);
                 }
             }
        }

    }

}
