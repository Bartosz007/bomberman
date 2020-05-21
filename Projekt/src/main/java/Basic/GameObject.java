package Basic;

import Objects.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public abstract class GameObject implements State {
    protected int x,y;
    protected int width,heigh;
    protected Dimension block_position;
    protected String name;
    protected String url;
    protected Image image;

    public GameObject(Dimension block_position, String name, String url) {
        this.block_position = block_position;
        this.name = name;
        this.url = url;

        this.x = block_position.width*45;
        this.y = block_position.height*45;
        try {
            this.image = ImageIO.read(getClass().getResource(this.url));
        } catch (IOException e) {
            System.out.println("Nie wczytało pliku "+ this.url);
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dimension getBlock_position() {
        return block_position;
    }

    public void setBlock_position(Dimension block_position) {
        this.block_position = block_position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*
    wzorzec projektowy: Template Method(68 strona)
    jest to to metoda, która jest tylko wzorcem(template), jest nadpisywana przez podklasy takie jak Hero, Bomb, czy z klasy Pakietu PowerUp
     */
    public abstract void draw(Graphics2D g2d);


}
