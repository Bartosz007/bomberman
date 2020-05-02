package Basic;

public class Field {
    private int x;
    private int y;
    private boolean destroable;
    private String image;
    private String id;

    private String name;
    public Field(int x, int y, boolean destroable, String image, String id, String name) {
        this.x = x;
        this.y = y;
        this.destroable = destroable;
        this.image = image;
        this.id = id;
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
                ", dostroable=" + destroable +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
