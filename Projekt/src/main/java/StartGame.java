import Menu.MainMenu;

import Settings.GAMESETTINGS;
import javax.swing.JFrame;

public class StartGame extends JFrame {

    public StartGame(){
        super("Hello world");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); //maksymalizacja okna, zmienić rozmiar
        setSize(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT);

        add(new MainMenu(this));
    }

}
