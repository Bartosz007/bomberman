package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener { //klasa dziedzicząca po JPanel, implementująca funckje ActionListener(pojęcie intefejsu)
    private JButton start_game, menu, button3, button4, exit;
    private JFrame window;


    public MainMenu(JFrame window) {
        this.window = window;
        //rozmiar okna
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

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
        button_menu.setLayout(new BoxLayout(button_menu,BoxLayout.PAGE_AXIS));
        center_panel.add(button_menu); //dodanie klasy JPanel do obiektu nadrzędnego - środkowy panel

        //utworzenie przycisków
        start_game = new JButton("Start");
        menu = new JButton("Menu");
        button3 = new JButton("button3");
        button4 = new JButton("button4");
        exit = new JButton("Exit");

        //przypisanie listenerów(zdarzeń) do przycisków
        start_game.addActionListener(this);//this -tutaj to oznacza interfejs ActionListener
        menu.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        exit.addActionListener(this);

        //dodanie przycisków do button_menu
        button_menu.add(start_game);
        button_menu.add(menu);
        button_menu.add(button3);
        button_menu.add(button4);
        button_menu.add(exit);
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
        else if(source == button3) {
            System.out.println("button3");
            //jakieś eventy przycisku 3
        }
        else if(source == button4) {
            System.out.println("button4");
            //jakieś eventy przycisku 3
        }
        else if(source == exit) {
            window.dispose();
        }

    }

}
