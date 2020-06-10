package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;
import Game.GamePanel;
import Settings.GAMESETTINGS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Settings.GAMESETTINGS.BUTTONWIDTH;

public class MapMenu extends ScreenFrame implements ActionListener {

    public MapMenu(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
        super(window, previous_menu, menu_music);
        setFocusable(true);
        JPanel icon_container = new JPanel();
        JScrollPane map_chose_scroll = new JScrollPane(icon_container);
        icon_container.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        second_panel.add(map_chose_scroll);
        JButton bt;
        for (int i = 0; i < 4; i++) {

            try {
                Image icon;
                icon = ImageIO.read(getClass().getResource("/mapy/GrafikaM"+i+".png"));
                bt = new JButton();
                bt.setIcon(new ImageIcon(icon));
                bt.addActionListener(this);
                bt.setFont(new Font("Dialog", Font.BOLD, GAMESETTINGS.TEXT_SIZE));
                bt.setFocusable(false);
                bt.setName("Mapa" + i);
                bt.setMaximumSize(new Dimension(BUTTONWIDTH,BUTTONWIDTH));
                bt.setPreferredSize(new Dimension(BUTTONWIDTH,BUTTONWIDTH));
                icon_container.add(bt);

            } catch (IOException e) {
                System.out.println("Nie wczytało pliku");
            }
        }
        back.addActionListener(this); // obsługa przycisku cofania się

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer(getClass().getResourceAsStream("/sounds/click_sound.wav"));
        menuButton.playOnce();

        Object source = e.getSource();

        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }else{
            String name = ((JButton)source).getName();
            setVisible(false);
            window.add(new GamePanel(window,this,name,menu_music));
            menu_music.changeTrack(getClass().getResourceAsStream("/sounds/game_music.wav"));
            menu_music.playContinoulsly();
        }

    }
}
