// put your code here

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuckyBeigeFish2 implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example

	private BoardAPI board;
	private PlayerAPI player;
	private int myId, enemyId, myCountries, enemyCountries;
	private double[] myContinents, enemyContinents;
	private ArrayList<Attack> possibleAttacks = new ArrayList<Attack>();
	private ArrayList<Attack> refinedAttacks = new ArrayList<Attack>();
	private ArrayList<Country> borderCountries;
	private ArrayList<Country> borderlessCountries;

	private Attack lastAttack;
	//Decides the minimum probability required to consider an attack
	private double minProbability = 0.5;
	//matrix of probabilities taken from Jason Osborne's "Markov Chains for the RISK Board Game Revisited"
	//for armies > 10, ratio is calculated and prob. approximated.
	private double[][] probabilityMatrix = {{0.417, 0.106, 0.027, 0.007, 0.002, 0, 0, 0, 0, 0},
											{0.754, 0.363 ,0.206, 0.091, 0.049, 0.021, 0.011, 0.005, 0.003, 0.001},
											{0.916, 0.656, 0.470, 0.315, 0.206, 0.134, 0.084, 0.054, 0.033, 0.021},
											{0.972, 0.785, 0.642, 0.477, 0.359, 0.253, 0.181, 0.123, 0.086, 0.057},
											{0.990, 0.890, 0.769, 0.638, 0.506, 0.397, 0.297, 0.224, 0.162, 0.118},
											{0.997, 0.934, 0.857, 0.745, 0.638, 0.521, 0.423, 0.329, 0.258, 0.193},
											{0.999, 0.967, 0.910, 0.834, 0.736, 0.640, 0.536, 0.446, 0.357, 0.287},
											{1, 0.980, 0.947, 0.888, 0.818, 0.730, 0.643, 0.547, 0.464, 0.380},
											{1, 0.990, 0.967, 0.930, 0.873, 0.808, 0.726, 0.646, 0.558, 0.480},
											{1, 0.994, 0.981, 0.954, 0.916, 0.861, 0.8, 0.724, 0.65, 0.568},
	};

	SuckyBeigeFish2 (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;
		player = inPlayer;
		// put your code here
		myId = getMyId();
		enemyId = getEnemyId();
		myContinents = new double[6];
		enemyContinents = new double[6];
	}


	public String getName () {
		String command = "";
		// put your code here
		command = "SBFbot2.0";
		return(command);
	}

	public String getReinforcement () {
		String command = "";
		String country = "";
		// put your code here
		borderCountries = new ArrayList<Country>();
		getBorderCountries(0);
		//get the least protected territory bordering the enemy
		if(borderCountries.size() != 0){
			Collections.sort(borderCountries, compareCountryByUnits);
			country = borderCountries.get(0).name;
		} else {
			getBorderCountries(-1);
			Collections.sort(borderCountries, compareCountryByUnits);
			country = borderCountries.get(0).name;
		}
		//replace spaces in country name
		country = country.replaceAll("\\s", "");
		command = country + " 1";
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
		getBoardEquity();
		possibleAttacks = new ArrayList<Attack>();
		getPossibleAttacks();
		refinedAttacks = new ArrayList<Attack>();
		getRefinedAttacks();
		Collections.sort(refinedAttacks, compareAttackByProb);
		if (refinedAttacks.size() > 0) {
			Attack chosenAttack = refinedAttacks.get(refinedAttacks.size() - 1);
			lastAttack = chosenAttack;

			String attackName = GameData.COUNTRY_NAMES[chosenAttack.aID].replaceAll("\\s", "");
			String defendName =  GameData.COUNTRY_NAMES[chosenAttack.dID].replaceAll("\\s", "");
			int troops;
			if (chosenAttack.attacker > 3){
				troops = 3;
			} else {
				troops = chosenAttack.attacker-1;
			}
			command = attackName + " " + defendName + " " + troops;
		} else {
			command = "skip";
		}
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		if (board.getNumUnits(countryId) >= 2){
			command += 2;
		} else {
			command += 1;
		}
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
		borderlessCountries = new ArrayList<Country>();
		getBorderlessCountries();
		Collections.sort(borderlessCountries, compareCountryByUnits);
		if (borderlessCountries.size() > 0) {
			possibleAttacks = new ArrayList<Attack>();
			getPossibleAttacks();
			Collections.sort(possibleAttacks, compareAttackByProb);
			int i = 0;
			while (possibleAttacks.size() > i && (!board.isConnected(borderlessCountries.get(borderlessCountries.size() - 1).index, possibleAttacks.get(i).aID))) {
				i++;
			}
			if (possibleAttacks.size() > i) {
				String donator = GameData.COUNTRY_NAMES[borderlessCountries.get(borderlessCountries.size() - 1).index].replaceAll("\\s", "");
				String reciever = GameData.COUNTRY_NAMES[possibleAttacks.get(i).aID].replaceAll("\\s", "");
				int troops = 0;
				if (borderlessCountries.get(borderlessCountries.size() - 1).numUnits() > 1){
					if (borderlessCountries.get(borderlessCountries.size() - 1).numUnits() == 2){
						troops = 1;
					} else {
						troops = borderlessCountries.get(borderlessCountries.size() - 1).numUnits() * 2 / 3;
					}
					command = donator + " " + reciever + " " + troops;
				} else {
					command = "skip";
				}
			} else {
				command = "skip";
			}
		} else {
			command = "skip";
		}
		return(command);
	}

	//END OF PUBLIC API
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

	public class Attack{
		int attacker, defender, aID, dID;
		double probability = 0;

		public Attack(int a, int d){
			aID = a;
			dID = d;
			attacker = board.getNumUnits(a);
			defender = board.getNumUnits(d);
			//not >= as is essentially a-1 >= d as at least one attacker must remain behind
			if (attacker > defender) {
				probability = calculateProb(attacker, defender);
			}
		}

		public double calculateProb(int a, int d){
			double prob = 0;
			if (a-1 <= 10 && d <= 10){
				prob = probabilityMatrix[a-1-1][d-1];
			} else if (a-1 > d){
				while (d > 10){
					a-=10;
					d-= 10;
				}
				if (a <= 20 && d >= 2) {
					prob = probabilityMatrix[a/2-1][d/2-1];
				} else {
					prob = 1;
				}
			}
			return prob;
		}
	}

	class Country {
		public int index;
		public String name;
		public int continent;
		public int[] adjacents;

		public Country(int ind) {
			index = ind;
			name = GameData.COUNTRY_NAMES[index];
			continent = GameData.CONTINENT_IDS[index];
			adjacents = GameData.ADJACENT[index];
		}

		public int owner(){return board.getOccupier(index);}

		public Boolean hasOwner(){return board.isOccupied(index);}

		public int numUnits(){ return board.getNumUnits(index);}
	}

	private void getRefinedAttacks(){
		for (int i=0; i<possibleAttacks.size(); i++){
			if (possibleAttacks.get(i).probability >= minProbability) {
				refinedAttacks.add(possibleAttacks.get(i));
				int defenderCont = GameData.CONTINENT_IDS[possibleAttacks.get(i).dID];
				refinedAttacks.get(refinedAttacks.size()-1).probability += refinedAttacks.get(refinedAttacks.size()-1).probability * 0.5 * (Math.pow(myContinents[defenderCont], 2) + Math.pow(enemyContinents[defenderCont], 2));
			}
		}
	}

	private void getPossibleAttacks(){
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == myId){
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (board.getOccupier(GameData.ADJACENT[i][j]) != myId){
						possibleAttacks.add(new Attack(i, GameData.ADJACENT[i][j]));
					}
				}
			}
		}
	}

	private void getBorderCountries(int flag){
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == myId){
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (flag == -1) {
						if (board.getOccupier(GameData.ADJACENT[i][j]) != myId) {
							borderCountries.add(new Country(i));
						}
					} else {
						if (board.getOccupier(GameData.ADJACENT[i][j]) == enemyId) {
							borderCountries.add(new Country(i));
						}
					}
				}
			}
		}
	}

	private void getBorderlessCountries(){
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == myId){
				boolean flag = true;
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (board.getOccupier(GameData.ADJACENT[i][j]) != myId) {
						flag = false;
					}
				}
				if (flag){
					borderlessCountries.add(new Country(i));
				}
			}
		}
	}

	private void getBoardEquity(){
		int owner;
		myCountries = 0;
		enemyCountries = 0;
		int[][] continents = new int[2][6];
		for (int i=0; i<6; i++){
			continents[0][i] = 0;
			continents[1][i] = 0;
		}
		for (int i=0; i< GameData.NUM_COUNTRIES; i++){
			owner = board.getOccupier(i);
			if (owner == myId){
				myCountries++;
				continents[0][GameData.CONTINENT_IDS[i]]++;
			} else if (owner == enemyId){
				enemyCountries++;
				continents[1][GameData.CONTINENT_IDS[i]]++;
			}
		}
		for (int i=0;i<6; i++){
			myContinents[i] = (double)continents[0][i]/(double)GameData.CONTINENT_COUNTRIES[i].length;
			enemyContinents[i] = (double)continents[1][i]/(double)GameData.CONTINENT_COUNTRIES[i].length;
		}
	}

	private int getMyId(){ return player.getId(); }

	private int getEnemyId(){
		if(getMyId() == 0){ return 1; }
		return 0;
	}

	class Fortify{
		int donator;
		int receiver;

		public Fortify(Country d, Country r){
			donator = d.index;
			receiver = r.index;
		}

		public Fortify(Attack attack){
			donator = attack.aID;
			receiver = attack.dID;
		}

		public int numTroops(){
			if(board.getNumUnits(donator) == 2){
				return 1;
			}
			return (board.getNumUnits(donator)/3)*2 -1;
		}

		public Boolean isPossible(){
			if(board.getNumUnits(donator) > 1 && board.isConnected(donator, receiver)){
				return true;
			}
			return false;
		}

		public String toString(){
			return GameData.COUNTRY_NAMES[donator] + "(" + board.getNumUnits(donator) + ") " +
					"--(" + numTroops() + ")--> " +
					GameData.COUNTRY_NAMES[receiver] + "(" + board.getNumUnits(receiver) + ")";
		}
	}

	Comparator<Attack> compareAttackByProb = new Comparator<Attack>() {
		@Override
		public int compare(Attack a, Attack b) {
			return new Double(a.probability).compareTo(b.probability);
		}
	};
	Comparator<Country> compareCountryByUnits = new Comparator<Country>(){
		public int compare(Country a, Country b){
			return new Integer(a.numUnits()).compareTo(new Integer(b.numUnits()));
		}
	};

}
