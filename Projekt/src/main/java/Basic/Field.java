package Basic;

public class Field {
    private int x;
    private int y;
    private boolean dostroable;
    private String image;
    private String id;

    public Field(int x, int y, boolean dostroable, String image, String id) {
        this.x = x;
        this.y = y;
        this.dostroable = dostroable;
        this.image = image;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDostroable() {
        return dostroable;
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
                ", dostroable=" + dostroable +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
