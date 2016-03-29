/**
 * Created by adam on 28/03/2016.
 */

import java.util.ArrayList;

/** Manages the sequence of players, as well as the different phases of each turn*/
public class TurnCycle {

    public static void play(){
        //while a winner doesnt exist
        while(!Player.doesWinnerExist()){
            Player currPlayer = Player.players.get(Player.currentPlayer);
            int reinforcements = 7;
            currPlayer.reinforcements = reinforcements;
            Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
            GameFrame.SideBar.log(currPlayer.name + "'s turn\n", GameFrame.SideBar.info);
            GameFrame.SideBar.log("You received " + reinforcements + " backup reinforcements\n", GameFrame.SideBar.info);

            currPlayer.allocateReinforcements(reinforcements);


            Player.nextPlayer();
        }
    }

    public static void cycleManager(){

        //reinforcement phase
        Player.players.get(Player.currentPlayer).addReinforcements(calculateReinforcements());

        //combat
        attackSequence();
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

    public static void attackSequence(){
        String input;
        int x;
        int y;
        while ((input = GameFrame.SideBar.getCommand().toLowerCase()) != "skip"){
            GameFrame.SideBar.log("Please enter a territory name you wish to attack from.\n", GameFrame.SideBar.prompt);
            String attacker = GameFrame.SideBar.getCommand();
            //check if input is a country
            if ((x=Commands.isCountry(attacker)) >= 0) {
                //check if player owns the country
                if (Country.countries.get(x).getOwner().index == Player.currentPlayer) {
                    //check if country has sufficient troops
                    if (Country.countries.get(x).getTroopCount() > 1) {

                        GameFrame.SideBar.log("Please enter a territory name you wish to attack from.\n", GameFrame.SideBar.prompt);
                        String attackee = GameFrame.SideBar.getCommand();
                        //check is attackee a country
                        if ((y=Commands.isCountry(attackee)) >= 0) {
                            //check if attacker owns attackee
                            if (Country.countries.get(y).getOwner().index != Player.currentPlayer) {
                                boolean isAdjacent = false;
                                //check are countries adjacent
                                for (int i=0; i< Constants.ADJACENT.length; i++) {
                                    if (Country.countries.get(y).index == Constants.ADJACENT[x][i]){
                                        isAdjacent = true;
                                    }
                                }
                                //if adjacent
                                if (isAdjacent){
                                    GameFrame.SideBar.log("How many troops would you like to attack with?.\n", GameFrame.SideBar.prompt);
                                    int numTroops = Integer.parseInt(GameFrame.SideBar.getCommand());

                                    //check if the number of troops is valid
                                    if (numTroops > 1 && numTroops <= 3 && numTroops >= Country.countries.get(x).getTroopCount()){

                                        //DO ATTACK STUFF HERE

                                    } else {
                                        GameFrame.SideBar.log("That is an invalid number of troops to attack with. Please try again.\n", GameFrame.SideBar.error);
                                    }
                                } else {
                                    GameFrame.SideBar.log("These territories are not adjacent. Please select two territories that are.\n", GameFrame.SideBar.error);
                                }
                            } else {
                                GameFrame.SideBar.log("You own this territory. Please select one that you do not currently control.\n", GameFrame.SideBar.error);
                            }
                        } else {
                            GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
                        }
                    } else {
                        GameFrame.SideBar.log("This territory does not have sufficient troops to launch an attack. Please choose another, or type 'skip' to proceed. \n", GameFrame.SideBar.error);
                    }
                } else {
                    GameFrame.SideBar.log("You do not own this territory. Please select one that you currently control.\n", GameFrame.SideBar.error);
                }
            } else {
                GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
            }
        }
    }

    public static void fortify(){
        //figure out way to check if there exists an link between two chosen territories
    }


}
