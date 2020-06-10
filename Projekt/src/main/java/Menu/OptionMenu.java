package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;
import Settings.GAMESETTINGS;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends ScreenFrame implements ActionListener, ChangeListener { //dziedziczy po ScreenFrame- klasa zawierająca 3 bloki i przycisk do cofania

    private JButton opcja1, opcja2;
    private JSlider suwak;
    public OptionMenu(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
        super(window, previous_menu, menu_music);

        JPanel lewy = new JPanel();
        JPanel srodek = new JPanel();
        JPanel prawy = new JPanel();

        lewy.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        lewy.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        lewy.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        second_panel.add(lewy);

        srodek.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        srodek.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT));
        srodek.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT));
        second_panel.add(srodek);

        prawy.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        prawy.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        prawy.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        second_panel.add(prawy);

        JPanel przesuniecie = new JPanel();
        przesuniecie.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        przesuniecie.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/8));
        przesuniecie.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/8));
        srodek.add(przesuniecie);

        JPanel music_menu = new JPanel();
        music_menu.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        music_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/5));
        music_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/5));
        srodek.add(music_menu);

        JPanel glosnosc_menu = new JPanel();
        glosnosc_menu.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        glosnosc_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        glosnosc_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        srodek.add(glosnosc_menu);

        suwak = new JSlider(0,100,0);
        suwak.setMinorTickSpacing(1);
        suwak.setValue(80);
        suwak.setPaintTicks(true);
        suwak.setSnapToTicks(true);
        suwak.setBackground(GAMESETTINGS.SZARY_JASNY);
        suwak.addChangeListener(this);

        JLabel glosnosc = new JLabel("  Głośność muzyki  ");
        glosnosc.setFont( new Font("Dialog", Font.BOLD, GAMESETTINGS.TEXT_SIZE));
        glosnosc.setForeground(GAMESETTINGS.BIALY);
        glosnosc_menu.add(glosnosc);
        glosnosc_menu.add(suwak);
        back.addActionListener(this);//to jest obsługa przycisku cofania
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer(getClass().getResourceAsStream("/sounds/click_sound.wav"));
        menuButton.playOnce();

        Object source = e.getSource();
        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
        else if(source == opcja1)
        {
            opcja1.setVisible(false);
            opcja2.setVisible(true);
        }
        else if(source == opcja2)
        {
            opcja2.setVisible(false);
            opcja1.setVisible(true);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == suwak){
            int value = suwak.getValue();
            menu_music.setLoud((float)value);
        }
    }

}
