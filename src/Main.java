
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
    //public static ArrayList<Player> players = new ArrayList<>();
    public static Boolean initialisationComplete = false;

    //all UI components
    public static GameFrame GameFrame = new GameFrame();


    public static void main(String[] args) {
        init();

        System.out.println(GameFrame.SideBar.getCommand());

        //TESTS

        //console log all countries
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).toString());
        }

    }

    public static void init(){
        Player.createPlayers();
        //GameFrame.Map.PlayerNamesBar.putPlayerNames();

        initialisationComplete = true;
    }

}