
public interface Bot {

		public String getName ();
		public String getReinforcement ();	
		public String getPlacement (int forPlayer);
		public String getCardExchange ();
		public String getBattle ();
		public String getDefence (int countryId);
		public String getMoveIn (int attackCountryId);
		public String getFortify ();

}
