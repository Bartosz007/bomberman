package Menu;

import Basic.ScreenFrame;
import Game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapMenu extends ScreenFrame implements ActionListener {

    public MapMenu(JFrame window, JPanel previous_menu) {
        super(window, previous_menu);
        int nr;
        JPanel icon_container = new JPanel();
        JScrollPane map_chose_scroll = new JScrollPane(icon_container);
        second_panel.add(map_chose_scroll);

      //  List<JButton> map_list = new ArrayList<>();
        JButton bt;
        for(int i=0;i<30;i++){
            nr = i+1;
            bt = new JButton("Mapa"+nr);
            bt.addActionListener(this);
            bt.setFont( new Font("Dialog", Font.BOLD, 24));
            icon_container.add(bt);
        }


        back.addActionListener(this); // obsługa przycisku cofania się
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == back){
            setVisible(false);
            previous_menu.setVisible(true);
        }else{
            String name = ((JButton)source).getText();
            setVisible(false);
            window.add(new GamePanel(window,this,name));
        }

    }
}
