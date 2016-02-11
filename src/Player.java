import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Cathal on 07/02/16.
 */

//ABSTRACT ClASS - players will be constructed differently depending on if they are human or neutral
public class Player {
    public int index;
    public String name;
    public Color color;

    public void allocateTerritory(Country country) {
        country.setOwner(this);
    }

    public Player(String name, int index){
        this.index = index;
        this.name = name;
        color = Constants.PLAYER_COLORS[index];
    }

    public ArrayList initialTerritories(ArrayList<Integer> availableCountries, int numCountries){
        int countryIndex;
        for(int i = 0; i < numCountries; i++){
            countryIndex = availableCountries.remove(0);
            Main.countries.get(countryIndex).setOwner(this);
        }

        return availableCountries;
    }


    public static ArrayList createPlayers(){
        ArrayList<Integer> availableCountries = new ArrayList<>();
        for(int i=0; i < Main.countries.size(); i++){
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
                "Player 1",
                "Player 2",
                "Player 3",
                "Player 4"
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


//NOTE if(playerObj instanceOf NeutralPlayer){...}
//OR if(playerObj instanceOf HumanPlayer{...}
//can be used to distinguish between human and neutral players