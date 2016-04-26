import java.util.ArrayList;
import java.util.Arrays;

public class Deck {

	public static final int NO_WILD_CARD_DECK = 0;   
	public static final int WILD_CARD_DECK = 1;
	public static final int NUM_INSIGNIA = 4;
	public static final int INFANTRY = 0;
	public static final int CAVALRY = 1;
	public static final int ARTILLERY = 2;
	public static final int WILD_CARD = 3;
	public static final int NUM_SETS = 13;
	public static final int SET_SIZE = 3;
	public static final int[][] SETS = {
		{INFANTRY,INFANTRY,INFANTRY},
		{INFANTRY,INFANTRY,WILD_CARD},
		{INFANTRY,WILD_CARD,WILD_CARD},
		{CAVALRY,CAVALRY,CAVALRY},
		{CAVALRY,CAVALRY,WILD_CARD},
		{CAVALRY,WILD_CARD,WILD_CARD},
		{ARTILLERY,ARTILLERY,ARTILLERY},
		{ARTILLERY,ARTILLERY,WILD_CARD},
		{ARTILLERY,WILD_CARD,WILD_CARD},
		{INFANTRY,CAVALRY,ARTILLERY},
		{CAVALRY,ARTILLERY,WILD_CARD},
		{INFANTRY,ARTILLERY,WILD_CARD},
		{INFANTRY,CAVALRY,WILD_CARD}};
	
	private static final int[] INSIGNIA = {
		CAVALRY,ARTILLERY,ARTILLERY,INFANTRY,CAVALRY,ARTILLERY,INFANTRY,CAVALRY,INFANTRY,
		CAVALRY,INFANTRY,CAVALRY,ARTILLERY,CAVALRY,INFANTRY,ARTILLERY,INFANTRY,INFANTRY,
		ARTILLERY,INFANTRY,CAVALRY,CAVALRY,CAVALRY,ARTILLERY,INFANTRY,ARTILLERY,ARTILLERY,
		CAVALRY,INFANTRY,CAVALRY,ARTILLERY,CAVALRY,ARTILLERY,CAVALRY,ARTILLERY,INFANTRY,
		CAVALRY,INFANTRY,ARTILLERY,INFANTRY,ARTILLERY,INFANTRY};
	private static final String[] INSIGNIA_NAME = {"Infantry","Cavalry","Artillary","Wild Card"};
	private static final int NUM_WILD_CARDS = 2;

	private ArrayList<Card> cards;
	
	Deck (int deckType) {
		int cardId;
		cards = new ArrayList<Card>();
		for (cardId=0; cardId<GameData.NUM_COUNTRIES; cardId++) {
			cards.add(new Card(cardId,GameData.COUNTRY_NAMES[cardId],INSIGNIA[cardId],INSIGNIA_NAME[INSIGNIA[cardId]]));
		}
		if (deckType == WILD_CARD_DECK) {
			for (; cardId<GameData.NUM_COUNTRIES+NUM_WILD_CARDS; cardId++) {
				cards.add(new Card(cardId,INSIGNIA_NAME[WILD_CARD],WILD_CARD,INSIGNIA_NAME[WILD_CARD]));
			}			
		}
		return;
	}
	
	public void addCards (ArrayList<Card> inCards) {
		cards.addAll(inCards);
		return;
	}
		
	public Card getCard () {
		int index = (int)(Math.random() * cards.size());  
		Card card = cards.remove(index);
		return card;
	}
	
	public boolean isEmpty () {
		return cards.isEmpty();
	}
	
	static public boolean isASet (int[] insigniaIds) {
		boolean found = false;
		Arrays.sort(insigniaIds);
		for (int i=0; (i<NUM_SETS) && !found; i++) {
			if ( (insigniaIds[0]==SETS[i][0]) && (insigniaIds[1]==SETS[i][1]) &&  (insigniaIds[2]==SETS[i][2]) ) {
				found = true;
			}
		}
		return found;
	}
	
}
