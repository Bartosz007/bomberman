package Basic;

import java.awt.*;

public class Field {
    private int x;
    private int y;
    private int type;
    private Dimension block_position;
    private String name;

    public Field(int x,int y) {
        this.block_position = new Dimension(x, y);
        this.x = x*45;
        this.y = y*45;
    }

    public void positioning(){
        this.block_position = new Dimension(x, y);
        this.x = x*45;
        this.y = y*45;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Dimension getBlock_position() {
        return block_position;
    }

    @Override
    public String toString() {
        return "Field{" +
                "type=" + type +
                ", block_position=" + block_position +
                ", name='" + name + '\'' +
                '}';
    }
}
