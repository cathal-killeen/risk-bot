import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Created by Cathal on 07/02/16.
 */

public class Player {
    public int index;
    public String name;
    public Color color;
    public int reinforcements;

    public Player(String name, int index){
        this.index = index;
        this.name = name;
        color = Constants.PLAYER_COLORS[index];
        if(isHuman()){
            reinforcements = 36;
        }else{
            reinforcements = 24;
        }
    }

    public Boolean isHuman(){
        if(index < 2){
            return true;
        } else{
            return false;
        }
    }

    public void allocateTerritory(Country country) {
        country.setOwner(this);
    }

    public void addReinforcements(int num) {
        reinforcements += num;
    }

    public void reinforceCountry(Country country, int troops){
        country.addTroops(troops);
        reinforcements -= troops;
        Main.GameFrame.Map.repaint();
        Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
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
        int arrayIndex = -1;
        for(int i = 0; i < Constants.PLAYER_ORDER.length; i++){
            if(Constants.PLAYER_ORDER[i] == currentPlayer){
                arrayIndex = i;
            }
        }

        if(arrayIndex == Constants.PLAYER_ORDER.length-1){
            currentPlayer = Constants.PLAYER_ORDER[0];
        }else{
            currentPlayer = Constants.PLAYER_ORDER[arrayIndex+1];
        }

        //redraw player name bar
        Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
        return currentPlayer;
    }

    public static void setPlayerOrder(){

        Dice dice = new Dice();
        int p1Roll = -1;
        int p2Roll = -2;
        do {
            //if repeated, print a prompt that notifies them of the problem
            if (p1Roll == p2Roll){
                Main.GameFrame.SideBar.log("Both players rolled the same value. Please go again\n", Main.GameFrame.SideBar.prompt);
            }
            //get p1 to roll
            Main.GameFrame.SideBar.log(players.get(0).name + ", press enter to roll dice", Main.GameFrame.SideBar.prompt);
            Main.GameFrame.SideBar.getCommand();
            p1Roll = dice.roll();
            Main.GameFrame.SideBar.log("You rolled a " + p1Roll + "\n", Main.GameFrame.SideBar.info);

            //get p2 to roll
            Main.GameFrame.SideBar.log(players.get(1).name + ", press enter to roll dice", Main.GameFrame.SideBar.prompt);
            Main.GameFrame.SideBar.getCommand();
            p2Roll = dice.roll();
            Main.GameFrame.SideBar.log("You rolled a " + p2Roll + "\n", Main.GameFrame.SideBar.info);
        } while (p1Roll == p2Roll);

        if (p1Roll > p2Roll){
            Constants.PLAYER_ORDER[0] = 0;
            Constants.PLAYER_ORDER[1] = 1;
        } else {
            Constants.PLAYER_ORDER[0] = 1;
            Constants.PLAYER_ORDER[1] = 0;
        }
        Constants.PLAYER_ORDER[2] = 2;
        Constants.PLAYER_ORDER[3] = 3;
        Constants.PLAYER_ORDER[4] = 4;
        Constants.PLAYER_ORDER[5] = 5;

        nextPlayer();
        System.out.println("initial player:" + currentPlayer);

        GameFrame.SideBar.log(players.get(currentPlayer).name + " goes first", GameFrame.SideBar.info);
        //Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
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
                GameFrame.SideBar.log("Creating player " + players.get(i).name + "\n", GameFrame.SideBar.info);
            }else{
                // neutral players
                players.add(new Player(neutralPlayerNames[i-2], i));
                players.get(i).initialTerritories(6);
                GameFrame.SideBar.log("Creating neutral player " + players.get(i).name + "\n", GameFrame.SideBar.info);
            }
            String territories = "";
            for(Country country: players.get(i).getOwnedTerritories()){
                territories += "> " +country.getName() + "\n";
            }
            GameFrame.SideBar.log("Initialising territories for "
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
        GameFrame.SideBar.log("Enter name for player " + playerNum + ":", GameFrame.SideBar.prompt);
        while(name.length() == 0){
            name = GameFrame.SideBar.getCommand();
        }
        return name;
    }

