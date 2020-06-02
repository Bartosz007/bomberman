package Menu;

import Basic.ScreenFrame;
import Settings.GAMESETTINGS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends ScreenFrame implements ActionListener { //dziedziczy po ScreenFrame- klasa zawierająca 3 bloki i przycisk do cofania
    JButton opcja1, opcja2;
    JSlider suwak;
    public OptionMenu(JFrame window, JPanel previous_menu) {
        super(window, previous_menu);

        JPanel lewy = new JPanel();
        JPanel srodek = new JPanel();
        JPanel prawy = new JPanel();

        lewy.setBackground(new Color(80,80,80));
        lewy.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        lewy.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        second_panel.add(lewy);

        srodek.setBackground(new Color(80,80,80));
        srodek.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT));
        srodek.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT));
        second_panel.add(srodek);

        prawy.setBackground(new Color(80,80,80));
        prawy.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        prawy.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/3));
        second_panel.add(prawy);

        JPanel przesuniecie = new JPanel();
        przesuniecie.setBackground(new Color(80,80,80));
        przesuniecie.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/8));
        przesuniecie.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/8));
        srodek.add(przesuniecie);

        JPanel music_menu = new JPanel();
        music_menu.setBackground(new Color(80,80,80));
        music_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/5));
        music_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/5));
        srodek.add(music_menu);

        JPanel predkosc_menu = new JPanel();
        predkosc_menu.setBackground(new Color(80,80,80));
        predkosc_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/4));
        predkosc_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH/3,GAMESETTINGS.HEIGHT/4));
        srodek.add(predkosc_menu);

        suwak = new JSlider(0,5,0);
        suwak.setMinorTickSpacing(1);
        suwak.setPaintTicks(true);
        suwak.setSnapToTicks(true);
        suwak.setBackground(new Color(120,120,120));


        JLabel muzyka_napis = new JLabel("  Opcje muzyki  ");
        muzyka_napis.setFont( new Font("Dialog", Font.BOLD, 24));
        muzyka_napis.setForeground(new Color(255,255,255));

        JLabel predkosc = new JLabel("  Prędkość początkowa  ");
        predkosc.setFont( new Font("Dialog", Font.BOLD, 24));
        predkosc.setForeground(new Color(255,255,255));

        opcja1 = new JButton("Music on");
        opcja1.setFont( new Font("Dialog", Font.BOLD, 24));

        opcja2 = new JButton("Music off");
        opcja2.setFont( new Font("Dialog", Font.BOLD, 24));
        opcja2.setVisible(false);

        music_menu.add(muzyka_napis);
        music_menu.add(opcja1);
        opcja1.addActionListener(this);
        srodek.add(opcja2);
        predkosc_menu.add(predkosc);
        opcja2.addActionListener(this);
        predkosc_menu.add(suwak);

        back.addActionListener(this);//to jest obsługa przycisku cofania
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
        else if(source == opcja1)
        {
            System.out.println("opcja głosu");
            opcja1.setVisible(false);
            opcja2.setVisible(true);
        }
        else if(source == opcja2)
        {
            System.out.println("opcja głosu");
            opcja2.setVisible(false);
            opcja1.setVisible(true);
        }
    }

}
