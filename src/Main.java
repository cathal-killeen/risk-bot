
/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {

    public static Boolean initialisationComplete = false;

    public static GameFrame GameFrame;

    public static void main(String[] args) {
        init();

        //TESTS

        //test nextPlayer function
        for(int i = 0; i<100; i++){
            //waits 2 seconds so the name changing can be seen
            try { Thread.sleep(2000);}
            catch(InterruptedException ex) {}

            System.out.println("Current player: " + Player.players.get(Player.nextPlayer()).name);
        }

        //console log all countries
        for (int i = 0; i < Country.countries.size(); i++) {
            System.out.println(Country.countries.get(i).toString());
        }

    }

    public static void init(){
        Country.createCountries();
        GameFrame = new GameFrame();
        printWelcomeMessage();
        Player.createPlayers();
        Player.setPlayerOrder();
        initReinforcements();
        initialisationComplete = true;
    }

    public static void printWelcomeMessage(){
        GameFrame.SideBar.log("**************************************************\n"
                            + "                    WELCOME TO RISK!\n"
                            + "**************************************************\n",
                            GameFrame.SideBar.info);
    }

    public static void initReinforcements(){
        while (Player.players.get(Constants.PLAYER_ORDER[5]).reinforcements > 0) {
            GameFrame.SideBar.log(Player.players.get(Player.currentPlayer).name + "'s turn\n", GameFrame.SideBar.info);
            int x;
            int troopsToAllocate = 3;
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
            for (int i=2; i<6; i++){
                GameFrame.SideBar.log("Please allocate a reinforcement on behalf of "+Player.players.get(Constants.PLAYER_ORDER[i]).name+"\n", GameFrame.SideBar.prompt);
                Boolean isCountry = false;
                while (!isCountry) {
                    String input = GameFrame.SideBar.getCommand();
                    if ((x=Commands.isCountry(input)) >= 0) {
                        if (Country.countries.get(x).getOwner().index == Constants.PLAYER_ORDER[i]) {
                            Player.players.get(Constants.PLAYER_ORDER[i]).reinforceCountry(Country.countries.get(x), 1);
                            isCountry = true;
                        } else {
                            GameFrame.SideBar.log(Player.players.get(Constants.PLAYER_ORDER[i]).name+" does not own this territory. Please select one that they currently control\n", GameFrame.SideBar.error);
                        }
                    } else {
                        GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name\n", GameFrame.SideBar.error);
                    }
                }
                Player.currentPlayer++;
            }
        }
    }

}