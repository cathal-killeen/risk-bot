import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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

    public void initialTerritories(int numCountries){
        int countryIndex;
        Random random = new Random();
        for(int i = 0; i < numCountries; i++){
            countryIndex = Country.availableCountries.remove(random.nextInt(Country.availableCountries.size()));
            Country.countries.get(countryIndex).setOwner(this);
        }
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

    //array list of all players
    public static ArrayList<Player> players = new ArrayList<>();

    //for storing the index of the current player
    public static int currentPlayer = -1;

    //sets currentPlayer to the next player, and returns the index of the next player
    public static int nextPlayer(){
        if(currentPlayer == players.size()-1){
            currentPlayer = 0;
        }else{
            currentPlayer++;
        }

        //redraw player name bar
        Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
        return currentPlayer;
    }

    public static void setPlayerOrder(){
        int[] diceRolls = new int[6];
        Dice dice = new Dice();

        for(int i = 0; i < 6; i++) {
            diceRolls[i] = dice.roll();
            if (i >= 0 && i <= 1) {
                // human players
                Main.GameFrame.SideBar.log(players.get(i).name + ", press enter to roll dice", Main.GameFrame.SideBar.prompt);
                Main.GameFrame.SideBar.getCommand();
                Main.GameFrame.SideBar.log("" + diceRolls[i] + "\n", Main.GameFrame.SideBar.info);
            } else {
                // neutral players
                Main.GameFrame.SideBar.log(players.get(i).name + " rolled " + diceRolls[i], Main.GameFrame.SideBar.info);
            }

            try {
                Thread.sleep(400);
            } catch(InterruptedException ex) {

            }
        }

        int highest = 0;
        for(int i=0; i<diceRolls.length;i++){
            if(diceRolls[i] > highest){
                highest = diceRolls[i];
                currentPlayer = i;
            }
        }

        Main.GameFrame.SideBar.log("\n" + players.get(currentPlayer).name + " goes first\n", Main.GameFrame.SideBar.info);
        Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();

    }

    public static void createPlayers(){
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
                players.get(i).initialTerritories(9);
                Main.GameFrame.SideBar.log("Creating player " + players.get(i).name + "\n", Main.GameFrame.SideBar.info);
            }else{
                // neutral players
                players.add(new Player(neutralPlayerNames[i-2], i));
                players.get(i).initialTerritories(6);
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
