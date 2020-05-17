package Objects;

import Basic.Field;
import Basic.GameObject;
import Settings.KEY;

import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero extends GameObject{
    private int speed;
    private int bombs;
    private int bomb_power;
    private int up, left,  down,  right,  plant_bomb;
    private int vector_x = 0;//kierunek zwrotu postaci - w oparciu o to będą też animacje
    private int vector_y = 1; //zwroty zależne od x i y, operacje na 0,1,-1
    //poszukaj funkcji/klasy dziedziczącej po obrazkach do cięcia obrazków
    //utwórz mape plików/tablice, żeby wiedzieć które miejsca odpowiadają za ruch w poszczególnych kierunkach
    public Hero(Dimension block_position, String name, String url, int speed, int bombs,int bomb_power) {
        super(block_position, name, url);
        this.speed = speed;
        this.bombs = bombs;
        this.bomb_power = bomb_power;

        this.width = 45;
        this.heigh = 51;

        this.x = block_position.width*45;
        this.y = block_position.height*45;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //tutaj rysujemy, można utorzyć zmienną inicjującą czas, w oparciu o którą będzie sie generować animacja
        //użyj wektor_x i wektor_y, możesz użyć z calculate
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
                if(direct[1].getName().equals("empty") || direct[1].getX() > (this.x + this.width) ) {
                    if (direct[2].getName().equals("wall") && (this.y + this.heigh - 6) > direct[2].getY()) {
                        this.y -= im_speed;
                      //  return;
                    } else if (direct[0].getName().equals("wall") && (this.y +6) < direct[0].getY() + this.heigh) {
                        this.y += im_speed;
                      //  return;
                    }else {
                        this.x += this.speed;
                    }
                }
            }else {//idę w lewo
                if( direct[1].getName().equals("empty") || direct[1].getX()+this.width < this.x ) {//45 to stała bloku
                    if (direct[2].getName().equals("wall") && (this.y + this.heigh - 6) > direct[2].getY()) {
                        this.y -= im_speed;
                  //      return false;
                    } else if (direct[0].getName().equals("wall") && (this.y + 6) < direct[0].getY() + this.heigh) {
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
                if(direct[1].getName().equals("empty") || direct[1].getY() > (this.y + this.heigh)) { // jeśli jest przed nim pusty blok to sobie idzie i nie sprawdza kolizji
                    if (direct[2].getName().equals("wall") && (this.x + this.width) > direct[2].getX()) {
                        this.x -= im_speed;
                    } else if (direct[0].getName().equals("wall") && (this.x) < direct[0].getX() + this.width) {
                        this.x += im_speed;
                        //    return false;
                    } else {
                        this.y += this.speed;
                    }
                }
            }else {//idę w górę
                if(direct[1].getName().equals("empty") || (direct[1].getY() + this.heigh - 6) < this.y  ) {//45 to stała bloku
                    if(direct[2].getName().equals("wall") && (this.x+this.width) > direct[2].getX() ){
                        this.x -= im_speed;
                      //  return false;
                    }
                    else if(direct[0].getName().equals("wall") && (this.x) < direct[0].getX() + this.width ) {
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
}
