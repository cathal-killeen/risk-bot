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

        Main.GameFrame.SideBar.log(players.get(Constants.PLAYER_ORDER[0]).name + " goes first", Main.GameFrame.SideBar.info);
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
