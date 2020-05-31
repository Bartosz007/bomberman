package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;
import Objects.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class EndMenu extends JPanel implements ActionListener {
    private JButton exit ;
    private JButton back ;
    private JFrame window;
    private SoundPlayer menu_music;
    public EndMenu(JFrame window, List<Hero> heros) {

        this.window = window;

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setMaximumSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.PAGE_AXIS));
        add(main_menu);

        JPanel first_panel = new JPanel();
        first_panel.setPreferredSize(new Dimension(screen_size.width,3*screen_size.height/4));
        first_panel.setMaximumSize(new Dimension(screen_size.width,3*screen_size.height/4));
        first_panel.setBackground(new Color(120,120,120));
        first_panel.setLayout(new BoxLayout(first_panel,BoxLayout.PAGE_AXIS));
        main_menu.add(first_panel);

        JPanel second_panel = new JPanel();
        second_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/4));
        second_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/4));
        second_panel.setBackground(new Color(80,80,80));
        main_menu.add(second_panel);

        JLabel header = new JLabel("Najlepsze wyniki");
        first_panel.add(header);

        JPanel score_board = new JPanel();
        score_board.setPreferredSize(new Dimension(screen_size.width/2,screen_size.height/4));
        score_board.setMaximumSize(new Dimension(screen_size.width/2,screen_size.height/4));
        score_board.setBackground(Color.magenta);
        first_panel.setLayout(new BoxLayout(first_panel,BoxLayout.PAGE_AXIS));
        first_panel.add(score_board);

        Hero h;
        for(int i =heros.size()-1;i>=0;i--){
            score_board.add(new JLabel((heros.size() - i) +". "+ heros.get(i).getName() +" osiągnął wynik:  "+  heros.get(i).getScore()));
        }

        /*
        TODO -to to tylko, żeby było lepiej widać - system punktacji
        wynik to punkty za podnoszenie power upów (50-100, chyba....)
        za zabójstwo dostaje się 500pkt
        za samobója -500pkt
        za przetrwanie dostaje się n*300, gdzie n to liczba wyeliminowanych graczy

         */

        menu_music = new SoundPlayer("sounds/end_music.wav");
        menu_music.playContinoulsly();

        exit = new JButton("EXIT");
        back = new JButton("MAIN MENU");

        second_panel.add(exit);
        second_panel.add(back);
        exit.addActionListener(this);
        back.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();

        if(source==exit){
            this.window.dispose();
        }else{
            setVisible(false);
            menu_music.stop();
            this.window.getContentPane().removeAll();
            this.window.add(new MainMenu(window));

        }


    }
}
