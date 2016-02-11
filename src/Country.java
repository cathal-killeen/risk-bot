
import com.sun.tools.classfile.ConstantPool;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class Country {
    public int index;
    public String name;
    public Player owner;
    public int continent;
    public Point2D coOrds;
    public int[] adjacents;
    private int troops;


    public Country(int index){
        this.index = index;
        name = Constants.COUNTRY_NAMES[index];
        continent = Constants.CONTINENT_IDS[index];
        coOrds = Constants.COUNTRY_P2D(index);
        adjacents = Constants.ADJACENT[index];
        setTroops();
    }

    // returns continents name from Constants class
    public String continentName(){
        return Constants.CONTINENT_NAMES[continent];
    }

    public void setOwner(Player player){
        owner = player;
        setTroops();
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
