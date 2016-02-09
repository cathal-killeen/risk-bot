
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


        //tests
        Country c = new Country(5);
        System.out.print(c.toString());

    }



}