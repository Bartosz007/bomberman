package Menu;

import Basic.ScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends ScreenFrame implements ActionListener { //dziedziczy po ScreenFrame- klasa zawierająca 3 bloki i przycisk do cofania
    JButton opcja1, opcja2;
    public OptionMenu(JFrame window, JPanel previous_menu) {
        super(window, previous_menu);

        JPanel button_menu = new JPanel();
        button_menu.setLayout(new BoxLayout(button_menu,BoxLayout.PAGE_AXIS));
        button_menu.setBackground(new Color(80,80,80));
        second_panel.add(button_menu);

        opcja1 = new JButton("Music on");
        opcja1.setFont( new Font("Dialog", Font.BOLD, 24));
        opcja2 = new JButton("Music off");
        opcja2.setFont( new Font("Dialog", Font.BOLD, 24));
        opcja2.setVisible(false);
        button_menu.add(opcja1);
        opcja1.addActionListener(this);
        button_menu.add(opcja2);
        opcja2.addActionListener(this);


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
