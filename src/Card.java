import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by adam on 10/04/2016.
 */

public class Card {
    private Country country;
    public int insignia;

    Card(Country c) {
        country = c;
        insignia = Constants.INSIGNIAS[country.getIndex()];
    }

    public String name() {
        return country.getName();
    }

    public String insigniaString(){
        String s = "";
        if (insignia == 0){
            s = "i";
        } else if (insignia == 1){
            s = "c";
        } else if (insignia == 2){
            s = "a";
        }
        return s;
    }

    public String toString(){
        return name() + " (" + insigniaString() + ")";
    }

    //static content
    public static ArrayList<Card> deck = new ArrayList<>();

    public static ArrayList<Card> tradedIn = new ArrayList<>();

    public static void createDeck(){
        for(Country country: Country.countries){
            deck.add(new Card(country));
        }
        Collections.shuffle(deck);
    }

}