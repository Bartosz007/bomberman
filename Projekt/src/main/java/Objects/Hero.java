package Objects;

import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.Bomb;
import Objects.Bomb.DamageArea;
import Objects.Bomb.DamageBlock;
import Settings.BLOCK_TYPE;
import Settings.PLAYER;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Date;
public class Hero extends GameObject{
    private int speed;
    private int bombs;
    private int bomb_power;
    private int lives;

    private int vector_x = 0;
    private int vector_y = 1;

    private int cooldown; //TODO czas nieśpmiertetlności po wybuchu
    private boolean move_bomb;
    private boolean bomb_in_hand;
    private Bomb picked_bomb;

    private Field[][] board;
    private List<Bomb> bombList;
    private int score;

    private int[] petla_animacji;
    private int aktualny_moment;
    private boolean[] nacisniety;
    private int wiersz, kolumna;
    private int zmiana_przezroczystosci;
    private float przezroczystosc;
    public Hero(Dimension block_position, String name, String url) {
        super(block_position, name, url);

        this.width = PLAYER.ROZMIAR;
        this.heigh = PLAYER.WZROST_STD;
        this.cooldown = 0;

        this.x = block_position.width* PLAYER.ROZMIAR;
        this.y = block_position.height* PLAYER.ROZMIAR;

        this.score = 0;
        this.wiersz = 0;
        this.kolumna = 0;

        this.petla_animacji = new int[]{0, 0, 0, 0};
        this.nacisniety = new boolean[]{false, false, false, false};
        this.przezroczystosc = 1f;
    }

    @Override //metody które się implementuje
    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, przezroczystosc));


        g2d.drawImage(wycinanie(wiersz, kolumna), this.x, this.y-6, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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

            if(!this.nacisniety[0]){
                wiersz = 3;
                aktualny_moment = petla_animacji[0];
                this.nacisniety[0] = true;
            }

            if(aktualny_moment +10 == petla_animacji[0]){
                kolumna = 1;
            }
            else if(aktualny_moment +20 == petla_animacji[0]){
                kolumna = 2;
                aktualny_moment = petla_animacji[0];
            }

            petla_animacji[0]++;
        }
        else{
            if(this.nacisniety[0]) {
                this.nacisniety[0] = false;
                kolumna = 0;
            }
        }

        if(codes[1]){//lewo
            vector_x = -1;
            vector_y = 0;
            collisions();

            if(!this.nacisniety[1]){
                wiersz = 1;
                aktualny_moment = petla_animacji[1];
                this.nacisniety[1] = true;
            }

            if(aktualny_moment +10 == petla_animacji[1]){
                kolumna = 1;
            }
            else if(aktualny_moment +20 == petla_animacji[1]){
                kolumna = 2;
                aktualny_moment = petla_animacji[1];
            }
            petla_animacji[1]++;
        }
        else{
            if(this.nacisniety[1]) {
                this.nacisniety[1] = false;
                kolumna = 0;
            }
        }

        if(codes[2]){//dół
            vector_x = 0;
            vector_y = 1;
            collisions();

            if(!this.nacisniety[2]){
                wiersz = 0;
                aktualny_moment = petla_animacji[2];
                this.nacisniety[2] = true;
            }

            if(aktualny_moment +10 == petla_animacji[2]){
                kolumna = 1;
            }
            else if(aktualny_moment +20 == petla_animacji[2]){
                kolumna = 2;
                aktualny_moment = petla_animacji[2];
            }

            petla_animacji[2]++;
        }
        else{
            if(this.nacisniety[2]) {
                this.nacisniety[2] = false;
                kolumna = 0;
            }
        }

        if(codes[3]){//prawo
            vector_x = 1;
            vector_y = 0;
            collisions();

            if(!this.nacisniety[3]){
                wiersz = 2;
                aktualny_moment = petla_animacji[3];
                this.nacisniety[3] = true;
            }

            if(aktualny_moment +10 == petla_animacji[3]){
                kolumna = 1;
            }
            else if(aktualny_moment +20 == petla_animacji[3]){
                kolumna = 2;
                aktualny_moment = petla_animacji[3];
            }

            petla_animacji[3]++;
        }
        else{
            if(this.nacisniety[3]) {
                this.nacisniety[3] = false;
                kolumna = 0;
            }
        }

        this.setBlock_position(new Dimension((int)((this.x+this.width/2)/ PLAYER.ROZMIAR),(int)(this.y+this.heigh/2)/ PLAYER.ROZMIAR));

        if(this.bomb_in_hand){

            this.picked_bomb.setX(this.x);
            this.picked_bomb.setY(this.y);

            this.picked_bomb.setBlock_position(this.block_position);

        }


        if(cooldown>0){
            if(this.cooldown==this.zmiana_przezroczystosci){
                if(this.przezroczystosc == 1f)
                    this.przezroczystosc = 0.5f;
                else
                    this.przezroczystosc = 1f;

                this.zmiana_przezroczystosci = this.cooldown - 10;
            }
            cooldown--;
        }else{
            this.przezroczystosc = 1f;
        }
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
                if(this.cooldown == 0 &&
                        this.block_position.width == db.getBlockPosition().width &&
                        this.block_position.height == db.getBlockPosition().height){
                    this.lives--;

                    Hero dmown = dm.getOwner();
                    if(this!= dmown)
                        dmown.setScore(dmown.getScore()+PLAYER.ZABOJSTWO);
                    else
                        dmown.setScore(dmown.getScore()-PLAYER.ZABOJSTWO);

                    //po trafieniu bombą w gracza, staje się on tymczasowo nieśmiertelny
                    this.cooldown = PLAYER.COOLDOWN;

                    this.zmiana_przezroczystosci = this.cooldown - 10;


                }
            }
        }
    }

    private BufferedImage wycinanie(int wiersz, int kolumna){
        return this.image.getSubimage(kolumna*this.width,wiersz*this.heigh,this.width,this.heigh);
    }


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
