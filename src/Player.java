import java.util.*;
import java.util.Collections;

public class Player implements PlayerAPI {
	
	private int id;
	private String name = "";
	private int numUnits = 0;
	private ArrayList<Integer> dice = new ArrayList<Integer>();
	private int battleLoss = 0;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private boolean isBot = false;
	private Bot bot;
	
	Player (int inId) {
		id = inId;
		return;
	}
	
	public void setName (String inName) {
		name = inName;
		return;
	}
	
	public void setBot (Bot inBot) {
		isBot = true;
		bot = inBot;
		return;
	}
	
	public Bot getBot () {
		return bot;
	}
		
	public void rollDice (int numDice) {
		dice.clear();
		for (int j=0; j<numDice; j++) {
				dice.add(1 + (int)(Math.random() * 6));   
		}
		Collections.sort(dice, Collections.reverseOrder());
		return;
	}

	public void addUnits (int inNum) {
		numUnits = numUnits + inNum;
		return;
	}
	
	public void subtractUnits (int inNum) {
		numUnits = numUnits - inNum;
		return;
	}
	
	public void addCard (Card inCard) {
		cards.add(inCard);
		return;
	}
	
	public void addCards (ArrayList<Card> inCards) {
		cards.addAll(inCards);
		return;
	}
	
	private Card subtractCard (int cardInsigniaId) {
		Card discard = new Card();
		boolean currentFound = false;
		for (int j=0; (j<cards.size()) && !currentFound; j++) {
			if (cardInsigniaId == cards.get(j).getInsigniaId()) {
				discard = cards.remove(j);
				currentFound = true;
			}
		}
		return discard;
	}
	
	public ArrayList<Card> subtractCards (int[] cardInsigniaIds) {
		// precondition: check the cards are available
		ArrayList<Card> discards = new ArrayList<Card>();
		for (int i=0; i<cardInsigniaIds.length; i++) {
			discards.add(subtractCard(cardInsigniaIds[i]));
		}
		return discards;
	}

	public boolean isCardsAvailable (int[] cardInsigniaIds) {
		boolean currentFound = true;      // just to start the loop
		ArrayList<Card> copyCards = new ArrayList<Card>(cards);
		for (int i=0; (i<cardInsigniaIds.length) && currentFound; i++) {
			currentFound = false;
			for (int j=0; (j<copyCards.size()) && !currentFound; j++) {
				if (cardInsigniaIds[i] == copyCards.get(j).getInsigniaId()) {
					copyCards.remove(j);
					currentFound = true;
				}
			}
		}
		return currentFound;
	}
	
	public boolean isForcedExchange () {
		return (cards.size()>=GameData.MAX_NUM_CARDS);
	}
	
	public boolean isOptionalExchange () {
		boolean found = false;
		int[] set = new int[Deck.SET_SIZE];
		for (int i=0; (i<Deck.NUM_SETS) && !found; i++) {
			for (int j=0; j<Deck.SET_SIZE; j++) {
				set[j] = Deck.SETS[i][j];
			}
			found = isCardsAvailable(set);
		}
		return found;
	}
	
	public boolean isBot () {
		return isBot;
	}

	public int getId () {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public int getNumUnits () {
		return numUnits;
	}

	public ArrayList<Integer> getDice () {
		return dice;
	}
	
	public int getDie (int dieId) {
		return dice.get(dieId);
	}
	
	public void resetBattleLoss () {
		battleLoss = 0;
		return;
	}
	
	public void addBattleLoss () {
		battleLoss++;
		return;
	}
	
	public int getBattleLoss () {
		return battleLoss;
	}
	
	public ArrayList<Card> getCards () {
		ArrayList<Card> copyCards = new ArrayList<Card>(cards);
		return copyCards;
	}
	
	public ArrayList<Card> removeCards () {
		ArrayList<Card> allCards = new ArrayList<Card>(cards);
		cards.clear();
		return allCards;
	}
		
}
