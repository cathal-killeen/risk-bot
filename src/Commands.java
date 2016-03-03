/**
 * Created by adamdoran on 01/03/2016.
 */
public class Commands {

    //returns the index of the selected country if it is a valid input.
    //returns -1 if
    public static int isCountry(String input){
        String cName = "";
        Boolean matchFound = false;
        Boolean multipleMatches = false;
        int index = -1;
        for (Country country: Country.countries){
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

    public Boolean isOwned(Country country, int playerIndex){
        if (country.getOwner().index == playerIndex){
            return true;
        } else {
            return false;
        }
    }

}
