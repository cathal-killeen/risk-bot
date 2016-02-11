import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cathal on 07/02/16.
 */

//ABSTRACT ClASS - players will be constructed differently depending on if they are human or neutral
public abstract class Player {
    public String name;
    public Color color;

    public void allocateTerritory(Country country) {
        country.setOwner(this);
    }

    public ArrayList initialTerritories(ArrayList<Integer> availableCountries){
        int countryIndex;
        for(int i = 0; i < 4; i++){
            countryIndex = availableCountries.remove(0);
            Main.countries.get(countryIndex).setOwner(this);
        }

        return availableCountries;
    }


}


//NOTE if(playerObj instanceOf NeutralPlayer){...}
//OR if(playerObj instanceOf HumanPlayer{...}
//can be used to distinguish between human and neutral players