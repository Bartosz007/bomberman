import Game.GamePanel;
import Menu.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartGame extends JFrame {

    public StartGame(){
        super("Hello world");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

     //   Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
      //  System.out.println(screen_size.width+" "+screen_size.height);

        //dodanie menu do okna gry
      //  add(new MainMenu(this));
        add(new GamePanel(this,new MainMenu(this),"bt1"));//przycisk cofnij nie będzie działać
    }


}
