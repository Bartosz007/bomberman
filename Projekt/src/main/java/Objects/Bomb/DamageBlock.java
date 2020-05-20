package Objects.Bomb;

import com.sun.javafx.scene.traversal.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class DamageBlock {
    //ta klasa mogłaby dziedziczyć po GameObject, ale nie robi tego z kilku powodów
    //-optymalizacja ładowania obrazków
    //-opytmalizacja rysowania tych obrazków
    private Dimension blockPosition;
    private int x;
    private int y;
    private Image image;
    public DamageBlock( Dimension blockPosition, Image image) {
        this.blockPosition = blockPosition;
        this.x = blockPosition.width * 45;
        this.y = blockPosition.height * 45;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public Dimension getBlockPosition() {
        return blockPosition;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
