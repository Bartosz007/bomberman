package Basic;

import java.awt.*;

public class Field {
    private int x;
    private int y;

    private boolean destroable;
    private String image;
    private String id;
    private Dimension block_position;
    private String name;
    public Field(int x, int y, boolean destroable, String image, String id, String name) {

        this.destroable = destroable;
        this.image = image;
        this.id = id;
        this.block_position = new Dimension(x,y);
    }
    public void reposition(){
        this.x = x*45;
        this.y = y*45;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDestroable() {
        return destroable;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Field{" +
                "x=" + x +
                ", y=" + y +
                ", destroable=" + destroable +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
