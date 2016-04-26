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
	private Attack lastAttack;
	//Decides the minimum probability required to consider an attack
	private double minProbability = 0.6;
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
	public class Attack{
		int attacker, defender, aID, dID;
		double probability = 0;

		public Attack(int a, int d){
			aID = a;
			dID = d;
			attacker = board.getNumUnits(a);
			defender = board.getNumUnits(GameData.ADJACENT[a][d]);
			//not >= as is essentially a-1 >= d as at least one attacker must remain behind
			if (attacker > defender) {
				probability = calculateProb(attacker, defender);
			}
		}

		public double calculateProb(int a, int d){
			double prob = 0;
			double ratio = (double)(a-1)/(double)d;
			if (a-1 <= 10 && d <= 10){
				prob = probabilityMatrix[a-1-1][d-1];
			} else {
				while (d > 10){
					a-=10;
					d-= 10;
				}
				if (a <= 20) {
					prob = probabilityMatrix[a/2-1][d/2-1];
				} else {
					prob = 1;
				}
			}
			return prob;
		}
	}

	public void sortAttacksByProbability(){
		Collections.sort(possibleAttacks, compareAttackByProb);
	}
	Comparator<Attack> compareAttackByProb = new Comparator<Attack>() {
		@Override
		public int compare(Attack a, Attack b) {
			return new Double(a.probability).compareTo(b.probability);
		}
	};

	private void getPossibleAttacks(){
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == myId){
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (board.getOccupier(GameData.ADJACENT[i][j]) != myId){
						possibleAttacks.add(new Attack(i, GameData.ADJACENT[i][j]));
						if (possibleAttacks.get(possibleAttacks.size()-1).probability <= minProbability){
							possibleAttacks.remove(possibleAttacks.size()-1);
						}
					}
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
		getBoardEquity();
		getPossibleAttacks();
		sortAttacksByProbability();

		Attack chosenAttack;
		lastAttack = chosenAttack;
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

}
