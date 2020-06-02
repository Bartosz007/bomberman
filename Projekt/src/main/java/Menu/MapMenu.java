package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;
import Game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapMenu extends ScreenFrame implements ActionListener {

    public MapMenu(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
        super(window, previous_menu, menu_music);
        setFocusable(true);
        int nr;

        JPanel icon_container = new JPanel();
        JScrollPane map_chose_scroll = new JScrollPane(icon_container);
        second_panel.add(map_chose_scroll);

        Icon icon = new ImageIcon("C:\\Users\\Aleksandra\\Desktop\\mapa1.PNG");
        JButton button7 = new JButton(icon);
        //icon_container.add(button7);
      //  List<JButton> map_list = new ArrayList<>();
        JButton bt;
        for(int i=0;i<4;i++)
        {
            nr = i+1;
            if (0==i)
            {
                bt = new JButton(icon);
            }
            else
                bt = new JButton("Mapa"+nr);
            bt.addActionListener(this);
            bt.setFont( new Font("Dialog", Font.BOLD, 24));
            bt.setFocusable(false);
            icon_container.add(bt);
        }


        back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer menuButton = new SoundPlayer("sounds/click_sound.wav");
        menuButton.playOnce();

        Object source = e.getSource();

        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }else{
            String name = ((JButton)source).getText();
            setVisible(false);
            window.add(new GamePanel(window,this,name));
            menu_music.stop();
        }

    }
}
