package Basic;


import Settings.GAMESETTINGS;
import Additions.SoundPlayer;


import javax.swing.*;
import java.awt.*;

public abstract class ScreenFrame extends JPanel { //screen przykładowy z domyślnymi wartosciami wszystkiego
    protected JButton back;
    protected JFrame window;
    protected JPanel previous_menu;

    protected JPanel main_menu,first_panel,second_panel,thrid_panel;
    protected SoundPlayer menu_music;
    public ScreenFrame(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {

        this.window = window;
        this.previous_menu =previous_menu;
        this.menu_music = menu_music;

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        screen_size.width= GAMESETTINGS.WIDTH;
        screen_size.height=GAMESETTINGS.HEIGHT;
        main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setMaximumSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.PAGE_AXIS));
        add(main_menu);

        first_panel = new JPanel();
        first_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/12));
        first_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/12));
        first_panel.setBackground(GAMESETTINGS.SZARY_JASNY);
        first_panel.setLayout(new BoxLayout(first_panel,BoxLayout.LINE_AXIS));
        main_menu.add(first_panel);

        second_panel = new JPanel();
        second_panel.setPreferredSize(new Dimension(screen_size.width,2*screen_size.height/3));
        second_panel.setMaximumSize(new Dimension(screen_size.width,2*screen_size.height/3));
        second_panel.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        second_panel.setLayout(new BoxLayout(second_panel,BoxLayout.LINE_AXIS));
        main_menu.add(second_panel);

        thrid_panel = new JPanel();
        thrid_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/4));
        thrid_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/4));
        second_panel.setLayout(new BoxLayout(second_panel,BoxLayout.LINE_AXIS));
        thrid_panel.setBackground(GAMESETTINGS.SZARY_JASNY);
        main_menu.add(thrid_panel);

        //przycisk cofania się
        back = new JButton("Cofnij");
        back.setFont( new Font("Dialog", Font.BOLD, 24));
        thrid_panel.add(back);
    }
}
