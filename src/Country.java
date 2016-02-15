
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class Country {
    public static final int nodeRadius = 20;

    private int index;
    private String name;
    private Player owner;
    private int continent;
    private Point2D coOrds;
    private Ellipse2D mapNode;
    private Ellipse2D ownershipNode;
    private int[] adjacents;
    private int troops;


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
    }

    public void removeTroops(int defeatedTroops){
        troops -= defeatedTroops;
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




    // create all country objects -- possibly relocate to another file
    // NOTE: does not put country nodes on map, but Point2D coOrds can be accessed from each object
    public static ArrayList createCountries(){
        ArrayList<Country> countries = new ArrayList<>();

        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            countries.add(new Country(i));
        }

        return countries;
    }

}
