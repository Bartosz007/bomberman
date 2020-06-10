package Menu;

import Additions.SoundPlayer;
import Objects.Hero;
import Settings.GAMESETTINGS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EndMenu extends JPanel implements ActionListener {
    private JButton exit ;
    private JButton back ;
    private JFrame window;
    private SoundPlayer menu_music;
    public EndMenu(JFrame window, List<Hero> heros) {
        this.window = window;

        JPanel main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT));
        main_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT));
        main_menu.setBackground(GAMESETTINGS.SZARY_JASNY);
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.PAGE_AXIS));
        add(main_menu);

        JPanel first_panel = new JPanel();
        first_panel.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,3*GAMESETTINGS.HEIGHT/20));
        first_panel.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,3*GAMESETTINGS.HEIGHT/20));
        first_panel.setBackground(GAMESETTINGS.SZARY_JASNY);
        first_panel.setLayout(new BoxLayout(first_panel,BoxLayout.PAGE_AXIS));
        main_menu.add(first_panel);

        JPanel second_panel = new JPanel();
        second_panel.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,3*GAMESETTINGS.HEIGHT/5));
        second_panel.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,3*GAMESETTINGS.HEIGHT/5));
        second_panel.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        main_menu.add(second_panel);

        JPanel third_panel = new JPanel();
        third_panel.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        third_panel.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        third_panel.setBackground(GAMESETTINGS.SZARY_JASNY);
        third_panel.setLayout(new BoxLayout(third_panel,BoxLayout.PAGE_AXIS));
        main_menu.add(third_panel);

        JLabel header = new JLabel("Wyniki");
        header.setFont( new Font("Dialog", Font.BOLD, 36));
        header.setForeground(GAMESETTINGS.BIALY);
        second_panel.add(header);

        JPanel button_menu = new JPanel();
        button_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        button_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        button_menu.setBackground(GAMESETTINGS.SZARY_JASNY);
        third_panel.add(button_menu);

        JPanel score_board = new JPanel();
        score_board.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        score_board.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,GAMESETTINGS.HEIGHT/4));
        score_board.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        second_panel.add(score_board);

        Hero h;
        for(int i =heros.size()-1;i>=0;i--){
            JLabel napis = new JLabel((heros.size() - i) +". "+ heros.get(i).getName() +" osiągnął wynik:  "+  heros.get(i).getScore());
            napis.setFont( new Font("Dialog", Font.BOLD, 24));
            napis.setForeground(GAMESETTINGS.BIALY);
            score_board.add(napis);

        }
        menu_music = new SoundPlayer(getClass().getResourceAsStream("/sounds/end_music.wav"));
        menu_music.playContinoulsly();

        exit = new JButton("Wyjście");
        back = new JButton("Menu główne");

        exit.setFont( new Font("Dialog", Font.BOLD, 24));
        back.setFont( new Font("Dialog", Font.BOLD, 24));
        button_menu.add(back);
        button_menu.add(exit);
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
