package Menu;

import Basic.ScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instruction extends ScreenFrame implements ActionListener  {
        public Instruction(JFrame window, JPanel previous_menu) {
            super(window, previous_menu);
            JPanel label_menu = new JPanel();
            label_menu.setLayout(new BoxLayout(label_menu,BoxLayout.PAGE_AXIS));
            label_menu.setBackground(new Color(80,100,80));
            label_menu.setPreferredSize(new Dimension(900,200));
            label_menu.setMaximumSize(new Dimension(900,200));
            second_panel.add(label_menu);
            JTextArea tekst_area= new JTextArea(10,1);
            tekst_area.setFont( new Font("Dialog", Font.BOLD, 24));
            tekst_area.setText(" oLorem ipsum dolor sit amet, consect etur adipiscing elit.vrvbrbg" +
                    "tbg bbhbhcdbhic dbidbhcdbhbhdb idubidebubief oLorem ipsum dolor sit amet, consect etur adipiscing elit.vrvbrbgtbg bbhbhcdbhic dbidbhcdbhbhdb idubidebubiefoLorem ipsum dolor sit amet, consect etur adipiscing elit.vrvbrbgtbg bbhbhcdbhic dbidbhcdbhbhdb idubidebubief ");
            tekst_area.setWrapStyleWord(true);
            tekst_area.setLineWrap(true);
            label_menu.add(tekst_area);
            JLabel label1 = new JLabel("Instrukcja gry");
            JLabel opis_gry = new JLabel("  Lorem ipsum dolor sit amet, consect etur adipiscing elit.vrvbrbgtbg bbhbhcdbhic dbidbhcdbhbhdb idubidebubief  ");
            JLabel opis_gry2 = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            opis_gry.setHorizontalAlignment(JLabel.CENTER);
            opis_gry.setSize(100,100);
            opis_gry.setFont( new Font("Dialog", Font.BOLD, 24));
            opis_gry.setForeground(new Color(255,255,255));
            opis_gry.setPreferredSize(new Dimension(900,200));
            opis_gry.setMaximumSize(new Dimension(900,200));
            //label_menu.add(label1);
            //label_menu.add(opis_gry);
            //second_panel.add(opis_gry2);
            back.addActionListener(this);//to jest obsługa przycisku cofania
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
