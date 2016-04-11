import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by adam on 10/04/2016.
 */
public class Deck {
    //card objects
    class Card{
        public String country;
        public String insignia;

        public Card(String c, String i){
            this.country = c;
            this.insignia = i;
        }
    }

    //arraylist of cards that make up the deck
    private static ArrayList<Card> deck = new ArrayList<Card>(44);

    //initialise cards with values
    public Deck(){
        String insignia;
        for (int i=0; i<44; i++){
            if (i%3 == 0){
                insignia = "Infantry";
            } else if (i%3 == 1){
                insignia = "Cavalry";
            } else {
                insignia = "Artilery";
            }
            if (i >= 42){
                deck.add(new Card("Wild", "Wild"));
            } else {
                deck.add(new Card(Constants.COUNTRY_NAMES[i], insignia));
            }
        }

        //shuffle deck of cards to mix in wilds
        Collections.shuffle(deck);
    }

    //called in the Player.drawCard() method.
    //returns null of deck empty, else removes and returns the top card of the deck
    public static Card draw() {
        if (deck.size() == 0) {
            return null;
        } else {
            return deck.remove(deck.size() - 1);
        }

    }
}
