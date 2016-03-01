/**
 * Created by adamdoran on 01/03/2016.
 */
public class Commands {
    private static String input;
    private static int playerIndex;

    public Commands(String s, int Player){
        input = s;
        playerIndex = Player;
    }

    //returns the index of the selected country if it is a valid input.
    //returns -1 if
    public int isCountry(){
        String cName = "";
        Boolean matchFound = false;
        Boolean multipleMatches = false;
        int index = -1;
        for (Country country: Main.countries){
            cName = country.getName();
            if (cName.toLowerCase().contains(input.toLowerCase())){
                if (!matchFound){
                    index = country.getIndex();
                    matchFound = true;
                } else {
                    //message: sorry, your input is ambiguous. Please enter more information
                    return -1;
                }
            }
        }

        return index;
    }

    public Boolean isOwned(Country country){
        if (country.getOwner().index == playerIndex){
            return true;
        } else {
            return false;
        }
    }

}
