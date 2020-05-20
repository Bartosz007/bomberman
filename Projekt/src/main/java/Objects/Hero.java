package Objects;

import Basic.Field;
import Basic.GameObject;
import Objects.Bomb.DamageArea;
import Objects.Bomb.DamageBlock;
import Settings.BLOCK_TYPE;
import Settings.KEY;

import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Date;
public class Hero extends GameObject{
    private int speed;
    private int bombs;
    private int bomb_power;
    private int vector_x = 0;//kierunek zwrotu postaci - w oparciu o to będą też animacje
    private int vector_y = 1;
    private int lives;
    private long cooldown;
    public Hero(Dimension block_position, String name, String url, int speed, int bombs,int bomb_power, int lives) {
        super(block_position, name, url);
        this.speed = speed;
        this.bombs = bombs;
        this.bomb_power = bomb_power;
        this.lives = lives;

        this.width = 45;
        this.heigh = 51;
        this.cooldown = 0;

        this.x = block_position.width*45;
        this.y = block_position.height*45;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //TODO - Ola - animacja tej postaci
        //używaj zmiennej vector do określania zwrotu pozycji
        //dodaj jakąś zmienną(np int), która cały czas będzie się zmieniać(np rosnąć o 1), w oparciu o tą zmienną robisz zmiany stanu animacji
        //gdy jest problem z wczytywaniem obrazków - uruchom IDEE jeszcze raz
        g2d.drawImage(this.image, this.x, this.y-6, null);

    }

    public void calculate(boolean[] codes, Field[][] board) {//system poruszania się i kolizjii

        if(codes[0]){ //góra
            vector_x = 0;
            vector_y = -1;
            colisions(board);
        }
        if(codes[1]){//lewo
            vector_x = -1;
            vector_y = 0;
            colisions(board);
        }
        if(codes[2]){//dół
            vector_x = 0;
            vector_y = 1;
            colisions(board);
             //   this.y += this.speed;
        }
        if(codes[3]){//prawo
            vector_x = 1;
            vector_y = 0;
            colisions(board);
        }

         this.setBlock_position(new Dimension((int)((this.x+this.width/2)/45),(int)(this.y+this.heigh/2)/45));

    }

    public void colisions( Field[][] board){
        int x = this.getBlock_position().width;
        int y = this.getBlock_position().height;
        int im_speed = this.speed/3;
      //  int im_speed = this.speed;
        Field direct[]=new Field[3];
        if(vector_y == 0){//poruszam się lewo-prawo
            for(int i=-1;i<2;i++){
                direct[i+1]=board[x+vector_x][y+i];
            }

            if(vector_x == 1){ //ide w prawo
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || direct[1].getX() > (this.x + this.width) ) {
                    if (direct[2].getType() == BLOCK_TYPE.WALL && (this.y + this.heigh - 6) > direct[2].getY()) {
                        this.y -= im_speed;
                      //  return;
                    } else if (direct[0].getType() == BLOCK_TYPE.WALL  && (this.y +6) < direct[0].getY() + this.heigh) {
                        this.y += im_speed;
                      //  return;
                    }else {
                        this.x += this.speed;
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
                        this.x -= this.speed;
                    }
                }
            }

        }

        if(vector_x == 0){//prouszam się góra-dół
            for(int i=-1;i<2;i++){
                direct[i+1]=board[x+i][y+vector_y];
            }
            if(vector_y == 1){ //idę w dół
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || direct[1].getY() > (this.y + this.heigh)) { // jeśli jest przed nim pusty blok to sobie idzie i nie sprawdza kolizji
                    if (direct[2].getType() != BLOCK_TYPE.FLOOR  && (this.x + this.width) > direct[2].getX()) {
                        this.x -= im_speed;
                    } else if (direct[0].getType() != BLOCK_TYPE.FLOOR  && (this.x) < direct[0].getX() + this.width) {
                        this.x += im_speed;
                        //    return false;
                    } else {
                        this.y += this.speed;
                    }
                }
            }else {//idę w górę
                if(direct[1].getType() == BLOCK_TYPE.FLOOR || (direct[1].getY() + this.heigh - 6) < this.y  ) {//45 to stała bloku
                    if(direct[2].getType() != BLOCK_TYPE.FLOOR && (this.x+this.width) > direct[2].getX() ){
                        this.x -= im_speed;
                      //  return false;
                    }
                    else if(direct[0].getType() != BLOCK_TYPE.FLOOR  && (this.x) < direct[0].getX() + this.width ) {
                        this.x += im_speed;
                      //  return false;
                    }else {
                        this.y -= this.speed;
                    }
                }

            }

        }
     //   System.out.println("player: "+this.x+" x "+this.y);
    }

    public void checkDamage(List<DamageArea> damageAreas){
        for(DamageArea dm: damageAreas){
            for(DamageBlock db: dm.getList()){
                if(cooldown < new Date().getTime() &&
                        this.block_position.width == db.getBlockPosition().width &&
                        this.block_position.height == db.getBlockPosition().height){
                    this.lives--;
                    //po trafieniu bombą w gracza, staje się on tymczasowo nieśmiertelny
                    cooldown = new Date().getTime()+3000;//TODO Ola - prosze do pliku SETTINGS dopisać zmienną od cooldownu(to 3000)
                    System.out.println("jeb" + this.name);
                }
            }
        }
    }

    public int getBomb_power() {
        return bomb_power;
    }

    public void setBomb_power(int bomb_power) {
        this.bomb_power = bomb_power;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

}
