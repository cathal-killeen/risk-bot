/**
 * Created by adam on 28/03/2016.
 */

import java.util.ArrayList;

/** Manages the sequence of players, as well as the different phases of each turn*/
public class TurnCycle {

    public static void cycleManager(){

        //reinforcement phase
        Player.players.get(Player.currentPlayer).addReinforcements(calculateReinforcements());
        allocateReinforcements();

        //combat

    }



    public static int calculateReinforcements(){
        int reinforcementsToAllocate = 0;
        int[] continentsControlled = {0, 0, 0, 0, 0, 0};
        ArrayList<Country> territories = Player.players.get(Player.currentPlayer).getOwnedTerritories();

        //allocate reinforcements based on num territories controlled
        int tmp = Math.floorDiv(territories.size(), 3);
        if (tmp <= 3){
            reinforcementsToAllocate += 3;
        } else {
            reinforcementsToAllocate += tmp;
        }

        //check to see does a player control a continent
        int j=0;
        for (int i=0; i<6; i++){
            boolean ownsAll = true;
            while (Constants.CONTINENT_IDS[j] == i) {
                if (Country.countries.get(j).getOwner().index != Player.currentPlayer){
                    ownsAll = false;
                }
                j++;
            }
            if (ownsAll == true){
                reinforcementsToAllocate += Constants.CONTINENT_VALUES[i];
            }
        }

        return reinforcementsToAllocate;
    }

    public static void allocateReinforcements(){
        int troopsToAllocate = Player.players.get(Player.currentPlayer).reinforcements;
        while (Player.players.get(Player.currentPlayer).reinforcements > 0) {
            int x;
            while (troopsToAllocate > 0) {
                Boolean isCountry = false;
                while (!isCountry) {
                    GameFrame.SideBar.log("Please enter a territory name to allocate reinforcements to\n", GameFrame.SideBar.prompt);
                    String input = GameFrame.SideBar.getCommand();
                    if ((x=Commands.isCountry(input)) >= 0) {
                        if (Country.countries.get(x).getOwner().index == Player.currentPlayer) {
                            GameFrame.SideBar.log("You can allocate up to "+troopsToAllocate+" troops. How many would you like to allocate?\n", GameFrame.SideBar.prompt);
                            int numTroops;
                            do {
                                numTroops = Integer.parseInt(GameFrame.SideBar.getCommand());
                                if (numTroops <= troopsToAllocate && numTroops > 0) {
                                    Player.players.get(Player.currentPlayer).reinforceCountry(Country.countries.get(x), numTroops);
                                    isCountry = true;
                                    troopsToAllocate -= numTroops;
                                } else {
                                    GameFrame.SideBar.log("That is an invalid number of troops. Please enter number between 1 and " + troopsToAllocate + "\n", GameFrame.SideBar.error);
                                }
                            } while (numTroops >= troopsToAllocate && numTroops <= 0);
                        } else {
                            GameFrame.SideBar.log("You do not own this territory. Please select one that you currently control\n", GameFrame.SideBar.error);
                        }
                    } else if(x == -1){
                        GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name", GameFrame.SideBar.error);
                    }
                }
            }
        }
    }


}
