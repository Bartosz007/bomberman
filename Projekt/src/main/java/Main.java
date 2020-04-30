import java.awt.*;

public class Main {

    public static void main(String[] args){
        System.out.println("Start");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartGame();
            }
        });

    }

}
