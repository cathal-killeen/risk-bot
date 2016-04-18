
public class Parse {
	
	private String[] countryCodes = new String[GameData.NUM_COUNTRIES];
	private boolean isError = false, turnEnded = false;
	private int countryId = 0, numUnits = 0, fromCountryId = 0, toCountryId = 0;
	private int[] insigniaIds = new int[Deck.SET_SIZE];
	
	Parse () {
		String name;
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			name = GameData.COUNTRY_NAMES[i];
			name = name.toLowerCase();
			name = name.replaceAll("\\s","");
			name = name.substring(0,4);
			countryCodes[i] = name;
		}
		return;
	}
	
	public void country (String string) {
		boolean found = false;
		string = string.replaceAll("\\s", "");
		if (string.length() >= 4) {
			string = string.toLowerCase();
			string = string.substring(0,4);
	 		for (int i=0; (i<GameData.NUM_COUNTRIES) && !found; i++) {
				if (string.equals(countryCodes[i])) {
					found = true;
					countryId = i;
				}
	 		}
		}
		isError = !found;
		return;
	}
	
	public void countryNumber (String string) {
		boolean found = false, parsable = true;
		String[] strings;
		string = string.trim();
		string = string.toLowerCase();
		if ((string.length() > 5) && string.contains(" ") && !string.contains("-")) { 
			strings = string.split("\\s+");
			if (strings.length == 2) {
				country(strings[0]);
				if (!isError) {
					try {
						numUnits = Integer.parseInt(strings[1]);
					} catch (NumberFormatException e) {
						parsable = false;
					}
					if (parsable) {
						if (numUnits>0) {
							found = true;
						}
					}
				}
			}
		}
		isError = !found;
		return;
	}
	
	public void countryCountryNumber (String string) {
		boolean found = false, parsable = true;
		turnEnded = false;
		String[] strings;
		string = string.trim();
		string = string.toLowerCase();
		if (string.equals("skip")) {
			found = true;
			turnEnded = true;
		} else if ((string.length() > 5) && string.contains(" ") && !string.contains("-")) { 
			strings = string.split("\\s+");
			if (strings.length == 3) {
				country(strings[0]);
				fromCountryId = countryId;
				if (!isError) {
					country(strings[1]);
					toCountryId = countryId;
					if (!isError) {
						try {
							numUnits = Integer.parseInt(strings[2]);
						} catch (NumberFormatException e) {
							parsable = false;
						}
						if (parsable) {
							if (numUnits>0) {
								found = true;
							}
						}
					}
				}
			}
		}
		isError = !found;
		return;
	}
	
	public void numUnits (String string) {
		boolean parsable = true;
		try {
			numUnits = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		isError = !parsable;
		return;
	}
	
	public void cardExchange (String string) {
		boolean found = false;
		turnEnded = false;
		string = string.replaceAll("\\s","");
		string = string.toLowerCase();
		if (string.equals("skip")) {
			found = true;
			turnEnded = true;
		} else if (string.matches("(i|c|a|w){3}")) { 
			found = true;
			for (int i=0; i<Deck.SET_SIZE; i++) {
				switch (string.charAt(i)) {
				case 'i' :  insigniaIds[i] = Deck.INFANTRY;
							break;
				case 'c' : 	insigniaIds[i] = Deck.CAVALRY;
							break;
				case 'a' : 	insigniaIds[i] = Deck.ARTILLERY;
							break;
				case 'w' : 	insigniaIds[i] = Deck.WILD_CARD;
							break;
				}
			}
		} 
		isError = !found;
		return;
	}
	
	public boolean isError () {
		return isError;
	}
	
	public boolean isTurnEnded() {
		return turnEnded;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public int getNumUnits () {
		return numUnits;
	}
	
	public int getFromCountryId () {
		return fromCountryId;
	}
	
	public int getToCountryId () {
		return toCountryId;
	}
	
	public int[] getInsigniaIds () {
		return insigniaIds;
	}

}
