
public interface BoardAPI {

	public boolean isAdjacent (int fromCountry, int toCountry);	
	public boolean isConnected (int fromCountry, int toCountry);	
	public boolean isOccupied(int country); 	
	public boolean isInvasionSuccess ();	
	public boolean isEliminated (int playerId); 
	public int getOccupier (int countryId);
	public int getNumUnits (int countryId);

}
