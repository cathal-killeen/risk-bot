// put your code here


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

    private Attack lastAttack;

	SuckyBeigeFish(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
        countries = createCountriesList();  //create country class for tracking each country
        members = createMembersList();      //create member list for tracking each player

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
            if (myCountries.size() > 0) {
                Collections.sort(borderingEnemy, compareCountryByUnits);
                country = myCountries.get(0).name;
            }
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
        if(player.isForcedExchange()){
            command = getTradeIns();
        }else{
            command = "skip";
        }

        return(command);
    }

	public String getBattle () {
		String command = "";
		// put your code here

        ArrayList<Attack> possibleAttacks = members.get(myId()).getPossibleAttacks();
        Collections.sort(possibleAttacks, compareAttackByRatio);
        //test
//        for(Attack a: possibleAttacks){
//            System.out.println(a);
//        }

        if(possibleAttacks.size() != 0){
            Attack attack = possibleAttacks.get(possibleAttacks.size()-1);
            if(attack.troopDifference() > 0){
                String attackName = attack.attacker.name.replaceAll("\\s", "");
                String defendName = attack.defender.name.replaceAll("\\s", "");

                command = attackName + " " + defendName + " " + attack.attacker.maxPossibleAttackTroops();
                lastAttack = attack;
            }else{
                command = "skip";
            }
        }else{
            command = "skip";
        }

		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		command += countries.get(countryId).maxPossibleDefendTroops();
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
        Fortify fortify = new Fortify(lastAttack);
        		command += fortify.numTroops();
        return(command);
	}

	public String getFortify () {
		String command = "";
		// put code here
//        ArrayList<Fortify> fortifies = members.get(myId()).getPossibleFortifys();
//        if(fortifies.size() > 0){
//            Collections.sort(fortifies, compareFortifyByTroops);
//            Fortify fortify = fortifies.get(fortifies.size()-1);
//            String donator = fortify.donator.name.replaceAll("\\s", "");
//            String reciever = fortify.reciever.name.replaceAll("\\s", "");
//
//            command = donator + " " + reciever + " " + fortify.numTroops();
//        }else {
            command = "skip";


		return(command);
	}

    //end of public API functions


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

    //create list of memebers (players and neutrals)
    private ArrayList<Member> createMembersList(){
        ArrayList<Member> list = new ArrayList<>();
        for(int i=0; i<GameData.NUM_PLAYERS_PLUS_NEUTRALS; i++){
            list.add(new Member(i));
        }
        return list;
    }

    //gets the tradeins -- this functions saves the Wild cards until the end
    private String getTradeIns(){
        ArrayList<Card> myCards = player.getCards();
        int[] c = {0,0,0,0};         //infantry, cavalry, artillery, wild;
        for(Card card: myCards){
            c[card.getInsigniaId()]++;
        }

        if(c[0] >= 3){
            return "iii";
        }
        if(c[1] >= 3){
            return "ccc";
        }
        if(c[2] >= 3){
            return "aaa";
        }
        if(c[0] >= 1 && c[1] >= 1 && c[2] >= 1){
            return "ica";
        }
        if(c[3] >= 1 && (c[0]+c[1]+c[2]) >= 2){
            if(c[0] >= 2){  return "iiw";}
            if(c[1] >= 2){  return "ccw";}
            if(c[2] >= 2){  return "aaw";}

            if(c[0]>=1 && c[1]>=1){ return "icw";}
            if(c[1]>=1 && c[2]>=1){ return "wca";}
            if(c[0]>=1 && c[2]>=1){ return "iwc";}
        }
        if(c[3] >= 2 && (c[0]+c[1]+c[2]) >= 1){
            if(c[0] >= 1){ return "iww";}
            if(c[1] >= 1){ return "cww";}
            if(c[2] >= 1){ return "aww";}
        }
        if(c[3] >= 3){
            return "www";
        }

        return "";
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

        public double troopRatio(){
            return (double)attacker.numUnits()/defender.numUnits();
        }


        public Boolean isPossible(){
            if(attacker.index != defender.index && attacker.owner() != defender.owner() && attacker.isAdjacent(defender) && attacker.numUnits() > 1){
                return true;
            }
            return false;
        }

        public String toString(){
            return attacker.owner() + ": " + attacker.name + "(" + attacker.numUnits() + ")" + " -> " +
                    defender.owner() + ": " + defender.name + "(" + defender.numUnits() + ")" + ", " +
                    troopRatio();
        }


    }

