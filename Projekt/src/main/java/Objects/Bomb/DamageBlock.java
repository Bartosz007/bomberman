package Objects.Bomb;

import Settings.PLAYER;

import java.awt.*;

public class DamageBlock {
    private Dimension blockPosition;
    private int x;
    private int y;
    private Image image;
    public DamageBlock( Dimension blockPosition, Image image) {
        this.blockPosition = blockPosition;
        this.x = blockPosition.width * PLAYER.ROZMIAR;
        this.y = blockPosition.height * PLAYER.ROZMIAR;
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
