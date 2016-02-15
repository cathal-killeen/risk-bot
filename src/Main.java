
import javax.swing.*;
import java.util.ArrayList;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    //array of all country objects
    public static ArrayList<Country> countries = Country.createCountries();
    public static ArrayList<Player> players;
    public static Boolean initialisationComplete = false;

    //all UI components
    public static JFrame gameFrame;


    public static void main(String[] args) {
        init();

        //TESTS

        //console log all countries
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).toString());
        }

    }

    public static void init(){
        players = Player.createPlayers();
        gameFrame = new GameFrame();
        initialisationComplete = true;
    }

}