//    private ArrayList<Fortify> getPossibleFortify(){
//        ArrayList<Fortify> list = new ArrayList<>();
//        for(CountryGroup group: members.get(myId()).getOwnedCountryGroups()){
//            if(group.hasOuter() && group.hasInner()){
//                Fortify f = new Fortify(group.strongestInner(), group.weakestOuter());
//                if(f.isPossible()){
//                    list.add(f);
//                    f.toString();
//                }
//            }
//        }
//        return list;
//    }

    class Fortify{
        Country donator;
        Country reciever;

        public Fortify(Country d, Country r){
            donator = d;
            reciever = r;
        }

        public Fortify(Attack attack){
            donator = attack.attacker;
            reciever = attack.defender;
        }

        public int numTroops(){
            if(donator.numUnits() == 2){
                return 1;
            }
            return (int)((donator.numUnits()/3)*2) -1;
        }

        public Boolean isPossible(){
            if(donator.numUnits() > 1 && donator.doesLinkExist(reciever)){
                return true;
            }
            return false;
        }

        public String toString(){
            return donator.name + "(" + donator.numUnits() + ") " +
                    "--(" + numTroops() + ")--> " +
                    reciever.name + "(" + reciever.numUnits() + ")";
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

        public ArrayList<Fortify> getPossibleFortifys(){
            ArrayList<Fortify> fortifys = new ArrayList<>();
            ArrayList<Country> group = new ArrayList<>();
            for(Country seed: getCountryGroupSeeds()){
                group = seed.getCountryGroup();
                if(hasInner(seed) && hasOuter(seed)){
                    Fortify f = new Fortify(getStrongestInner(seed), getWeakestOuter(seed));
                    if(f.isPossible()){
                        fortifys.add(f);
                    }
                }
            }
            return fortifys;
        }

        private ArrayList<Country> getInners(Country seed){
            ArrayList<Country> group = seed.getCountryGroup();
            ArrayList<Country> inners = new ArrayList<>();
            if(group.size() > 1){
                for(Country country: group){
                    if(country.isInner()){
                        inners.add(country);
                    }
                }
            }
            return group;
        }

        private Boolean hasInner(Country seed){
            ArrayList<Country> group = seed.getCountryGroup();
            for(Country country: group){
                if(country.isInner()){
                    return true;
                }
            }
            return false;
        }

        private Boolean hasOuter(Country seed){
            ArrayList<Country> group = seed.getCountryGroup();
            for(Country country: group){
                if(country.isOuter()){
                    return true;
                }
            }
            return false;
        }

        private Country getWeakestOuter(Country seed){
            ArrayList<Country> group = seed.getCountryGroup();
            Country weakest = seed;
            int w = 10000;
            for(Country country: group){
                if(country.isInner() && country.numUnits() < w){
                    weakest = country;
                    w = weakest.numUnits();
                }
            }
            return weakest;
        }

        private Country getStrongestInner(Country seed){
            ArrayList<Country> group = seed.getCountryGroup();
            Country strongest = seed;
            int s = 0;
            for(Country country: group){
                if(country.isInner() && country.numUnits() > s){
                    strongest = country;
                    s = strongest.numUnits();
                }
            }
            return strongest;
        }

        private ArrayList<Country> getCountryGroupSeeds(){
            ArrayList<Country> owned = ownedCountries();
            ArrayList<Country> seeds = new ArrayList<>();
            ArrayList<Country> exclude = new ArrayList<>();

            for(Country country: owned){
                if(country.groupHasCountry(exclude, country)){
                    //skip
                }else{
                    seeds.add(country);
                    exclude = country.mergeCountryGroups(exclude,country.getCountryGroup());
                }
            }

            return seeds;
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

        public ArrayList<Attack> getPossibleAttacks(){
            ArrayList<Attack> attacks = new ArrayList<>();
            for(Country country: ownedCountries()){
                for(int i=0; i<country.adjacents.length; i++){
                    Attack a = new Attack(country, countries.get(country.adjacents[i]));
                    if(a.isPossible()){
                        attacks.add(a);
                    }
                }
            }
            return attacks;
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

        public Boolean isInner(){
            for(int i=0; i<adjacents.length;i++){
                Country adj = countries.get(adjacents[i]);
                if(adj.owner() == enemyId()){
                    return false;
                }
            }
            return true;
        }

        public Boolean isOuter(){
            return !isInner();
        }

        public ArrayList<Country> getOwnedAdjacents(){
            return getOwnedAdjacents(new ArrayList<Country>());
        }

        public int maxPossibleAttackTroops(){
            if(numUnits() > 3){
                return 3;
            }else{
                return numUnits()-1;
            }
        }

        public int maxPossibleDefendTroops(){
            if(numUnits() == 1){
                return 1;
            }else{
                return 2;
            }
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

        public Boolean doesLinkExist(Country c){
            return groupHasCountry(getCountryGroup(), c);
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

    Comparator<Attack> compareAttackByRatio = new Comparator<Attack>() {
        @Override
        public int compare(Attack a, Attack b) {
            return new Double(a.troopRatio()).compareTo(new Double(b.troopRatio()));
        }
    };

    Comparator<Fortify> compareFortifyByTroops = new Comparator<Fortify>() {
        @Override
        public int compare(Fortify a, Fortify b) {
            return new Integer(a.numTroops()).compareTo(new Integer(b.numTroops()));
        }
    };

}

