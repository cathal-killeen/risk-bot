
import javax.swing.*;
import java.awt.*;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    static JFrame gameFrame = new GameFrame();
    static JPanel mapPanel = new MapPanel();
    static JPanel sideBar = new SideBar();

    static Country[] countries = createCountries();


    public static void main(String[] args){
        initGame();

        //TESTS
        for(int i = 0; i < countries.length; i++){
            System.out.println(countries[i].toString());
        }

    }

    // initialise game - setup UI components, create default objects, assign countries to users
    public static void initGame(){
        gameFrame.add(mapPanel, BorderLayout.WEST);
        gameFrame.add(sideBar, BorderLayout.EAST);



        // done last when everything has been setup
        gameFrame.setVisible(true);
    }

    // create all country objects
    // NOTE: does not put country nodes on map, but Point2D coOrds can be accessed from each object
    public static Country[] createCountries(){
        Country[] countries = new Country[Constants.NUM_COUNTRIES];

        for(int i = 0; i < countries.length; i++){
            countries[i] = new Country(i);
        }

        return countries;
    }





}