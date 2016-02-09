import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Point2D;

/**
 * Created by Cathal on 09/02/16.
 */
public class Country {
    public String name;
    public Player owner;
    public int continent;
    public Point2D coOrds;
    private int troops;

    public Country(int index){
        name = Constants.COUNTRY_NAMES[index];
        continent = Constants.CONTINENT_IDS[index];
        coOrds = new Point2D.Float((float)Constants.COUNTRY_COORD[index][0], (float)Constants.COUNTRY_COORD[index][1]);
        setTroops();
    }

    // returns continents name from Constants class
    public String continentName(){
        return Constants.CONTINENT_NAMES[continent];
    }

    public void setOwner(Player player){
        owner = player;
    }

    public int getTroopCount(){ return troops; }

    public void addTroops(int newTroops){
        troops += newTroops;
    }

    public void removeTroops(int defeatedTroops){
        troops -= defeatedTroops;
    }

    //set or reset troops to 0
    public void setTroops(){
        troops = 0;
    }

    public String toString(){
        StringBuffer b = new StringBuffer();
        b.append("Name: ").append(name).append("\n");
        b.append("Continent: ").append(continentName()).append("\n");
        b.append("Owned by: ");
        if(owner != null){
            b.append(owner);
        }else{
            b.append("unowned");
        }
        b.append("\n");
        return b.toString();
    }

}
