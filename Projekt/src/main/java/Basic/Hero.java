package Basic;

public class Hero {
    private int x;
    private int y;
    private String name;
    private int speed;
    private int bombs;

    public Hero(int x, int y, String name, int speed, int bombs) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.speed = speed;
        this.bombs = bombs;
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
    
}
