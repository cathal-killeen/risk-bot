import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class HumanPlayer extends Player {
    public HumanPlayer(String name, int index){
        super(name, index);
    }

    public ArrayList initialTerritories(ArrayList<Integer> availableCountries){
        int countryIndex;
        for(int i = 0; i < 9; i++){
            countryIndex = availableCountries.remove(0);
            Main.countries.get(countryIndex).setOwner(this);
        }

        return availableCountries;
    }


}
