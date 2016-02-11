
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
        players = createPlayers();
        gameFrame = new GameFrame();
    }




    public static ArrayList createPlayers(){
        ArrayList<Integer> availableCountries = new ArrayList<>();
        for(int i=0; i < countries.size(); i++){
            availableCountries.add(i);
        }
        ArrayList<Player> players = new ArrayList<>();

        String[] playerNames = getPlayerNames();



        for(int i = 0; i < 6; i++){
            if(i >= 0 && i <= 1){
                players.add(new Player(playerNames[i], i));
                availableCountries = players.get(i).initialTerritories(availableCountries, 9);
            }else{
                players.add(new Player(playerNames[i], i));
                availableCountries = players.get(i).initialTerritories(availableCountries, 6);
            }
        }

        return players;
    }

    public static String[] getPlayerNames(){
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();

        String[] playerNames = {
                "Player 6",
                "Player 5",
                "Player 4",
                "Player 3",
                "Player 2",
                "Player 1"
        };

        Object[] message = {
                "Player 1:", field1,
                "Player 2:", field2,

        };
        int option = JOptionPane.showConfirmDialog(null, message, "Player names", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION){
            playerNames[0] = field1.getText();
            playerNames[1] = field2.getText();
        }

        return playerNames;
    }

}