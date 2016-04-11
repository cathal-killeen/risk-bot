/**
 * Created by adam on 28/03/2016.
 */

import javax.swing.*;
import java.util.ArrayList;

/** Manages the sequence of players, as well as the different phases of each turn*/
public class TurnCycle {

    public static void play(){
        //while a winner doesnt exist
        while(!Player.doesWinnerExist()){
            Player currPlayer = Player.players.get(Player.currentPlayer);
            if(currPlayer.getOwnedTerritories().size() != 0){
                int reinforcements = calculateReinforcements();
                currPlayer.reinforcements = reinforcements;
                Main.GameFrame.Map.PlayerNamesBar.putPlayerNames();
                GameFrame.SideBar.log(currPlayer.name + "'s turn\n", GameFrame.SideBar.info);
                GameFrame.SideBar.log("You received " + reinforcements + " backup reinforcements\n", GameFrame.SideBar.info);

                currPlayer.allocateReinforcements(reinforcements);
                //attack here
                if(currPlayer.isHuman()){
                    cardSequence();
                    Boolean didWinAttack = attackSequence();
                    fortifySequence();

                    if(didWinAttack && Card.deck.size() > 0){
                        GameFrame.SideBar.log("You have recieved a territory card", GameFrame.SideBar.info);
                        Card drawnCard = currPlayer.drawCard();
                        GameFrame.SideBar.info(drawnCard.toString() + "\n");
                    }
                }
            }
            Player.nextPlayer();
        }
        if(Player.doesWinnerExist()){
            Player winner = Player.getWinner();
            JOptionPane.showMessageDialog(null, "Congratulations " + winner.name + "! You won!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
        }



    }

    public static void cardSequence(){
         if(Player.players.get(Player.currentPlayer).hasPossibleTradeIn()){
             GameFrame.SideBar.prompt("Would you like to trade in cards for reinforcements? (Y/n)");
         }
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
            while (j < Constants.CONTINENT_IDS.length && Constants.CONTINENT_IDS[j] == i) {
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

    //returns true if the player won an attack during their turn, false if otherwise
    public static Boolean attackSequence(){
        Boolean attackWon = false;
        Player currPlayer = Player.players.get(Player.currentPlayer);
        Boolean skip = false;

        while(!skip){
            GameFrame.SideBar.log("ATTACK!\nIf you do not wish to attack, type skip\n", GameFrame.SideBar.info);
            GameFrame.SideBar.log("Select a country to attack from\n",GameFrame.SideBar.prompt);
            String attackerName = GameFrame.SideBar.getCommand();
            if(attackerName.toLowerCase().contains("skip")) {
                skip = true;
            }else{
                int attackIndex = -1;
                int defendIndex = -1;
                while(attackIndex < 0){
                    attackIndex = Country.getCountry(attackerName);
                    //if user entered correct country
                    if(attackIndex >= 0){
                        Country attackCountry = Country.countries.get(attackIndex);
                        //if the current player owns the country
                        if(attackCountry.getOwner() == currPlayer){
                            //check if country has sufficient troops.
                            if(attackCountry.troops > 1){
                                GameFrame.SideBar.log("Please select the territory you wish to attack\n", GameFrame.SideBar.prompt);
                                String defenderName = GameFrame.SideBar.getCommand();
                                defendIndex = -1;


                                while(defendIndex < 0){
                                    defendIndex = Country.getCountry(defenderName);
                                    //if user entered a country
                                    if(defendIndex >= 0){
                                        Country defendCountry = Country.countries.get(defendIndex);
                                        //check that player DOESNT own defendCountry
                                        if(defendCountry.getOwner() != currPlayer){
                                            //if countries are adjacent
                                            if(attackCountry.isAdjacent(defendCountry)){
                                                int numTroops = -1;
                                                int maxAttackTroops;
                                                if(attackCountry.troops <= 3){
                                                    maxAttackTroops = attackCountry.troops - 1;
                                                }else{
                                                    maxAttackTroops = 3;
                                                }
                                                GameFrame.SideBar.log("How many troops would you like to attack with?.\n", GameFrame.SideBar.prompt);
                                                while(numTroops < 0){
                                                    try
                                                    {
                                                        numTroops = Integer.parseInt(GameFrame.SideBar.getCommand());
                                                    }
                                                    catch (NumberFormatException ex)
                                                    {
                                                        //not an integer
                                                    }
                                                    //numTroops must be less than the number of troops on the territory (as at least one has to stay behind)
                                                    if(numTroops > 1 && numTroops <= maxAttackTroops && numTroops < attackCountry.troops){
                                                        Boolean attackWins = attackCountry.attack(defendCountry, numTroops);
                                                        if(attackWins){
                                                            attackWon = true;
                                                        }

                                                    }else{
                                                        GameFrame.SideBar.log("That is an invalid number of troops to attack with. You can attack with a max of " + maxAttackTroops + " troops. Try again:\n", GameFrame.SideBar.error);
                                                        numTroops = -1;
                                                    }
                                                }
                                            }else{
                                                GameFrame.SideBar.log("These territories are not adjacent. Please select two territories that are.\n", GameFrame.SideBar.error);
                                                attackIndex = -1; // go back to beginning - select attacker country
                                                GameFrame.SideBar.log("Select a country to attack from", GameFrame.SideBar.prompt);
                                            }
                                        }else{
                                            GameFrame.SideBar.log("You own this territory. Please select one that you do not currently control.\n", GameFrame.SideBar.error);
                                            defendIndex = -1;
                                        }
                                    }else{
                                        GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
                                        defendIndex = -1;
                                    }

                                    if(defendIndex < 0){
                                        defenderName = GameFrame.SideBar.getCommand();
                                    }
                                }



                            }else{
                                GameFrame.SideBar.log("This territory does not have sufficient troops to launch an attack. Please choose another, or type 'skip' to proceed. \n", GameFrame.SideBar.error);
                                attackIndex = -1;
                            }
                        }else{
                            GameFrame.SideBar.log("You do not own " + Country.countries.get(attackIndex).name + ". Please select one that you currently control.\n", GameFrame.SideBar.error);
                            attackIndex = -1; //to go through loop again
                        }
                    }else{
                        GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
                    }

                    if(attackIndex < 0){
                        attackerName = GameFrame.SideBar.getCommand();
                    }
                }


            }
        }

        return attackWon;
    }

    public static void fortifySequence(){
        Player currPlayer = Player.players.get(Player.currentPlayer);
        int toBeFortifiedIndex = -1;
        int fortifierIndex = -1;



        while(toBeFortifiedIndex < 0){
            GameFrame.SideBar.log("Please enter a territory name you wish to fortify.(Or 'skip')\n", GameFrame.SideBar.prompt);
            String toBeFortifiedName = GameFrame.SideBar.getCommand();
            if(toBeFortifiedName.toLowerCase().contains("skip")){
                toBeFortifiedIndex = 1;
            }else{
                toBeFortifiedIndex = Country.getCountry(toBeFortifiedName);
                //check if input is a country
                if(toBeFortifiedIndex >= 0) {
                    Country toBeFortified = Country.countries.get(toBeFortifiedIndex);
                    //if the current player owns the country
                    if(toBeFortified.getOwner() == currPlayer) {
                        fortifierIndex = -1;
                        GameFrame.SideBar.log("Please enter the territory you wish to fortify from\n", GameFrame.SideBar.prompt);
                        String fortifierName;


                        while(fortifierIndex < 0){
                            fortifierName = GameFrame.SideBar.getCommand();
                            fortifierIndex = Country.getCountry(fortifierName);

                            if(fortifierIndex >= 0){
                                Country fortifer = Country.countries.get(fortifierIndex);
                                //if the current player owns the country
                                if(fortifer.getOwner() == currPlayer){
                                    //check if link exists
                                    if(fortifer.doesLinkExist(toBeFortified)){
                                        int numTroops = -1;
                                        int maxFortifyTroops = fortifer.troops - 1;
                                        GameFrame.SideBar.log("How many troops would you like to send from " + fortifer.name + " to " + toBeFortified.name + "?\n", GameFrame.SideBar.prompt);
                                        while(numTroops < 0){
                                            numTroops = Integer.parseInt(GameFrame.SideBar.getCommand());
                                            //numTroops must be less than the number of troops in the fortifing country (ie there must be at least 1 troop remaining in all territories)
                                            if(numTroops >= 1 && numTroops <= maxFortifyTroops){
                                                //fortify country
                                                fortifer.fortify(toBeFortified, numTroops);

                                                GameFrame.SideBar.log(currPlayer.name + " sent " + numTroops + " from " + fortifer.name + " to " + toBeFortifiedName + "\n", GameFrame.SideBar.info);
                                                try { Thread.sleep(400);}
                                                catch(InterruptedException ex) {}
                                            }else{
                                                GameFrame.SideBar.log("That is an invalid number of troops to fortify with. You can fortify with a max of " + maxFortifyTroops + " troops. Try again:\n", GameFrame.SideBar.error);
                                                numTroops = -1;
                                            }
                                        }
                                    }else{
                                        GameFrame.SideBar.log("A link doesn't exist between " + fortifer.name + " and " + toBeFortified.name + ". Please select two territories that are connected by other territories you own\n", GameFrame.SideBar.error);
                                        toBeFortifiedIndex = -1; //to go through loop again
                                    }
                                }else{
                                    GameFrame.SideBar.log("You do not own " + fortifer.name + ". Please select one that you currently control.\n", GameFrame.SideBar.error);
                                    fortifierIndex = -1; //to go through loop again
                                }
                            }else{
                                GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
                            }
                        }


                    }else{
                        GameFrame.SideBar.log("You do not own " + toBeFortified.name + ". Please select one that you currently control.\n", GameFrame.SideBar.error);
                        toBeFortifiedIndex = -1; //to go through loop again
                    }
                }else{
                    GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name.\n", GameFrame.SideBar.error);
                }
            }

        }

    }

}
