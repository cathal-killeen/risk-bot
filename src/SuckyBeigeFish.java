// put your code here

import com.sun.tools.internal.jxc.ap.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuckyBeigeFish implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example
	
	private BoardAPI board;
	private PlayerAPI player;

    private ArrayList<Country> countries;
    private ArrayList<Member> members;

    private ArrayList<Turn> turns = new ArrayList<>();

	SuckyBeigeFish(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
        countries = createCountriesList();  //create country class for tracking each country
        members = createMembersList();      //create member list for tracking each player
        turns.add(new Turn());              //add first turn

        //tests
        //logAllCountries();

		return;
	}
	
	public String getName () {
		String command = "";
		// put your code here
		command = "SBFbot";
		return(command);
	}

	public String getReinforcement () {
        String command = "";
		// put your code here
        String country = "";

        ArrayList<Country> borderingEnemy = members.get(enemyId()).getEnemyNeighbors(myId());
        //get the least protected territory bordering the enemy
        if(borderingEnemy.size() != 0){
            Collections.sort(borderingEnemy, compareCountryByUnits);
            country = borderingEnemy.get(0).name;
        }else{
            //if no countries are bordering the enemy - select least protected country
            ArrayList<Country> myCountries = members.get(myId()).ownedCountries();
            Collections.sort(borderingEnemy, compareCountryByUnits);
            country = myCountries.get(0).name;
        }
        //replace spaces in country name
        country = country.replaceAll("\\s", "");
		command = country + " 1";
		return(command);
	}
	
	public String getPlacement (int forPlayer) {
		String command = "";
		// put your code here
        String country = "";

        ArrayList<Country> borderingEnemy = members.get(enemyId()).getEnemyNeighbors(forPlayer);
        //get the least protected territory bordering the enemy
        if(borderingEnemy.size() != 0){
            Collections.sort(borderingEnemy, compareCountryByUnits);
            country = borderingEnemy.get(0).name;
        }else{
            //if no countries are bordering the enemy - select random
            country = members.get(forPlayer).getRandomOwned();
        }
        //replace spaces in country name
        country = country.replaceAll("\\s", "");
        command = country;
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

        //fortify is the last step in each turn
        turns.add(new Turn());
		return(command);
	}

    private int myId(){
        return player.getId();
    }

    //assuming its a 2 player game and the players are assigned before the neutrals
    private int enemyId(){
        if(myId() == 0){
            return 1;
        }
        return 0;

    }

    //creates a list of all the countries on the board - country data can then be easily accessed by using 'countries.get(countryId)'
    private ArrayList<Country> createCountriesList(){
        ArrayList<Country> list = new ArrayList<>();
        for(int i=0; i<GameData.NUM_COUNTRIES; i++){
           list.add(new Country(i));
        }
        return list;
    }

    private ArrayList<Member> createMembersList(){
        ArrayList<Member> list = new ArrayList<>();
        for(int i=0; i<GameData.NUM_PLAYERS_PLUS_NEUTRALS; i++){
            list.add(new Member(i));
        }
        return list;
    }

    private ArrayList<CountryGroup> getAllCountryGroups(){
        ArrayList<CountryGroup> groups = new ArrayList<>();
        ArrayList<Country> exclude = new ArrayList<>();
        for(Country country: countries){
            //if this country is not in the excluded list of countries
            if(!country.groupHasCountry(exclude, country)){
                ArrayList<Country> group = country.getCountryGroup();
                groups.add(new CountryGroup(group));
                //add all of these countries to the excluded group
                exclude = country.mergeCountryGroups(exclude, group);
            }
        }
        return groups;
    }

    //class for checking info on possible attack
    class Attack{
        Country attacker;
        Country defender;

        public Attack(Country a, Country d){
            attacker = a;
            defender = d;
        }

        public int troopDifference(){
            return attacker.numUnits() - defender.numUnits();
        }

        public int troopRatio(){
            return (int)((attacker.numUnits()/defender.numUnits()) * 100);
        }


        public Boolean isPossible(){
            if(attacker.index != defender.index && attacker.isAdjacent(defender) && attacker.numUnits() > 1){
                return true;
            }
            return false;
        }


    }


    //Player class for tracking each of the players on the board - named member so not to clash with Main Player class
    class Member{
        public int index;

        public Member(int ind){
            index = ind;
        }

        public String getRandomOwned(){
            ArrayList<Country> owned = ownedCountries();
            return owned.get((int)(Math.random() * owned.size() -1)).name;
        }

        public Boolean isNetural(){
            if(index>=GameData.NUM_PLAYERS){
                return true;
            }
            return false;
        }

        public ArrayList<Country> ownedCountries(){
            ArrayList<Country> ownedTerritories = new ArrayList<>();
            for(Country country: countries){
                if(country.owner() == index){
                    ownedTerritories.add(country);
                }
            }
            return ownedTerritories;
        }

        //returns a list of all of the country groups owned by this player
        private ArrayList<CountryGroup> getOwnedCountryGroups(){
            ArrayList<CountryGroup> groups = new ArrayList<>();
            ArrayList<Country> exclude = new ArrayList<>();
            for(Country country: ownedCountries()){
                //if this country is not in the excluded list of countries
                if(!country.groupHasCountry(exclude, country)){
                    ArrayList<Country> group = country.getCountryGroup();
                    groups.add(new CountryGroup(group));
                    //add all of these countries to the excluded group
                    exclude = country.mergeCountryGroups(exclude, group);
                }
            }
            return groups;
        }

        //returns a list of countries belonging to enemy that border all of my countries
        public ArrayList<Country> getEnemyNeighbors(int enemy){
            ArrayList<Country> borderEnemies = new ArrayList<>();
            for(Country myCountry: ownedCountries()){
                for(int i=0; i<myCountry.adjacents.length; i++){
                    //if neighboring country is owned by enemy
                    Country borderCountry = countries.get(myCountry.adjacents[i]);
                    if(borderCountry.owner() == enemy){
                        borderEnemies.add(borderCountry);
                    }
                }
            }
            return borderEnemies;
        }
    }


    //Turn class for storing info about each turn - eg. checking if at least one attack was succesful during this turn
    class Turn{
        public int attacksStarted;
        public int attacksWon;

        public Turn(){
            attacksStarted = 0;
            attacksWon = 0;
        }

        public Boolean didWinOnce(){
            if(attacksWon>0){
                return true;
            }
            return false;
        }
    }

    // a country group is a group of countries linked together owned by the same person
    class CountryGroup{
        public ArrayList<Country> list;

        CountryGroup(ArrayList<Country> g){
            list = g;
        }

        public int size(){
            return list.size();
        }

        public int owner(){
            if(list.size() == 0) {
                return -1;
            }else{
                return list.get(0).owner();
            }
        }

        public int totalUnits(){
            int total = 0;
            for(Country country: list){
                total += country.numUnits();
            }
            return total;
        }

        //check if certain country is contained in countrygroup
        public Boolean hasCountry(Country country){
            for(Country member: list){
                if(member == country){
                    return true;
                }
            }
            return false;
        }

        public String toString(){
            String s = "Owner " + owner() + ": ";
            s += size() + " countries: ";
            s += totalUnits() + " units: ";
            int ind = 0;
            for(Country country: list){
                if(ind > 0){
                    s += ", ";
                }
                s+=country.name;
                ind++;
            }
            return s;
        }

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

        //check if adjacent to another country
        public Boolean isAdjacent(Country country){
            for(int i=0; i<adjacents.length; i++) {
                if(country.index == adjacents[i]){
                    return true;
                }
            }
            return false;
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
        public ArrayList<Country> getCountryGroup(ArrayList<Country> excludeList){
            ArrayList<Country> countryGroup = new ArrayList<>();
            countryGroup.add(this);

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
        public ArrayList<Country> mergeCountryGroups(ArrayList<Country> g1, ArrayList<Country> g2){
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

        public String toString(){
            return name + ": " + numUnits();
        }

    }

    Comparator<Country> compareCountryByUnits = new Comparator<Country>(){
        public int compare(Country a, Country b){
            return new Integer(a.numUnits()).compareTo(new Integer(b.numUnits()));
        }
    };

    //*******
    //TEST AREA
    //*******

    private void logAllCountries(){
        Collections.sort(countries, compareCountryByUnits);
        for(Country country: countries){
            System.out.println(country.toString());
        }
        return;
    }

    private void logAllCountryGroups(){

        for(CountryGroup group: getAllCountryGroups()){
            System.out.println(group.toString());
        }

        System.out.println("\n");
        return;
    }
}

