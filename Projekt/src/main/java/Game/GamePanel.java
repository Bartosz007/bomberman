package Game;

import Settings.GAMESETTINGS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private JFrame window;
    private JPanel previous_menu;
    private String mapa;
    private JButton back;
    public GamePanel(JFrame window, JPanel previous_menu, String mapa)  {
        this.window = window;
        this.previous_menu = previous_menu;
        this.mapa = mapa;


        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = GAMESETTINGS.WIDTH;
        int heigh = GAMESETTINGS.HEIGHT;


        JPanel main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(width,heigh));
        main_menu.setMaximumSize(new Dimension(width,heigh));
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.LINE_AXIS));
        add(main_menu);

        JPanel left_menu = new JPanel();
        left_menu.setPreferredSize(new Dimension(6*width/25,heigh));
        left_menu.setMaximumSize(new Dimension(6*width/25,heigh));
        left_menu.setBackground(GAMESETTINGS.SZARY_JASNY);
        left_menu.setLayout(new BoxLayout(left_menu,BoxLayout.PAGE_AXIS));
        main_menu.add(left_menu);

        JPanel game_frame = new JPanel();
        game_frame.setPreferredSize(new Dimension(19*width/25,heigh));
        game_frame.setMaximumSize(new Dimension(19*width/25,heigh));
        game_frame.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        game_frame.setLayout(new BoxLayout(game_frame,BoxLayout.PAGE_AXIS));
        /*
        wzorzec projektowy: Abstract Facade(str 38)
        dodajemy do JPanela main_menu nową klasę(DrawFrame), która tworzy planszę gry, obługę klawiatury, rysowanie itp,
        wszystko to dzieje się po za 'fasadą" i klasa main_manu nie ma pojęcia co dalej się dzieje, natomiast wie, co otrzymała

         */

        JPanel hero_menu = new JPanel();
        hero_menu.setPreferredSize(new Dimension(width,heigh/8));
        hero_menu.setMaximumSize(new Dimension(width,heigh/8));
        hero_menu.setBackground(GAMESETTINGS.SZARY_JASNY);
        //hero_menu.setLayout(new BoxLayout(hero_menu,BoxLayout.PAGE_AXIS));
        left_menu.add(hero_menu);

        JPanel scores_menu = new JPanel();
        scores_menu.setPreferredSize(new Dimension(width,7*heigh/8));
        scores_menu.setMaximumSize(new Dimension(width,7*heigh/8));
        scores_menu.setBackground(GAMESETTINGS.SZARY_JASNY);
        scores_menu.setLayout(new BoxLayout(scores_menu,BoxLayout.PAGE_AXIS));
        left_menu.add(scores_menu);

        JPanel back_menu = new JPanel();
        back_menu.setPreferredSize(new Dimension(width,2*heigh/8));
        back_menu.setMaximumSize(new Dimension(width,2*heigh/8));
        back_menu.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        back_menu.setLayout(new BoxLayout(back_menu,BoxLayout.PAGE_AXIS));
        left_menu.add(back_menu);

        main_menu.add(game_frame);

        game_frame.add(new DrawFrame(window, scores_menu,mapa));



        back = new JButton("Cofnij się");
        back.setPreferredSize(new Dimension(130, 60));
        back.setFont( new Font("Dialog", Font.BOLD, 20));
        back.addActionListener(this);
        back.setFocusable(false);
        back_menu.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source ==back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
    }

    Action doNothing = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            System.out.println("test");
        }
    };

}
