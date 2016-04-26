
public class Card {
	
	private int countryId;
	private String countryName;
	private int insigniaId;
	private String insigniaName;
	
	Card () {
		return;
	}
	
	Card (int inCountryId, String inCountryName, int inInsigniaId, String inInsigniaName) {   
		countryId = inCountryId;
		countryName = inCountryName;
		insigniaId = inInsigniaId;
		insigniaName = inInsigniaName;
		return;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public String getCountryName () {
		return countryName;
	}
	
	public int getInsigniaId () {
		return insigniaId;
	}
	
	public String getInsigniaName () {
		return insigniaName;
	}
}