import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Cathal on 07/02/16.
 */

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

    public ArrayList<Country> getOwnedTerritories(){
        ArrayList<Country> ownedTerritories = new ArrayList<>();

        for(Country country: Main.countries){
            if(country.getOwner() == this){
                ownedTerritories.add(country);
            }
        }

        return ownedTerritories;
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
                Main.GameFrame.Map.repaint();
                availableCountries = players.get(i).initialTerritories(availableCountries, 9);
            }else{
                players.add(new Player(playerNames[i], i));
                Main.GameFrame.Map.repaint();
                availableCountries = players.get(i).initialTerritories(availableCountries, 6);
            }
        }

        return players;
    }

    public static String[] getPlayerNames(){
        String[] playerNames = {
                "",
                "",
                "Player 1",
                "Player 2",
                "Player 3",
                "Player 4"
        };
        Main.GameFrame.SideBar.log("Enter name for player 1: ", Main.GameFrame.SideBar.prompt);
        while(playerNames[0].length() == 0){
            playerNames[0] = Main.GameFrame.SideBar.getCommand();
        }
        Main.GameFrame.SideBar.log(playerNames[0], Main.GameFrame.SideBar.userInput);

        Main.GameFrame.SideBar.log("Enter name for player 2: ", Main.GameFrame.SideBar.prompt);
        while(playerNames[1].length() == 0){
            playerNames[1] = Main.GameFrame.SideBar.getCommand();
        }
        Main.GameFrame.SideBar.log(playerNames[1], Main.GameFrame.SideBar.userInput);
        
        return playerNames;
    }


}
