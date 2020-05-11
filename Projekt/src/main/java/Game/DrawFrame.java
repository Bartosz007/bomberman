package Game;

import Basic.Field;
import Basic.Hero;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawFrame extends JPanel implements KeyListener {
    private int width,heigh;
    private int x = 0;
    private int y = 0;

    private Timer timer;
    private Dimension size;
    private boolean key_a,key_s,key_d,key_w;
    private Field[][] board = new Field[15][13];
    private int field_size = 45;//rozmiar pola w pikselach

    private Hero hero;

    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.85));
        this.heigh = (int)(size.height *(0.93));
        setPreferredSize(new Dimension(5*size.width/6,size.height));

        requestFocusInWindow();
        setFocusable(true);
        addKeyListener(this);

        Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
        board  = g.fromJson(Data.fields_data,Field[][].class);

        //test InteliJ
       // hero = new Hero()

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate_position();
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


        Image image = null;//rysowanie postaci
        try {
            image = ImageIO.read(new File("src/main/resources/niebieski/", "niebieskiStoi.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

      //  Ellipse2D.Double shape = new Ellipse2D.Double(x, y, 50, 50);

        g2d.drawImage(image, x, y, null);



        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='a'){
            key_a = true;
        }
        if(e.getKeyChar()=='s'){
            key_s = true;
        }
        if(e.getKeyChar()=='d'){
            key_d = true;
        }
        if(e.getKeyChar()=='w'){
            key_w = true;
        }
       // System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar()=='a'){
            key_a = false;
        }
        if(e.getKeyChar()=='s'){
            key_s = false;
        }
        if(e.getKeyChar()=='d'){
            key_d = false;
        }
        if(e.getKeyChar()=='w'){
            key_w = false;
        }
    }

    protected void calculate_position(){
        if(key_a){
            x=x-2;
        }
        if(key_s){
            y=y+2;
        }
        if(key_d){
            x = x+2;
        }
        if(key_w){
            y=y-2;
        }
    }


    private void draw_field(Graphics2D g2d){

        try {//jedyen try catch bo chyba wystarczy
            Image background = ImageIO.read(new File("src/main/resources/elementyTla/", "tlo2.png"));
            g2d.drawImage(background,0,0,null);


            Image barrel = ImageIO.read(new File("src/main/resources/elementyTla/", "skrzynia.png"));
            Image wall = ImageIO.read(new File("src/main/resources/elementyTla/", "staly.png"));
            for(int i=1;i<14;i++){//nie renderujemy stałych, nie zależnych od mapy- czyli tła planszy i ramy
                for(int j =1;j<12;j++){
                    if(board[i][j].getImage().equals("staly.png")){
                        g2d.drawImage(wall, i*field_size, j*field_size, null);
                    }
                    else if(board[i][j].getImage().equals("skrzynia.png")){
                        g2d.drawImage(barrel, i*field_size, j*field_size, null);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
