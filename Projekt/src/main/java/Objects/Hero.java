package Objects;

import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.Bomb;
import Objects.Bomb.DamageArea;
import Objects.Bomb.DamageBlock;
import Settings.BLOCK_TYPE;
import Settings.PLAYER;

import java.awt.*;
import java.util.List;
import java.util.Date;
public class Hero extends GameObject{
    private int speed;
    private int bombs;
    private int bomb_power;
    private int vector_x = 0;//kierunek zwrotu postaci - w oparciu o to będą też animacje
 //zwroty zależne od x i y, operacje na 0,1,-1
    //poszukaj funkcji/klasy dziedziczącej po obrazkach do cięcia obrazków
    //utwórz mape plików/tablice, żeby wiedzieć które miejsca odpowiadają za ruch w poszczególnych kierunkach
    private int vector_y = 1;
    private int lives;
    private long cooldown;
    private boolean move_bomb;
    private boolean bomb_in_hand;
    private Bomb picked_bomb;
    private Field[][] board;
    private List<Bomb> bombList;
    private int score = 0;
    protected final int rowCount;
    protected int colCount;
    protected int WIDTH;
    protected int HEIGHT;

    public Hero(Dimension block_position, String name, String url) {
        super(block_position, name, url);

        this.width = PLAYER.ROZMIAR;
        this.heigh = PLAYER.WZROST_STD;
        this.cooldown = 0;

        this.x = block_position.width* PLAYER.ROZMIAR;
        this.y = block_position.height* PLAYER.ROZMIAR;

        this.rowCount=4;
        this.colCount= 3;

    }




    @Override //metody które się implementuje
    public void draw(Graphics2D g2d) {

        //tutaj rysujemy, można utorzyć zmienną inicjującą czas, w oparciu o którą będzie sie generować animacja
        //użyj wektor_x i wektor_y, możesz użyć z calculate

        //TODO - Ola - animacja tej postaci
        //używaj zmiennej vector do określania zwrotu pozycji
        //dodaj jakąś zmienną(np int), która cały czas będzie się zmieniać(np rosnąć o 1), w oparciu o tą zmienną robisz zmiany stanu animacji
        //gdy jest problem z wczytywaniem obrazków - uruchom IDEE jeszcze raz

        g2d.drawImage(this.image, this.x, this.y-6, null);

    }

    @Override
    public boolean checkState() {
        if(this.lives==0)
            return false;
        else
            return true;
    }

    @Override
    public boolean checkState(Hero obj) {
        return false;
    }

    public void setPlayer(Field[][] board, List<Bomb> bombList, int speed, int bombs,int bomb_power, int lives){
        this.speed = speed;
        this.bombs = bombs;
        this.bomb_power = bomb_power;
        this.lives = lives;
        this.board = board;
        this.bombList = bombList;
        this.move_bomb = false;
        this.bomb_in_hand = false;
    }


    public void calculate(boolean[] codes) {//system poruszania się i kolizjii

        if(codes[0]){ //góra
            vector_x = 0;
            vector_y = -1;
            collisions();
        }
        if(codes[1]){//lewo
            vector_x = -1;
            vector_y = 0;
            collisions();
        }
        if(codes[2]){//dół
            vector_x = 0;
            vector_y = 1;
            collisions();
             //   this.y += this.speed;
        }
        if(codes[3]){//prawo
            vector_x = 1;
            vector_y = 0;
            collisions();
        }
//licznik porównujący czas i zwrot
        this.setBlock_position(new Dimension((int)((this.x+this.width/2)/ PLAYER.ROZMIAR),(int)(this.y+this.heigh/2)/ PLAYER.ROZMIAR));

        if(this.bomb_in_hand){

            this.picked_bomb.setX(this.x);
            this.picked_bomb.setY(this.y);

            this.picked_bomb.setBlock_position(this.block_position);

        }
//tutaj robimy i się odwołujemy

    }

    public void collisions(){
        int x = this.getBlock_position().width;
        int y = this.getBlock_position().height;
        int im_speed = this.speed/3;//TODO - Bartosz - trzeba poprawić bo postacie przy większej prędkości dostają świra

        Field direct[]=new Field[3];
        if(vector_y == 0){//poruszam się lewo-prawo
            System.arraycopy(board[x + vector_x], y + -1, direct, 0, 3);
            if(vector_x == 1){ //ide w prawo
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || direct[1].getX() > (this.x + this.width) ) {
                    if (direct[2].getType() == BLOCK_TYPE.WALL && (this.y + this.heigh - 6) > direct[2].getY()) {
                        this.y -= im_speed;
                      //  return;
                    } else if (direct[0].getType() == BLOCK_TYPE.WALL  && (this.y +6) < direct[0].getY() + this.heigh) {
                        this.y += im_speed;
                      //  return;
                    }else{
                        if(!move_bomb){
                            for(Bomb b: bombList){
                                if(b.getBlock_position().equals(direct[1].getBlock_position()) && b.getX() < this.width+this.x){
                                    return;
                                }
                            }
                        }
                        this.x += this.speed;
                        if(Math.abs(this.x-direct[1].getBlock_position().width)<2){
                            this.x = direct[1].getBlock_position().width;
                        }
                    }
                }
            }else {//idę w lewo
                if( direct[1].getType() == BLOCK_TYPE.FLOOR || direct[1].getX()+this.width < this.x ) {//45 to stała bloku
                    if (direct[2].getType() != BLOCK_TYPE.FLOOR  && (this.y + this.heigh - 6) > direct[2].getY()) {
                        this.y -= im_speed;
                  //      return false;
                    } else if (direct[0].getType() != BLOCK_TYPE.FLOOR  && (this.y + 6) < direct[0].getY() + this.heigh) {
                        this.y += im_speed;
                     //   return false;
                    }else {
                        if(!move_bomb){
                            for(Bomb b: bombList){
                                //  System.out.println(b.getBlock_position()+ " vs "+this.getBlock_position() );
                                if(b.getBlock_position().equals(direct[1].getBlock_position()) && b.getX()+this.width > this.x){
                                    return;
                                }
                            }
                        }
                        this.x -= this.speed;
                        if(Math.abs(this.x-direct[1].getBlock_position().width)<2){
                            this.x = direct[1].getBlock_position().width;
                        }
                    }
                }
            }

        }

