import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cathal on 07/02/16.
 */

//ABSTRACT ClASS - players will be constructed differently depending on if they are human or neutral
public class Player {
    public int index;
    public String name;
    public Color color;

    public void allocateTerritory(Country country) {
        country.setOwner(this);
    }

    public Player(String name, int index){
        this.index = index;
        this.name = name;
        color = Constants.PLAYER_COLORS[index];
    }

    public ArrayList initialTerritories(ArrayList<Integer> availableCountries, int numCountries){
        int countryIndex;
        for(int i = 0; i < numCountries; i++){
            countryIndex = availableCountries.remove(0);
            Main.countries.get(countryIndex).setOwner(this);
        }

        return availableCountries;
    }


}


//NOTE if(playerObj instanceOf NeutralPlayer){...}
//OR if(playerObj instanceOf HumanPlayer{...}
//can be used to distinguish between human and neutral players