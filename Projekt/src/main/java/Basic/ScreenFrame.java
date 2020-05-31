package Basic;

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

        main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setMaximumSize(new Dimension(screen_size.width,screen_size.height));
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.PAGE_AXIS));
        add(main_menu);

        first_panel = new JPanel();
        first_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/4));
        first_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/4));
        first_panel.setBackground(new Color(120,120,120));
        first_panel.setLayout(new BoxLayout(first_panel,BoxLayout.LINE_AXIS));
        main_menu.add(first_panel);

        second_panel = new JPanel();
        second_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/2));
        second_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/2));
        second_panel.setBackground(new Color(80,80,80));
        second_panel.setLayout(new BoxLayout(second_panel,BoxLayout.LINE_AXIS));
        main_menu.add(second_panel);

        thrid_panel = new JPanel();
        thrid_panel.setPreferredSize(new Dimension(screen_size.width,screen_size.height/4));
        thrid_panel.setMaximumSize(new Dimension(screen_size.width,screen_size.height/4));
        second_panel.setLayout(new BoxLayout(second_panel,BoxLayout.LINE_AXIS));
        thrid_panel.setBackground(new Color(120,120,120));
        main_menu.add(thrid_panel);

        //przycisk cofania się
        back = new JButton("Cofnij się");
        first_panel.add(back);
    }
}
