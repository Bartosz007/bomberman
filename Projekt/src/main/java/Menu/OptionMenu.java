package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends ScreenFrame implements ActionListener { //dziedziczy po ScreenFrame- klasa zawierająca 3 bloki i przycisk do cofania
    JButton opcja;
    public OptionMenu(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
        super(window, previous_menu, menu_music);

        opcja = new JButton("opcja");
        second_panel.add(opcja);
        opcja.addActionListener(this);

        back.addActionListener(this);//to jest obsługa przycisku cofania
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer("sounds/click_sound.wav");
        menuButton.playOnce();

        Object source = e.getSource();
        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
        else if(source == opcja){
            System.out.println("opcja");
        }

    }

}
