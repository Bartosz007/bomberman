package Game;

import Additions.Loader;
import Additions.SoundPlayer;
import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.DamageArea;
import Objects.Hero;
import Objects.Bomb.Bomb;
import Settings.BLOCK_TYPE;
import Settings.GAMESETTINGS;
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

public class DrawFrame extends JPanel{
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
    public DrawFrame(JFrame window, JPanel scores, String mapa, SoundPlayer game_music) {
        this.window = window;
        this.scores = scores;
        this.game_music = game_music;

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screen_size.width;
        this.heigh = screen_size.height;

        this.width = (int)(5*this.width/6 *(0.80)); // to się zmieni
        this.heigh = (int)(this.heigh *(0.80));
        setPreferredSize(new Dimension(5*this.width/6,this.heigh));


        board = new Loader(mapa).getBoard();

        game_heros = new ArrayList<>();//pojemnik na postacie
        dead_heros = new ArrayList<>();
        bombList = new ArrayList<>();//bomby
        powerUps = new ArrayList<>();//pwoer upy
        damageAreas =  new ArrayList<>();//bloki obrażeń

        player_one_moves = new boolean[]{false,false,false,false,false};
        player_two_moves = new boolean[]{false,false,false,false,false};
        is_player_one_here = true;
        is_player_two_here = true;

        player_one = new Hero(new Dimension(1,1),"Niebieski bomberman", "/blue/paleta.png");
        player_one.setPlayer(board.getBoard(),bombList,3,1,2,2);

        player_two = new Hero(new Dimension(13,1),"Czerwony bomerman","/red/paleta.png");
        player_two.setPlayer(board.getBoard(),bombList,3,1,2,2);

        game_heros.add(player_one);
        game_heros.add(player_two);

        addKeyListeners();



        timeout = 0;
        this.display_scores = make_scoreboard();
        timer = new Timer(15, e -> {

            calculate();
            update_scores();
            repaint();

        });
        timer.start();
    }

    private Action released(int button) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(is_player_one_here) {
                    if (button == KEY.W) {
                        player_one_moves[0] = false;
                    }
                    if (button == KEY.A) {
                        player_one_moves[1] = false;
                    }
                    if (button == KEY.S) {
                        player_one_moves[2] = false;
                    }
                    if (button == KEY.D) {
                        player_one_moves[3] = false;
                    }
                    if (button == KEY.SPACE) {
                        if(player_one.isBomb_in_hand()){
                            throw_bomb(player_one);
                        }
                    }
                }

                if(is_player_two_here){
                    if(button==KEY.UP){
                        player_two_moves[0] = false;
                    }
                    if(button==KEY.LEFT){
                        player_two_moves[1] = false;
                    }
                    if(button==KEY.DOWN){
                        player_two_moves[2] = false;
                    }
                    if(button==KEY.RIGHT){
                        player_two_moves[3] = false;
                    }
                    if(button==KEY.ENTER){
                        if(player_two.isBomb_in_hand()){
                            throw_bomb(player_two);
                        }
                    }
                }
            }
        };
    }

    private Action pressed(int button) {

        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  System.out.println(button);
                if(is_player_one_here) {
                    if (button == KEY.W) {
                        player_one_moves[0] = true;
                    }
                    if (button == KEY.A) {
                        player_one_moves[1] = true;
                    }
                    if (button == KEY.S) {
                        player_one_moves[2] = true;
                    }
                    if (button == KEY.D) {
                        player_one_moves[3] = true;
                    }
                    if (button == KEY.SPACE) {
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
                    if(button==KEY.UP){
                        player_two_moves[0] = true;
                    }
                    if(button==KEY.LEFT){
                        player_two_moves[1] = true;
                    }
                    if(button==KEY.DOWN){
                        player_two_moves[2] = true;
                    }
                    if(button==KEY.RIGHT){
                        player_two_moves[3] = true;
                    }
                    if(button==KEY.ENTER){
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
                            Bomb bomb = new Bomb(player_two.getBlock_position(), player_two.getBomb_power(), "red", "/red/dynamit.png", player_two);

                            bombList.add(bomb);
                        }
                    }
                }
            }
        };

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g.create();


        board.draw_field(g2d);


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
                bomb_sound = new SoundPlayer(getClass().getResourceAsStream("/sounds/bomb_sound.wav"));
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
        is_player_one_here = false;
        is_player_two_here = false;
        game_music.changeTrack(getClass().getResourceAsStream("/sounds/death_sound.wav"));
        game_music.playOnce();
        try {
            TimeUnit.SECONDS.sleep(PLAYER.USPIENIE);
        }catch (InterruptedException ex){
            System.out.println("Sleep nie udany");
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
               display_scores.get(i).setForeground(GAMESETTINGS.BIALY);
               display_scores.get(i).setFont( new Font("Dialog", Font.BOLD, 22));
               display_scores.get(i).setText(" Gracz "+(i+1)+" Wynik:"+ game_heros.get(i).getScore());
           }

        }

    void addKeyListeners(){
        int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

        getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), KEY.W);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), KEY.A);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), KEY.S);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), KEY.D);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("SPACE"), KEY.SPACE);

        getInputMap(IFW).put(KeyStroke.getKeyStroke("released W"),KEY.RW);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released A"),KEY.RA);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released S"),KEY.RS);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released D"),KEY.RD);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released SPACE"),KEY.RSPACE);

        getActionMap().put(KEY.W, pressed(KEY.W));
        getActionMap().put(KEY.A, pressed(KEY.A));
        getActionMap().put(KEY.S, pressed(KEY.S));
        getActionMap().put(KEY.D, pressed(KEY.D));
        getActionMap().put(KEY.SPACE, pressed(KEY.SPACE));

        getActionMap().put(KEY.RW, released(KEY.W));
        getActionMap().put(KEY.RA, released(KEY.A));
        getActionMap().put(KEY.RS, released(KEY.S));
        getActionMap().put(KEY.RD, released(KEY.D));
        getActionMap().put(KEY.RSPACE, released(KEY.SPACE));

        getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), KEY.UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), KEY.LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), KEY.DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), KEY.RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("ENTER"), KEY.ENTER);

        getInputMap(IFW).put(KeyStroke.getKeyStroke("released UP"),KEY.RUP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released LEFT"),KEY.RLEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released DOWN"),KEY.RDOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released RIGHT"),KEY.RRIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released ENTER"),KEY.RENTER);

        getActionMap().put(KEY.UP, pressed(KEY.UP));
        getActionMap().put(KEY.LEFT, pressed(KEY.LEFT));
        getActionMap().put(KEY.DOWN, pressed(KEY.DOWN));
        getActionMap().put(KEY.RIGHT, pressed(KEY.RIGHT));
        getActionMap().put(KEY.ENTER, pressed(KEY.ENTER));

        getActionMap().put(KEY.RUP, released(KEY.UP));
        getActionMap().put(KEY.RLEFT, released(KEY.LEFT));
        getActionMap().put(KEY.RDOWN, released(KEY.DOWN));
        getActionMap().put(KEY.RRIGHT, released(KEY.RIGHT));
        getActionMap().put(KEY.RENTER, released(KEY.ENTER));

    }


}
