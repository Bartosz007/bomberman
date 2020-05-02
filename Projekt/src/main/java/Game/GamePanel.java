package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private JFrame window;
    private JPanel previous_menu;
    private String name;
    private JButton back;

    public GamePanel(JFrame window, JPanel previous_menu, String name)  {
        this.window = window;
        this.previous_menu = previous_menu;
        this.name = name;

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen_size.width;
        int heigh = screen_size.height;

        JPanel main_menu = new JPanel();
        main_menu.setPreferredSize(new Dimension(width,heigh));
        main_menu.setMaximumSize(new Dimension(width,heigh));
        main_menu.setLayout(new BoxLayout(main_menu,BoxLayout.LINE_AXIS));
        add(main_menu);

        JPanel left_menu = new JPanel();
        left_menu.setPreferredSize(new Dimension(width/3,heigh));
        left_menu.setMaximumSize(new Dimension(width/3,heigh));
        left_menu.setBackground(new Color(120,120,120));
        main_menu.add(left_menu);

        JPanel game_frame = new JPanel();
        game_frame.setPreferredSize(new Dimension(5*width/6,heigh));
        game_frame.setMaximumSize(new Dimension(5*width/6,heigh));
        game_frame.setBackground(new Color(80,80,80));
        game_frame.setLayout(new BoxLayout(game_frame,BoxLayout.PAGE_AXIS));
        main_menu.add(game_frame);

        game_frame.add(new DrawFrame(screen_size));

        back = new JButton("Cofnij się");
        back.addActionListener(this);
        back.setFocusable(false);
        left_menu.add(back);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source ==back){
            setVisible(false);
            previous_menu.setVisible(true);
        }
    }


}
