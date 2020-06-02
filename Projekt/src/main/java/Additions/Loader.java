package Additions;

import Basic.Field;
import Game.PlayField;
import Settings.Data;
import com.google.gson.Gson;

public class Loader {
    private Gson g = new Gson();//wczytanie stringa planszy i zrobienie z niego obiektu
    private PlayField board;
    public Loader() {
        board  =  new PlayField(g.fromJson(Data.fields_data, Field[][].class));
        board.reposition();//trzeba jeszcze przypisać dokładne współrzędne a to wyżej nie ładuje konstruktora
    }

    public PlayField getBoard() {
        return board;
    }
}
