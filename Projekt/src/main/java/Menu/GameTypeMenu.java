package Menu;

import Basic.ScreenFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTypeMenu extends ScreenFrame implements ActionListener {
    private JButton single,lan, multi;

    public GameTypeMenu(JFrame window, JPanel previous_menu) {
        super(window, previous_menu);

        //przyciski wyboru typu gry
        single = new JButton("SP");
        lan = new JButton("LAN");
        multi = new JButton("MP");

        single.addActionListener(this);
        lan.addActionListener(this);
        multi.addActionListener(this);

        second_panel.add(single);
        second_panel.add(lan);
        second_panel.add(multi);

        back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == back) {
            setVisible(false);
            previous_menu.setVisible(true);
        }
        else if(source == single) {
            setVisible(false);
            window.add(new MapMenu(window,this));

            System.out.println("single");
        }
        else if(source == lan) {
            System.out.println("lan");
        }
        else if(source == multi) {
            System.out.println("multi");
        }

    }

}
