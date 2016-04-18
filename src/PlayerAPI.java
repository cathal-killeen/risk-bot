import java.util.*;

public interface PlayerAPI {
	
	public boolean isCardsAvailable (int[] cardInsigniaIds);
	public boolean isForcedExchange ();
	public boolean isOptionalExchange ();
	public int getId ();
	public String getName ();
	public int getNumUnits ();
	public ArrayList<Integer> getDice ();
	public int getDie (int dieId);
	public int getBattleLoss ();
	public ArrayList<Card> getCards ();
			
}
