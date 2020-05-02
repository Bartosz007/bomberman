package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawFrame extends JPanel implements KeyListener {
    private int width,heigh;
    private int x = 0;
    private int y = 0;
    private Timer timer;
    private Dimension size;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private boolean key_a,key_s,key_d,key_w;

    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.85));
        this.heigh = (int)(size.height *(0.93));
        setPreferredSize(new Dimension(5*size.width/6,size.height));

        requestFocusInWindow();
        setFocusable(true);
        //https://stackoverflow.com/questions/22741215/how-to-use-key-bindings-instead-of-key-listeners
     //   addKeyListener(this);
   //     getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
   //     getActionMap().put(MOVE_UP, new MoveAction(1, 1));

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate_position();
                repaint();
            }
        });
        timer.start();
    }
    void anAction(){
        System.out.println("bla");
    }
    protected void calculate_position(){
        if(key_a){
            x=x-10;
        }
        if(key_s){
            y=y+10;
        }
        if(key_d){
            x = x+10;
        }
        if(key_w){
            y=y-10;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g.create();

        g2d.setColor(Color.magenta);//rysoanie ramki
        g2d.drawRect(0,0,width,heigh);

        g2d.setColor(Color.magenta);
        g2d.fillRect(x,y,30,30);
       // Ellipse2D.Double shape = new Ellipse2D.Double(x, y, 30, 30);//inny sposób ze zmiennymi double
       // g2d.draw(shape); //https://stackoverflow.com/questions/9650000/how-to-draw-a-circle-positioning-it-at-a-double-value-instead-of-a-int

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



    /*private Dimension size;
    private int width,heigh;
    private int x =0, y =0;
    Timer timer = new Timer(5,this);


    public DrawFrame(Dimension size) {
        this.size = size;
        this.width = (int)(5*size.width/6 *(0.85));
        this.heigh = (int)(size.height *(0.93));
        setPreferredSize(new Dimension(5*size.width/6,size.height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.magenta);//rysowanie ramki
        g.fillRect(x,y,20,20);
        timer.start();

        g.setColor(Color.white);//rysowanie białego prostokąta
        g.fillRect(0,0,width,heigh);

        g.setColor(Color.magenta);//rysowanie ramki
        g.drawRect(0,0,width,heigh);

        for(int i =0;i<1000;i++){
            g.setColor(Color.white);//rysowanie białego prostokąta
            g.fillRect(0,0,width,heigh);

            g.setColor(Color.magenta);//rysowanie ramki
            g.drawRect(0,0,width,heigh);

            g.setColor(Color.magenta);//rysowanie ramki
            g.fillRect(i,i,20,20);

        }


        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, heigh);
        g2d.setColor(Color.magenta);
        //rectangle.set
        // kolo
        Ellipse2D circle = new Ellipse2D.Double(300, 300, 38, 30);

        g2d.draw(rectangle);
        g2d.draw(circle);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    */

}
