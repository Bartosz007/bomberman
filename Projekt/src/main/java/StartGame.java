import Menu.MainButtonMenu;

import javax.swing.*;
public class StartGame extends JFrame{

    public StartGame(){
        super("Hello world");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

     //   Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
      //  System.out.println(screen_size.width+" "+screen_size.height);

        //dodanie menu do okna gry
        add(new MainButtonMenu(this));

    }
}
