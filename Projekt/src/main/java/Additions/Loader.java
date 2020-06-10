package Additions;

import Basic.Field;
import Game.PlayField;
import com.google.gson.Gson;

import java.util.Scanner;

public class Loader {
    private Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
    private PlayField board;
    private String mapa;

    public Loader(String nazwa_mapa) {

        this.mapa = new String();

       try {

            Scanner odczyt = new Scanner(getClass().getResourceAsStream("/mapy/"+nazwa_mapa+".txt")); //wczytujesz plik do zmiennej odczyt

            while (odczyt.hasNextLine()){
                mapa = mapa + odczyt.nextLine();
            }

            odczyt.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        board  =  new PlayField(g.fromJson(this.mapa, Field[][].class));
        board.reposition();//trzeba jeszcze przypisać dokładne współrzędne, bo to wyżej nie ładuje konstruktora

    }

    public PlayField getBoard() {
        return board;
    }
}
