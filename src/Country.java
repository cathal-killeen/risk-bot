import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class Country {
    public static final int nodeRadius = 20;

    public int index;
    public String name;
    private Player owner;
    private int continent;
    private Point2D coOrds;
    private Ellipse2D mapNode;
    private Ellipse2D ownershipNode;
    private int[] adjacents;
    public int troops;

    public boolean hasOwner(){
        if(owner == null){
            return false;
        }else{
            return true;
        }
    }

    public Country(int index){
        this.index = index;
        name = Constants.COUNTRY_NAMES[index];
        continent = Constants.CONTINENT_IDS[index];
        coOrds = Constants.COUNTRY_P2D(index);
        setMapNode();
        setOwnershipNode();
        adjacents = Constants.ADJACENT[index];
        setTroops();
    }

    public void setMapNode(){
        mapNode = new Ellipse2D.Float();
        mapNode.setFrameFromCenter(
                coOrds.getX(),
                coOrds.getY(),
                coOrds.getX() + nodeRadius,
                coOrds.getY() + nodeRadius);
    }
    
    public void setOwnershipNode(){
        ownershipNode = new Ellipse2D.Float();
        ownershipNode.setFrameFromCenter(
                coOrds.getX(),
                coOrds.getY(),
                coOrds.getX() + nodeRadius/2,
                coOrds.getY() + nodeRadius/2);
    }

    // returns continents name from Constants class
    public String continentName(){
        return Constants.CONTINENT_NAMES[continent];
    }

    public void setOwner(Player player){
        owner = player;
        setTroops();
        Main.GameFrame.Map.repaint();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getContinent() {
        return continent;
    }

    public Point2D getCoOrds() {
        return coOrds;
    }

    public Ellipse2D getMapNode() {
        return mapNode;
    }
    
    public Ellipse2D getOwnershipNode() {
        return ownershipNode;
    }

    public int[] getAdjacents() {
        return adjacents;
    }

    public int getTroopCount(){ 
    	return troops; 
    }

    public void addTroops(int newTroops){
        troops += newTroops;
        Main.GameFrame.Map.repaint();
    }

    public void removeTroop(){
        troops -= 1;
        Main.GameFrame.Map.repaint();
    }

    public void removeTroops(int numTroops){
        troops -= numTroops;
        Main.GameFrame.Map.repaint();
    }

    //set or reset troops to 0
    //[Update (Adam) 13/2/16] troops should be initialised to 1 
    public void setTroops(){
        troops = 1;
    }

    public String toString(){
        StringBuffer b = new StringBuffer();
        b.append("Name: ").append(name).append("\n");
        b.append("Continent: ").append(continentName()).append("\n");
        b.append("Owned by: ");
        if(owner != null){
            b.append(owner.name);
        }else{
            b.append("unowned");
        }
        b.append("\n");
        b.append("Coords: ").append(coOrds.toString()).append("\n");
        return b.toString();
    }

    //check if another country is adjacentg
    public Boolean isAdjacent(Country otherCountry){
        for(int i = 0; i < adjacents.length; i++){
            if(adjacents[i] == otherCountry.index){
                return true;
            }
        }
        return false;
    }

    public void attack(Country defendCountry, int attackTroops){
        Player attackPlayer = getOwner();
        Player defendPlayer = defendCountry.getOwner();
        Dice attackDice = new Dice();
        Dice defendDice = new Dice();

        int defendTroops;
        if(defendCountry.troops <= 1 || attackTroops == 1){
            defendTroops = 1;
        }else{
            defendTroops = 2;
        }

        GameFrame.SideBar.log(attackPlayer.name + " initialised an attack on " + defendCountry.name + " from " + name + "\n", GameFrame.SideBar.info);
        GameFrame.SideBar.log(defendPlayer.name + ", press enter to roll " + defendTroops + " dice", GameFrame.SideBar.prompt);
        if(defendPlayer.isHuman()){
            GameFrame.SideBar.getCommand();
        }
        defendDice.roll(defendTroops);
        GameFrame.SideBar.log(defendDice.toString() + "\n", GameFrame.SideBar.userInput);

        GameFrame.SideBar.log(attackPlayer.name + ", press enter to roll " + attackTroops + " dice", GameFrame.SideBar.prompt);
        GameFrame.SideBar.getCommand();
        attackDice.roll(attackTroops);
        GameFrame.SideBar.log(attackDice.toString() + "\n", GameFrame.SideBar.userInput);

        GameFrame.SideBar.log("Comparing highest dice\n" +
                attackPlayer.name + ": [" + attackDice.highest() + "]\n" +
                defendPlayer.name + ": [" + defendDice.highest() + "]\n", GameFrame.SideBar.info);

        if(attackDice.highest() > defendDice.highest()){
            GameFrame.SideBar.log(attackPlayer.name + " wins! " + defendCountry.name + " loses a troop\n", GameFrame.SideBar.info);
            defendCountry.removeTroop();
        }else if(attackDice.highest() == defendDice.highest()){
            GameFrame.SideBar.log("Tie! " + name + " loses a troop\n", GameFrame.SideBar.info);
            removeTroop();
        }else{
            GameFrame.SideBar.log(defendPlayer.name + " wins! " + name + " loses a troop\n", GameFrame.SideBar.info);
            removeTroop();
        }

        if(attackTroops > 1 && defendTroops > 1){
            GameFrame.SideBar.log("Comparing second highest dice\n" +
                    attackPlayer.name + ": [" + attackDice.secondHighest() + "]\n" +
                    defendPlayer.name + ": [" + defendDice.secondHighest() + "]\n", GameFrame.SideBar.info);

            if(attackDice.secondHighest() > defendDice.secondHighest()){
                GameFrame.SideBar.log(attackPlayer.name + " wins! " + defendCountry.name + " loses a troop\n", GameFrame.SideBar.info);
                defendCountry.removeTroop();
            }else if(attackDice.secondHighest() == defendDice.secondHighest()){
                GameFrame.SideBar.log("Tie! " + name + " loses a troop\n", GameFrame.SideBar.info);
                removeTroop();
            }else{
                GameFrame.SideBar.log(defendPlayer.name + " wins! " + name + " loses a troop\n", GameFrame.SideBar.info);
                removeTroop();
            }
        }

        if(defendCountry.troops <= 0){
            defendCountry.setOwner(attackPlayer);
            //fortify conquered territory with 1 troop (setOwner adds a troop)
            removeTroop();
            GameFrame.SideBar.log(attackPlayer.name + " has conquered " + defendCountry.name + "!!!\n", GameFrame.SideBar.info);
        }
    }




    // STATIC CONTENT

    public static ArrayList<Country> countries = new ArrayList<>();

    //used for initally adding countries at random
    public static ArrayList<Integer> availableCountries = new ArrayList<>();

    // NOTE: does not put country nodes on map, but Point2D coOrds can be accessed from each object
    public static void createCountries(){
        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            countries.add(new Country(i));
        }

        for(int i=0; i < Country.countries.size(); i++){
            availableCountries.add(i);
        }
    }


    public static int getCountry(String input){
        String cName = "";
        Boolean matchFound = false;
        Boolean multipleMatches = false;
        int index = -1;
        for (Country country: countries){
            cName = country.getName();
            if (cName.toLowerCase().contains(input.toLowerCase())){
                if (!matchFound){
                    index = country.getIndex();
                    matchFound = true;
                } else {
                    GameFrame.SideBar.log("Sorry, your entry was ambiguous. Try entering a unique portion of the name next time.\n", GameFrame.SideBar.error);
                    return -2;
                }
            }
        }

        return index;
    }

}
