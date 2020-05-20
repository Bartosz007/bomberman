package Basic;

import java.awt.*;

public class Field {
    private int x;
    private int y;
    private int type;
    private Dimension block_position;
    private String name;

    public Field(int x, int y, int type, String name) {
        this.type = type;
        this.name = name;
        this.block_position = new Dimension(x, y);
    }
    public void positioning(){
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

    @Override
    public String toString() {
        return "Field{" +
                "type=" + type +
                ", block_position=" + block_position +
                ", name='" + name + '\'' +
                '}';
    }
}