        else if(vector_x == 0){//prouszam się góra-dół
            for(int i=-1;i<2;i++){
                direct[i+1]=board[x+i][y+vector_y];
            }
            if(vector_y == 1){ //idę w dół
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || direct[1].getY() > (this.y + this.heigh)) { // jeśli jest przed nim pusty blok to sobie idzie i nie sprawdza kolizji
                    if (direct[2].getType() != BLOCK_TYPE.FLOOR  && (this.x + this.width) > direct[2].getX()) {
                        this.x -= im_speed;
                    } else if (direct[0].getType() != BLOCK_TYPE.FLOOR  && (this.x) < direct[0].getX() + this.width) {
                        this.x += im_speed;
                    } else {
                        if(!move_bomb){
                            for(Bomb b: bombList){
                                if(b.getBlock_position().equals(direct[1].getBlock_position()) && b.getY() + 6 < this.heigh + this.y){
                                    return;
                                }
                            }
                        }
                        this.y += this.speed;
                        if(Math.abs(this.y-direct[1].getBlock_position().height)<2){
                            this.y = direct[1].getBlock_position().height;
                        }
                    }
                }
            }else {//idę w górę
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || (direct[1].getY() + this.heigh - 6) < this.y  ) {//45 to stała bloku
                    if(direct[2].getType() != BLOCK_TYPE.FLOOR && (this.x+this.width) > direct[2].getX() ){
                        this.x -= im_speed;
                    }
                    else if(direct[0].getType() != BLOCK_TYPE.FLOOR  && (this.x) < direct[0].getX() + this.width ) {
                        this.x += im_speed;
                    }else {
                        if(!move_bomb){
                            for(Bomb b: bombList){
                                if(b.getBlock_position().equals(direct[1].getBlock_position()) && b.getY() + this.heigh > this.y + 6){
                                    return;
                                }
                            }
                        }
                        this.y -= this.speed;
                        if(Math.abs(this.y-direct[1].getBlock_position().height)<2){
                            this.y = direct[1].getBlock_position().height;
                        }
                    }
                }

            }

        }

    }

    public void checkDamage(List<DamageArea> damageAreas){
        for(DamageArea dm: damageAreas){
            for(DamageBlock db: dm.getList()){
                if(cooldown < new Date().getTime() &&
                        this.block_position.width == db.getBlockPosition().width &&
                        this.block_position.height == db.getBlockPosition().height){
                    this.lives--;
                    System.out.println(this.name);
                    Hero dmown = dm.getOwner();
                    if(this!= dmown)
                        dmown.setScore(dmown.getScore()+PLAYER.ZABOJSTWO);
                    else
                        dmown.setScore(dmown.getScore()-PLAYER.ZABOJSTWO);

                    //po trafieniu bombą w gracza, staje się on tymczasowo nieśmiertelny
                    cooldown = new Date().getTime()+PLAYER.COOLDOWN;
                    System.out.println("jeb" + this.name);
                }
            }
        }
    }

    /*protected Bitmap createSubImageAt(int row, int col)  {
    // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width,height);
        return subImage;
    }*/

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }


    public int getBomb_power() {
        return bomb_power;
    }

    public void setBomb_power(int bomb_power) {
        this.bomb_power = bomb_power;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public Bomb getPicked_bomb() {
        return picked_bomb;
    }

    public void setPicked_bomb(Bomb picked_bomb) {
        this.picked_bomb = picked_bomb;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setMove_bomb(boolean move_bomb) {
        this.move_bomb = move_bomb;
    }

    public boolean isMove_bomb() {
        return move_bomb;
    }


    public void setBomb_in_hand(boolean bomb_in_hand) {
        this.bomb_in_hand = bomb_in_hand;
    }

    public boolean isBomb_in_hand() {
        return bomb_in_hand;
    }


    public int getVector_x() {
        return vector_x;
    }

    public int getVector_y() {
        return vector_y;
    }

}
