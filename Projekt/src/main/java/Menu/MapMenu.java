package Menu;

import Frames.ScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapMenu extends ScreenFrame implements ActionListener {

    public MapMenu(JFrame window, JPanel previous_menu) {
        super(window, previous_menu);

        JPanel icon_container = new JPanel();
        icon_container.setBackground(Color.BLUE);

        JScrollPane map_chose = new JScrollPane(icon_container);
        second_panel.add(map_chose);

        for (int i=0;i<500;i++){//to zniknie na rzecz ikonek z mapkami i obsługą przycisków
            icon_container.add(new JButton("bt"+i));
        }

        back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
    }
}
