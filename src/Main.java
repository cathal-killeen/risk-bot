
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    //array of all country objects
    public static ArrayList<Country> countries = createCountries();

    //contains all UI components
    public static JFrame gameFrame = new GameFrame();



    public static void main(String[] args){



        //TESTS

        //console log all countries
        for(int i = 0; i < countries.size(); i++){
            System.out.println(countries.get(i).toString());
        }

    }

    // create all country objects -- possibly relocate to another file
    // NOTE: does not put country nodes on map, but Point2D coOrds can be accessed from each object
    public static ArrayList createCountries(){
        ArrayList<Country> countries = new ArrayList<Country>();

        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            countries.add(new Country(i));
        }

        return countries;
    }

}