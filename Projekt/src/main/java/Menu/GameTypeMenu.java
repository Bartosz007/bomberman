package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTypeMenu extends ScreenFrame implements ActionListener {
    private JButton single,lan, multi;

      public GameTypeMenu(JFrame window, JPanel previous_menu, SoundPlayer menuMusic) {
        super(window, previous_menu, menuMusic);
        
        JPanel button_menu = new JPanel();
        button_menu.setLayout(new BoxLayout(button_menu,BoxLayout.PAGE_AXIS));
        button_menu.setBackground(new Color(80,80,80));
        second_panel.add(button_menu);

        JLabel wybor = new JLabel("  Wybierz rodzaj gry");
        wybor.setFont( new Font("Dialog", Font.BOLD, 35));
        wybor.setForeground(new Color(192,192,192));

        //przyciski wyboru typu gry
        single = new JButton(" Single Player");
        lan = new JButton(" Z przeciwnikiem przez sieć lokalną");
        multi = new JButton(" Z przeciwnikiem na tym samym komputerze");
        single.setFont( new Font("Dialog", Font.BOLD, 24));
        lan.setFont( new Font("Dialog", Font.BOLD, 24));
        multi.setFont( new Font("Dialog", Font.BOLD, 24));

        single.addActionListener(this);
        multi.addActionListener(this);


        button_menu.add(wybor);
        button_menu.add(single);
        button_menu.add(lan);
        button_menu.add(multi);


        back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer("sounds/click_sound.wav");
        menuButton.playOnce();

        Object source = e.getSource();

        if(source == back) {
            setVisible(false);
            previous_menu.setVisible(true);
        }
        else if(source == single) {
            setVisible(false);
            window.add(new MapMenu(window,this,menu_music));
            System.out.println("single");
        }
        else if(source == multi) {
            System.out.println("multi");
        }

    }

}