    //gets the total number of troops that are to be allocated by all players
    public static int totalTroopsToAllocate(){
        int total = 0;
        for(Player player: players){
            total += player.reinforcements;
        }
        return total;
    }

    public static void initReinforcements(){
        while(totalTroopsToAllocate() > 0) {
            Player currPlayer = players.get(currentPlayer);

            if (currPlayer.isHuman()) {
                int troopsToAllocate = 0;

                //set troopsToAllocate, 3 by default, but remainder of reinforcements if total is less than 3
                if (currPlayer.reinforcements >= 3) {
                    troopsToAllocate = 3;
                } else {
                    troopsToAllocate = currPlayer.reinforcements;
                }

                //NOTE: this also handles case where one player finishes allocation before others
                if (troopsToAllocate > 0) {
                    GameFrame.SideBar.log(players.get(currentPlayer).name + "'s turn\n", GameFrame.SideBar.info);
                    //repeat while troopsToAllocate is > 0
                    do {
                        GameFrame.SideBar.log("Please enter a territory name to allocate reinforcements to", GameFrame.SideBar.prompt);
                        String territoryInput = GameFrame.SideBar.getCommand();
                        int countryIndex = Country.getCountry(territoryInput);
                        if (countryIndex >= 0) {
                            if (Country.countries.get(countryIndex).getOwner().index == currentPlayer) {
                                GameFrame.SideBar.log("You can allocate up to " + troopsToAllocate + " troops. How many would you like to allocate?", GameFrame.SideBar.prompt);
                                int numTroops = 0;
                                while (numTroops == 0) {
                                    numTroops = Integer.parseInt(GameFrame.SideBar.getCommand());
                                    if (numTroops <= troopsToAllocate && numTroops > 0) {
                                        players.get(currentPlayer).reinforceCountry(Country.countries.get(countryIndex), numTroops);
                                        troopsToAllocate -= numTroops;
                                    } else {
                                        GameFrame.SideBar.log("That is an invalid number of troops. Please enter number between 1 and " + troopsToAllocate + "\n", GameFrame.SideBar.error);
                                        numTroops = 0;
                                    }
                                }
                            } else {
                                GameFrame.SideBar.log("You do not own this territory. Please select one that you currently control\n", GameFrame.SideBar.error);
                            }
                        } else {
                            GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name\n", GameFrame.SideBar.error);
                        }
                    } while (troopsToAllocate > 0);
                }
                //check if neutral players have reinforcements
                if(players.get(5).reinforcements > 0){
                    for(int i = 2; i<6; i++){
                        GameFrame.SideBar.log("Select a territory belonging to Player " + (i-1) + " to reinforce with 1 troop", GameFrame.SideBar.prompt);
                        int countryIndex = -1;
                        do {
                            String territoryInput = GameFrame.SideBar.getCommand();
                            countryIndex = Country.getCountry(territoryInput);
                            if(countryIndex >= 0){
                                if(Country.countries.get(countryIndex).getOwner().index == i){
                                    players.get(i).reinforceCountry(Country.countries.get(countryIndex), 1);
                                    GameFrame.SideBar.log(currPlayer.name + " reinforced " + Country.countries.get(countryIndex).name + " on behalf of Player " + (i-1) + "\n", GameFrame.SideBar.info);
                                }else{
                                    GameFrame.SideBar.log("Player " + (i-1) + " does not own " + Country.countries.get(countryIndex).name + ". Please select a territory owned by Player " + (i-1) + "\n", GameFrame.SideBar.error);
                                    countryIndex = -1;
                                }
                            }else{
                                GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name\n", GameFrame.SideBar.error);
                            }
                        }while(countryIndex <= 0);
                    }
                }
            }
            //go to next player
            nextPlayer();
        }
    }


}
