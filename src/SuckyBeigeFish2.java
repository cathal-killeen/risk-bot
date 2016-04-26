// put your code here

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

	SuckyBeigeFish2 (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;
		player = inPlayer;
		// put your code here
		myId = getMyId();
		enemyId = getEnemyId();
		myContinents = new double[6];
		enemyContinents = new double[6];

		getBoardEquity();

		return;
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
