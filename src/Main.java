
import javax.swing.*;
import java.awt.*;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    public static void main(String[] args){
        JFrame gameFrame = new GameFrame();
        JPanel mapPanel = new MapPanel();
        JPanel sideBar = new SideBar();

        gameFrame.add(mapPanel, BorderLayout.WEST);
        gameFrame.add(sideBar, BorderLayout.EAST);

        Country[] countries = createCountries();

        gameFrame.setVisible(true);

        //tests
        for(int i = 0; i < countries.length; i++){
            System.out.println(countries[i].toString());
        }

    }

    public static Country[] createCountries(){
        Country[] countries = new Country[Constants.NUM_COUNTRIES];

        for(int i = 0; i < countries.length; i++){
            countries[i] = new Country(i);
        }

        return countries;
    }





}