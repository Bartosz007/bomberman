package Menu;

import Settings.GAMESETTINGS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener { //klasa dziedzicząca po JPanel, implementująca funckje ActionListener(pojęcie intefejsu)
    private JButton start_game, menu, instrukcje, exit;
    private JFrame window;


    public MainMenu(JFrame window) {
        this.window = window;
        //rozmiar okna
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        screen_size.width= GAMESETTINGS.WIDTH;
        screen_size.height=GAMESETTINGS.HEIGHT;
        //okno główne
        JPanel main_menu = new JPanel();  //utworzenie klasy
        main_menu.setBackground(Color.black); //nadanie koloru framowi
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.LINE_AXIS)); //ustawienie typu pozycjonowania - w parametrze klasa BoxLayout(cel_pozycjonowania,kierunek) LINE_axis - poziomo
        add(main_menu); //dodanie klasy JPanel do obiektu nadrzędnego (plik StartGame.java)

        //lewy panel
        JPanel left_panel = new JPanel();
        left_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height)); //aby rozmiary dobrze pasowały, trzeba używać metod PrefferedSize i MaximumSize
        left_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));   //w parametrach przyjmują klasę Dimension(szerokość,wysokość)
        left_panel.setBackground(new Color(120,120,120));//nadanie koloru klasą kolor
        main_menu.add(left_panel);

        //srodkowy panel
        JPanel center_panel = new JPanel();
        center_panel.setPreferredSize(new Dimension(screen_size.width/2,screen_size.height));
        center_panel.setMaximumSize(new Dimension(screen_size.width/2,screen_size.height));
        center_panel.setBackground(new Color(80,80,80));
        main_menu.add(center_panel);

        //prawy panel
        JPanel right_panel = new JPanel();
        right_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height));
        right_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));
        right_panel.setBackground(new Color(120,120,120));
        main_menu.add(right_panel);

        //panel przycisków(dodany w celu lepszego pozycjonownia wyglądu)
        JPanel button_menu = new JPanel();
        button_menu.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height/10));
        button_menu.setLayout(new BoxLayout(button_menu,BoxLayout.PAGE_AXIS));
        button_menu.setBackground(new Color(80,80,80));
        center_panel.add(button_menu); //dodanie klasy JPanel do obiektu nadrzędnego - środkowy panel

        JPanel start_panel = new JPanel();
        start_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height/6));
        start_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));
        start_panel.setBackground(new Color(80,80,80));
        center_panel.add(start_panel);

        JPanel opcje_panel = new JPanel();
        opcje_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height/6));
        opcje_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));
        opcje_panel.setBackground(new Color(80,80,80));
        center_panel.add(opcje_panel);

        JPanel instrukcje_panel = new JPanel();
        instrukcje_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height/6));
        instrukcje_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));
        instrukcje_panel.setBackground(new Color(80,80,80));
        center_panel.add(instrukcje_panel);

        JPanel wyjscie_panel = new JPanel();
        wyjscie_panel.setPreferredSize(new Dimension(screen_size.width/4,screen_size.height/6));
        wyjscie_panel.setMaximumSize(new Dimension(screen_size.width/4,screen_size.height));
        wyjscie_panel.setBackground(new Color(80,80,80));
        center_panel.add(wyjscie_panel);

        /*JPanel opcje_panel = new JPanel();
        JPanel instrukcje_panel = new JPanel();
        JPanel wyjscie_panel = new JPanel();*/

        start_game = new JButton("\n Start \n");
        start_game.setPreferredSize(new Dimension(160, 70));
        start_game.setFont( new Font("Dialog", Font.BOLD, 24));
        menu = new JButton("Opcje");
        menu.setPreferredSize(new Dimension(160, 70));
        menu.setFont( new Font("Dialog", Font.BOLD, 24));
        instrukcje = new JButton("Instrukcje");
        instrukcje.setPreferredSize(new Dimension(160, 70));
        instrukcje.setFont( new Font("Dialog", Font.BOLD, 24));
        exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(160, 70));
        exit.setFont( new Font("Dialog", Font.BOLD, 24));

        //przypisanie listenerów(zdarzeń) do przycisków
        start_game.addActionListener(this);//this -tutaj to oznacza interfejs ActionListener
        menu.addActionListener(this);
        instrukcje.addActionListener(this);
        exit.addActionListener(this);


        //dodanie przycisków do button_menu
        start_panel.add(start_game);
        opcje_panel.add(menu);
        instrukcje_panel.add(instrukcje);
        wyjscie_panel.add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //obsługa zdarzeń przycisku z pomocą interwejsu o nazwie actionPerformed
        Object source = e.getSource();  //uzyskanie klikniętego przycisku

        if(source == start_game) { //sprawdzanie, który przycisk się kliknęło
            setVisible(false);
            window.add(new GameTypeMenu(window,this));
        }
        else if(source == menu) {
            setVisible(false);
            window.add(new OptionMenu(window,this));
        }
        else if(source == instrukcje) {
            setVisible(false);
            window.add(new Instruction(window,this));
        }
        else if(source == exit) {
            window.dispose();
        }

    }

}
