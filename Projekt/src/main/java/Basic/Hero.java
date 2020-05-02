package Basic;

public class Hero {
    private int x,y;//dokładna pozycja na planszy w pikselach
    private int field_x,field_y;//blok na którym postać się znajduje
    private int speed,bombs;
    private String name;
    private String color;

    public Hero(int field_x, int field_y, int speed, int bombs, String name,String color) {
        this.x = x;//to będzie wyliczane na pdstawie field_x i field_y
        this.y = y;
        this.name = name;
        this.speed = speed;
        this.bombs = bombs;
        this.color = color;
        this.field_x = field_x;
        this.field_y = field_y;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
