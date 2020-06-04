package Game;

import Additions.Loader;
import Additions.SoundPlayer;
import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.DamageArea;
import Objects.Hero;
import Objects.Bomb.Bomb;
import Objects.PowerUp.MoarHand;
import Settings.BLOCK_TYPE;
import Settings.KEY;
import Settings.PLAYER;
import Menu.EndMenu;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DrawFrame extends JPanel implements KeyListener {
    private int width, heigh;
    private Timer timer;
    private PlayField board;
    /*
    wzorzec projektowy: Abstract Factory(10 strona), tworzymy listy obiektów abstrakcyjnych typu GameObject,
    do niego dodajemy klasy takie jak Hero, Bomb, MoarBomb, MoarHand, które dziedziczą po GameObject,
    jest też wspólna metoda draw, napisywana przez każdą klase i wywoływana wspólnie w paintComponent

     */
    private List<Hero> game_heros; //gracze
    private List<Hero> dead_heros;
    private List<GameObject> powerUps;      //power upy
    private List<DamageArea> damageAreas;   //bloki obrażeń
    private List<Bomb> bombList;            //bomby

    private Hero player_one;
    private Hero player_two;

    private boolean[] player_one_moves;
    private boolean[] player_two_moves;

    private boolean is_player_one_here;
    private boolean is_player_two_here;//będzie to w konstruktorze

    private JFrame window;
    private JPanel scores;
    private List<JLabel> display_scores;
    private int timeout;
    private SoundPlayer game_music;
    public DrawFrame(JFrame window, JPanel scores) {
        this.window = window;
        this.scores = scores;

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screen_size.width;
        this.heigh = screen_size.height;

        this.width = (int)(5*this.width/6 *(0.80)); // to się zmieni
        this.heigh = (int)(this.heigh *(0.80));
        setPreferredSize(new Dimension(5*this.width/6,this.heigh));

        setFocusable(true);
        setRequestFocusEnabled(true);
        requestFocus();
        requestFocusInWindow();

        addKeyListener(this);

        board = new Loader().getBoard();
      //  Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
      //  board  =  new PlayField(g.fromJson(Data.fields_data,Field[][].class));
      //  board.reposition();//trzeba jeszcze przypisać dokładne współrzędne a to wyżej nie ładuje konstruktora
       // board = FieldTestMain();

        game_heros = new ArrayList<>();//pojemnik na postacie
        dead_heros = new ArrayList<>();
        bombList = new ArrayList<>();//bomby
        powerUps = new ArrayList<>();//pwoer upy
        damageAreas =  new ArrayList<>();//bloki obrażeń

        player_one_moves = new boolean[]{false,false,false,false,false};
        player_two_moves = new boolean[]{false,false,false,false,false};
        is_player_one_here = true;
        is_player_two_here = true;

        player_one = new Hero(new Dimension(1,1),"blue_bomberman", "/blue/niebieski.png");
        player_one.setPlayer(board.getBoard(),bombList,3,2,2,1);


        player_two = new Hero(new Dimension(10,1),"green_bomberman","/blue/niebieski.png");
        player_two.setPlayer(board.getBoard(),bombList,3,2,2,1);

        powerUps.add(new MoarHand(new Dimension(3,1)));
        game_heros.add(player_one);
        game_heros.add(player_two);

        game_music = new SoundPlayer("sounds/game_music.wav");
        game_music.playContinoulsly();
        timeout = 0;
        this.display_scores = make_scoreboard();
        timer = new Timer(15, e -> {

            calculate();
            update_scores();
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
            if (keycode == KEY.SPACE) {
                for (Bomb b : bombList) {
                    if (player_one.getBlock_position().equals(b.getBlock_position())) {  //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                        if(player_one.isMove_bomb() && !player_one.isBomb_in_hand()){
                            player_one.setPicked_bomb(b);
                            player_one.setBomb_in_hand(true);
                        }

                        return;
                    }

                }
                if(player_one.getBombs() > 0) {
                    player_one.setBombs(player_one.getBombs() - 1);
                    //    player_one_moves[4] = true;
                    Bomb bomb = new Bomb(player_one.getBlock_position(), player_one.getBomb_power(), "blue", "/blue/dynamit.png", player_one);
                    //    game_objects.add(bomb);
                    bombList.add(bomb);
                }
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
            if(keycode==KEY.ENTER){
                for(Bomb b: bombList){
                    if(player_two.getBlock_position().equals(b.getBlock_position())) { //jeśli w tym bloku jest już bomba to nie można postawić kolejnej
                        if(player_two.isMove_bomb() && !player_two.isBomb_in_hand()){
                            player_two.setPicked_bomb(b);
                            player_two.setBomb_in_hand(true);
                        }
                        return;
                    }
                }
                if( player_two.getBombs() > 0) {
                    player_two.setBombs(player_two.getBombs() - 1);
                    System.out.println(player_two.getBombs());
                    Bomb bomb = new Bomb(player_two.getBlock_position(), player_two.getBomb_power(), "blue", "/blue/dynamit.png", player_two);

                    bombList.add(bomb);
                    System.out.println("bomba podłożona");
                }
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

    protected void calculate()  {
        //pole ma strukturę siatki, otoczone jest blokami typu wall- czyli nieziszczalne i nieprzechodząca
        //liczenie pozycji postaci i kolizji

        if(timeout> PLAYER.CZAS_GRY)
            end_game();
        timeout++;

        if(game_heros.size()==1){
            end_game();
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
        SoundPlayer bomb_sound;
        int a = 0;
        for(int i = 0;i<bombList.size()-a;i++){ // uniwersalna funkcja sprawdzająca wybuchy bomb - dla wszystkich postaci
            bomb = bombList.get(i);
            if(!bomb.checkState()){
                bomb_sound = new SoundPlayer("sounds/bomb_sound.wav");
                bomb_sound.playOnce();

                bombList.remove(bomb);
                bomb.getOwner().setBombs(bomb.getOwner().getBombs()+1);

                dm = new DamageArea(bomb.getBlock_position(),bomb.getPower(),bomb.getColor(),bomb.getOwner(),board,powerUps);
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
                hero.setScore(hero.getScore()+(dead_heros.size()*300));
                dead_heros.add(hero);
                game_heros.remove(hero);
                display_scores.remove(display_scores.get(i));
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

    private void end_game(){

        for(Hero hero:game_heros){
            hero.setScore(hero.getScore()+(dead_heros.size()*300));
        }
        for(Hero hero:game_heros){ //musimy tą pętle powtórzyć, bo punktacja byłaby przekłamana
            dead_heros.add(hero);  //dodajemy do listy ostatniego gracza
        }


        removeKeyListener(this);
        try {
            TimeUnit.SECONDS.sleep(PLAYER.USPIENIE);
        }catch (InterruptedException ex){
            System.out.println("Sleen nie udany");;
        }


        setVisible(false);
        window.getContentPane().removeAll();
        window.add(new EndMenu(window,dead_heros));
        window.repaint();

        timer.stop();
        game_music.stop();
    }

    private ArrayList<JLabel> make_scoreboard(){
        ArrayList<JLabel> jlabels = new ArrayList<>();
        JLabel jlb;
        for(Hero hero:game_heros) {
            jlb = new JLabel("");
            this.scores.add(jlb);
            jlabels.add(jlb);
        }
        return jlabels;
      //  this.scores.add(new Label(""));
    }

    private void update_scores(){

        for(int i =0;i<game_heros.size();i++){
            display_scores.get(i).setText("Gracz "+game_heros.get(i).getName()+" wynik: "+ game_heros.get(i).getScore() );
        }

    }

}
