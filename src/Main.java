
import javax.swing.*;
import java.awt.*;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    //contains all UI components
    static JFrame gameFrame = new GameFrame();

    //array of all country objects
    static Country[] countries = createCountries();

    public static void main(String[] args){



        //TESTS

        //console log all countries
        for(int i = 0; i < countries.length; i++){
            System.out.println(countries[i].toString());
        }

    }

    // create all country objects -- possibly relocate to another file
    // NOTE: does not put country nodes on map, but Point2D coOrds can be accessed from each object
    public static Country[] createCountries(){
        Country[] countries = new Country[Constants.NUM_COUNTRIES];

        for(int i = 0; i < countries.length; i++){
            countries[i] = new Country(i);
        }

        return countries;
    }

}