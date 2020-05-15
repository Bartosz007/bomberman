package Objects;

import Basic.GameObject;

import java.awt.*;

public class Bomb extends GameObject {
    public String owner;
    public int TTL = 3000;//czas życia bomby(po którym wybucha)
    public Bomb(Dimension block_position, String name, String url, String owner) {
        super(block_position, name, url);
        this.owner = owner;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.image, this.x, this.y, null);
    }
}
