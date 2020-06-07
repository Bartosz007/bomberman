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
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapMenu extends ScreenFrame implements ActionListener {

    public MapMenu(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
        super(window, previous_menu, menu_music);
        setFocusable(true);
        int nr;

        JPanel icon_container = new JPanel();
        JScrollPane map_chose_scroll = new JScrollPane(icon_container);
        icon_container.setBackground(GAMESETTINGS.SZARY_CIEMNY);
        second_panel.add(map_chose_scroll);
        /*try {
            Image icon;
            icon = ImageIO.read(getClass().getResource("resources/mapy/grafikaM0.PNG"));
            JButton button7 = new JButton("Button7");

            }
        } catch (IOException e) {
            System.out.println("Nie wczytało pliku "+ "resources/mapy/grafikaM0.PNG");
        }
*/
        //icon_container.add(button7);
        //  List<JButton> map_list = new ArrayList<>()
        JButton bt;

        for (int i = 0; i < 4; i++) {
            nr = i + 1;
            bt = new JButton("Mapa" + nr);
            bt.addActionListener(this);
            bt.setFont(new Font("Dialog", Font.BOLD, GAMESETTINGS.TEXT_SIZE));
            bt.setFocusable(false);
            bt.setName("Mapa" + i);
            icon_container.add(bt);
        }
            back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer("src/main/resources/sounds/click_sound.wav");
        menuButton.playOnce();

        Object source = e.getSource();

        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }else{
            String name = ((JButton)source).getName();
            setVisible(false);
            window.add(new GamePanel(window,this,name));
            menu_music.stop();
        }

    }
}
