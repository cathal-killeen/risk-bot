// put your code here

import java.util.ArrayList;

public class SuckyBeigeFish implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example
	
	private BoardAPI board;
	private PlayerAPI player;
    private ArrayList<Country> countries;

	SuckyBeigeFish(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
        countries = createCountriesList();
		return;
	}
	
	public String getName () {
		String command = "";
		// put your code here
		command = "BOT";
		return(command);
	}

	public String getReinforcement () {
		String command = "";
		// put your code here
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		command += " 1";
		return(command);
	}
	
	public String getPlacement (int forPlayer) {
		String command = "";
		// put your code here
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		return(command);
	}
	
	public String getCardExchange () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getBattle () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		command = "1";
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		command = "0";
		return(command);
	}

	public String getFortify () {
		String command = "";
		// put code here
		command = "skip";
		return(command);
	}

    //creates a list of all the countries on the board - country data can then be easily accessed by using 'countries.get(countryId)'
    private ArrayList<Country> createCountriesList(){
        ArrayList<Country> list = new ArrayList<>();
        for(int i=0; i<GameData.NUM_COUNTRIES; i++){
           list.add(new Country(i));
        }
        return list;
    }



    //internal country class for storing and retrieving info about ALL countries
    class Country{
        public int index;
        public String name;
        public int continent;
        public int[] adjacents;

        public Country(int ind){
            index = ind;
            name = GameData.COUNTRY_NAMES[index];
            continent = GameData.CONTINENT_IDS[index];
            adjacents = GameData.ADJACENT[index];
        }

        public int owner(){
            return board.getOccupier(index);
        }

        public Boolean hasOwner(){
            return board.isOccupied(index);
        }

        public int numUnits(){
            return board.getNumUnits(index);
        }

        public ArrayList<Country> getOwnedAdjacents(){
            return getOwnedAdjacents(new ArrayList<Country>());
        }

        //get the neighboring countries owned by the same person as this country
        private ArrayList<Country> getOwnedAdjacents(ArrayList<Country> excludeList){
            ArrayList<Country> adjacentsList = new ArrayList<>();
            for(int i=0; i<adjacents.length; i++){
                Country adj = countries.get(adjacents[i]);
                Boolean excl = false;
                for(Country excluded: excludeList){
                    if(excluded == adj){
                        excl = true;
                    }
                }
                if(adj.owner() == owner() && excl == false){
                    adjacentsList.add(adj);
                }
            }
            return adjacentsList;
        }

        public ArrayList<Country> getCountryGroup(){
            return getCountryGroup(new ArrayList<Country>());
        }

        //get the group of countries that this country belongs to - ie all of the countries connected and owned by the same player
        private ArrayList<Country> getCountryGroup(ArrayList<Country> excludeList){
            ArrayList<Country> thisCountry = new ArrayList<>();
            thisCountry.add(this);

            ArrayList<Country> countryGroup = mergeCountryGroups(new ArrayList<Country>(), thisCountry);

            ArrayList<Country> ownedAdjacents = getOwnedAdjacents(excludeList);
            if(ownedAdjacents.size() > 0){
                countryGroup = mergeCountryGroups(countryGroup, ownedAdjacents);
                for(Country adj: ownedAdjacents){
                    countryGroup = mergeCountryGroups(countryGroup, adj.getCountryGroup(countryGroup));
                }
            }

            return countryGroup;
        }

        //merges two arraylists of countries together - ensuring duplicates are removed
        private ArrayList<Country> mergeCountryGroups(ArrayList<Country> g1, ArrayList<Country> g2){
            for(Country member: g2){
                //if g1 doesnt already contain member of g2
                if(!groupHasCountry(g1,member)){
                    g1.add(member);
                }
            }

            return g1;
        }

        //check if group of contries contains given country
        public Boolean groupHasCountry(ArrayList<Country> group, Country country){
            for(Country member: group){
                if(member == country){
                    return true;
                }
            }
            return false;
        }

    }
}

