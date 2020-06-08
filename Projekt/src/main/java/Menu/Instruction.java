package Menu;

import Additions.SoundPlayer;
import Basic.ScreenFrame;
import Settings.GAMESETTINGS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instruction extends ScreenFrame implements ActionListener  {
        public Instruction(JFrame window, JPanel previous_menu, SoundPlayer menu_music) {
            super(window, previous_menu, menu_music);
            JPanel label_menu = new JPanel();
            label_menu.setLayout(new BoxLayout(label_menu,BoxLayout.PAGE_AXIS));
            label_menu.setBackground(GAMESETTINGS.SZARY_CIEMNY);
            label_menu.setPreferredSize(new Dimension(GAMESETTINGS.WIDTH,300));
            label_menu.setMaximumSize(new Dimension(GAMESETTINGS.WIDTH,300));
            second_panel.add(label_menu);
            JTextArea tekst_area= new JTextArea(30,1);
            tekst_area.setFont( new Font("Dialog", Font.BOLD, 20));
            tekst_area.setBackground(GAMESETTINGS.SZARY_CIEMNY);
            tekst_area.setForeground(GAMESETTINGS.BIALY);
            tekst_area.setText("     Instrukcja gry");

            JTextArea tekst_area2= new JTextArea(30,1);
            tekst_area2.setFont( new Font("Dialog", Font.BOLD, 16));
            tekst_area2.setBackground(GAMESETTINGS.SZARY_CIEMNY);
            tekst_area2.setForeground(GAMESETTINGS.BIALY);
            tekst_area2.setText("\n       Gra bomberman to gra, której celem jest wysadzenie przeciwników, zanim oni zdążą wysadzić ciebie.\n" +
                    "       Podkładaj bomby, wysadzaj skrzynie i zbieraj dodatki.\n\n"+
                    "       Rodzaje dodatków:\n "+
                    "       -błyskawica- zwiększa siłę wybuchu\n" +
                    "       -buty- zwiększa szybkość postaci\n" +
                    "       -dynamit- daje możliwość podłożenia wiekszej ilości bomb\n" +
                    "       -dynamit z stopą- daje możliwość przekopania dynamitu na kolejne wolne pole\n\n" +
                    "       Poruszanie się postaciami: \n" +
                    "       Osoba 1: w-przód, a-lewo, d-prawo, s-tył, spacja-podłóż bombę\n" +
                    "       Osoba2: strzałka w górę-przód, w lewo-lewo, w prawo-prawo, w dół-tył, enter-podłóż bombę\n");
            tekst_area.setWrapStyleWord(true);
            tekst_area.setLineWrap(true);
            tekst_area.setEditable(false);
            tekst_area2.setWrapStyleWord(true);
            tekst_area2.setLineWrap(true);
            tekst_area2.setEditable(false);
            label_menu.add(tekst_area);
            label_menu.add(tekst_area2);
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
