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
    public Boolean isNeutral;

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
            Country.countries.get(countryIndex).setOwner(this);
        }

        return availableCountries;
    }

    public ArrayList<Country> getOwnedTerritories(){
        ArrayList<Country> ownedTerritories = new ArrayList<>();

        for(Country country: Country.countries){
            if(country.getOwner() == this){
                ownedTerritories.add(country);
            }
        }

        return ownedTerritories;
    }


    //
    // STATIC CONTENT
    //

    public static ArrayList<Player> players = new ArrayList<>();

    public static void createPlayers(){
        ArrayList<Integer> availableCountries = new ArrayList<>();
        for(int i=0; i < Country.countries.size(); i++){
            availableCountries.add(i);
        }
        //ArrayList<Player> players = new ArrayList<>();

        String[] neutralPlayerNames = {
                "Player 1",
                "Player 2",
                "Player 3",
                "Player 4"
        };

        for(int i = 0; i < 6; i++){
            if(i >= 0 && i <= 1){
                // human players
                players.add(new Player(getPlayerName(i+1), i));
                availableCountries = players.get(i).initialTerritories(availableCountries, 9);
                Main.GameFrame.SideBar.log("Creating player " + players.get(i).name + "\n", Main.GameFrame.SideBar.info);
            }else{
                // neutral players
                players.add(new Player(neutralPlayerNames[i-2], i));
                availableCountries = players.get(i).initialTerritories(availableCountries, 6);
                Main.GameFrame.SideBar.log("Creating neutral player " + players.get(i).name + "\n", Main.GameFrame.SideBar.info);
            }
            String territories = "";
            for(Country country: players.get(i).getOwnedTerritories()){
                territories += "> " +country.getName() + "\n";
            }
            Main.GameFrame.SideBar.log("Initialising territories for "
                                        + players.get(i).name + "\n"
                                        + territories, Main.GameFrame.SideBar.info);
            Main.GameFrame.Map.repaint();
            Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();

            //timeout not necessary, just looks cool!
            try {
                Thread.sleep(750);
            } catch(InterruptedException ex) {

            }
        }

    }

    public static String getPlayerName(int playerNum){
        String name = "";
        Main.GameFrame.SideBar.log("Enter name for player " + playerNum + ":", Main.GameFrame.SideBar.prompt);
        while(name.length() == 0){
            name = Main.GameFrame.SideBar.getCommand();
        }
        Main.GameFrame.SideBar.log(name, Main.GameFrame.SideBar.userInput);
        return name;
    }


}
