import java.util.ArrayList;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */

public class Main {

    public static Boolean initialisationComplete = false;

    public static GameFrame GameFrame;
    public static Deck deck;

    public static void main(String[] args) {
        init();
        TurnCycle.play();

        //TEST

        //test nextPlayer function
        for(int i = 0; i<100; i++){
            //waits 2 seconds so the name changing can be seen
            try { Thread.sleep(2000);}
            catch(InterruptedException ex) {}

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
        Deck deck = new Deck();
        GameFrame.SideBar.log("Would you like to randomly add initial reinforcements? (Y/n)", GameFrame.SideBar.prompt);
        String input = GameFrame.SideBar.getCommand();
        if(input.toLowerCase().contains("y")){
            Player.randomInitReinforcements();
        }else{
            Player.initReinforcements();
        }
        //testCountryGroup();

        initialisationComplete = true;
    }

    public static void printWelcomeMessage(){
        GameFrame.SideBar.log("**************************************************\n"
                            + "                    WELCOME TO RISK!\n"
                            + "**************************************************\n",
                            GameFrame.SideBar.info);
    }

    public static void testCountryGroup(){
        GameFrame.SideBar.log("Select a territory to display group\n", GameFrame.SideBar.prompt);
        int countryIndex = -1;
        do {
            String territoryInput = GameFrame.SideBar.getCommand();
            countryIndex = Country.getCountry(territoryInput);
            if(countryIndex >= 0){
                ArrayList<Country> countryGroup = Country.countries.get(countryIndex).getCountryGroup();
                String s = "";
                for(Country country: countryGroup){
                    s += country.name + "\n";
                }
                System.out.print(s);
            }else{
                GameFrame.SideBar.log("That doesn't appear to be a territory. Please enter a valid territory name\n", GameFrame.SideBar.error);
            }
        }while(countryIndex <= 0);
        try { Thread.sleep(2000);}
        catch(InterruptedException ex) {}
    }


}
