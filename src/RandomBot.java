/**
 * Created by Cathal on 25/04/16.
 */

/**
 * A bot that just makes random decisions - use for testing against SBF bot
 */


// put your code here

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RandomBot implements Bot {
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

    RandomBot(BoardAPI inBoard, PlayerAPI inPlayer) {
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
        command = "RandomBot";
        return(command);
    }

    public String getReinforcement () {
        String command = "";
        // put your code here
        String country = members.get(myId()).getRandomOwned();
        country = country.replaceAll("\\s", "");
        command = country + " 1";
        return(command);
    }

    public String getPlacement (int forPlayer) {
        String command = "";
        // put your code here
        String country = members.get(forPlayer).getRandomOwned();
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
        if(possibleAttacks.size() != 0){
            Attack attack = possibleAttacks.get((int)(Math.random() * possibleAttacks.size() -1));
            String attackName = attack.attacker.name.replaceAll("\\s", "");
            String defendName = attack.defender.name.replaceAll("\\s", "");

            command = attackName + " " + defendName + " " + attack.attacker.maxPossibleAttackTroops();
        }else{
            command = "skip";
        }

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

    private ArrayList<String> getWildTradeIns(){
        ArrayList<Card> myCards = player.getCards();
        int[] c = {0,0,0,0};         //infantry, cavalry, artillery, wild;
        for(Card card: myCards){
            c[card.getInsigniaId()]++;
        }

        ArrayList<String> tradeIns = new ArrayList<>();
        if(c[3] >= 3){
            tradeIns.add(new String("www"));
        }
        if(c[3] >= 1 && (c[0]+c[1]+c[2]) >= 2){
            if(c[0] >= 2){  tradeIns.add(new String("iiw"));}
            if(c[1] >= 2){  tradeIns.add(new String("ccw"));}
            if(c[2] >= 2){  tradeIns.add(new String("aaw"));}

            if(c[0]>1 && c[1]>1){   tradeIns.add(new String("icw"));}
            if(c[1]>1 && c[2]>1){   tradeIns.add(new String("wca"));}
            if(c[0]>1 && c[2]>1){   tradeIns.add(new String("iwc"));}
        }
        if(c[3] >= 2 && (c[0]+c[1]+c[2]) >= 1){
            if(c[0] >= 1){  tradeIns.add(new String("iww"));}
            if(c[1] >= 1){  tradeIns.add(new String("cww"));}
            if(c[2] >= 1){  tradeIns.add(new String("aww"));}
        }

        return tradeIns;
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

        //returns a list of all of the country groups owned by this player
        public ArrayList<CountryGroup> getOwnedCountryGroups(){
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

        public int maxPossibleAttackTroops(){
            if(numUnits() > 3){
                return 3;
            }else{
                return numUnits()-1;
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

    Comparator<Attack> compareAttackByRatio = new Comparator<Attack>() {
        @Override
        public int compare(Attack a, Attack b) {
            return new Double(a.troopRatio()).compareTo(new Double(b.troopRatio()));
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
