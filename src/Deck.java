import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by adam on 10/04/2016.
 */
public class Deck {
    class Card{
        public String country;
        public String insignia;

        public Card(String c, String i){
            this.country = c;
            this.insignia = i;
        }
    }

    private ArrayList<Card> deck = new ArrayList<Card>(44);

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

        Collections.shuffle(deck);
    }

    public Card draw(){
        return deck.remove(deck.size()-1);
    }
}
