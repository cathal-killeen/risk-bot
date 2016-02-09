
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Cathal on 05/02/16.
 */
public class Constants {
    public static final int NUM_PLAYERS = 2;
    public static final int NUM_NEUTRALS = 4;
    public static final int NUM_PLAYERS_PLUS_NEUTRALS = NUM_PLAYERS + NUM_NEUTRALS;
    public static final int NUM_COUNTRIES = 42;
    public static final int INIT_COUNTRIES_PLAYER = 9;
    public static final int INIT_COUNTRIES_NEUTRAL = 6;
    public static final int INIT_UNITS_PLAYER = 36;
    public static final int INIT_UNITS_NEUTRAL = 24;
    public static final String[] COUNTRY_NAMES = {
            "Ontario", "Quebec", "NW Territory", "Alberta", "Greenland", "E United States", "W United States", "Central America", "Alaska",
            "Great Britain", "W Europe", "S Europe", "Ukraine", "N Europe", "Iceland", "Scandinavia",
            "Afghanistan", "India", "Middle East", "Japan", "Ural", "Yakutsk", "Kamchatka", "Siam", "Irkutsk", "Siberia", "Mongolia", "China",
            "E Australia", "New Guinea", "W Australia", "Indonesia",
            "Venezuela", "Peru", "Brazil", "Argentina",
            "Congo", "N Africa", "S Africa", "Egypt", "E Africa", "Madagascar"};  // for reference
    public static final int[][] ADJACENT = {
            {4, 1, 5, 6, 3, 2},    // 0
            {4, 5, 0},
            {4, 0, 3, 8},
            {2, 0, 6, 8},
            {14, 1, 0, 2},
            {0, 1, 7, 6},
            {3, 0, 5, 7},
            {6, 5, 32},
            {2, 3, 22},
            {14, 15, 13, 10},
            {9, 13, 11, 37},     // 10
            {13, 12, 18, 39, 10},
            {20, 16, 18, 11, 13, 15},
            {15, 12, 11, 10, 9},
            {15, 9, 4},
            {12, 13, 14},
            {20, 27, 17, 18, 12},
            {16, 27, 23, 18},
            {12, 16, 17, 40, 39, 11},
            {26, 22},
            {25, 27, 16, 12},    // 20
            {22, 24, 25},
            {8, 19, 26, 24, 21},
            {27, 31, 17},
            {21, 22, 26, 25},
            {21, 24, 26, 27, 20},
            {24, 22, 19, 27, 25},
            {26, 23, 17, 16, 20, 25},
            {29, 30},
            {28, 30, 31},
            {29, 28, 31},      // 30
            {23, 29, 30},
            {7, 34, 33},
            {32, 34, 35},
            {32, 37, 35, 33},
            {33, 34},
            {37, 40, 38},
            {10, 11, 39, 40, 36, 34},
            {36, 40, 41},
            {11, 18, 40, 37},
            {39, 18, 41, 38, 36, 37},  //40
            {38, 40}
    };
    public static final int NUM_CONTINENTS = 6;
    public static final String[] CONTINENT_NAMES = {"N America", "Europe", "Asia", "Australia", "S America", "Africa"};  // for reference
    public static final int[] CONTINENT_IDS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
    public static final int[] CONTINENT_VALUES = {5, 5, 7, 2, 2, 3};
    public static final Color[] CONTINTENT_COLORS = {Color.YELLOW, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.RED};
    private static final int MAP_WIDTH = 1000;    // must be even
    private static final int MAP_HEIGHT = 600;
    public static Dimension MAP_DIM = new Dimension(MAP_WIDTH, MAP_HEIGHT);
    private static final int SIDEBAR_WIDTH = 250;
    public static Dimension SIDEBAR_DIM = new Dimension(SIDEBAR_WIDTH, MAP_HEIGHT);
    public static Dimension FRAME_DIM = new Dimension(MAP_WIDTH + SIDEBAR_WIDTH, MAP_HEIGHT);
    public static final int[][] COUNTRY_COORD = {
            {191, 150},     // 0
            {255, 161},
            {146, 86},
            {123, 144},
            {314, 61},
            {205, 235},
            {135, 219},
            {140, 299},
            {45, 89},
            {370, 199},
            {398, 280},      // 10
            {465, 270},
            {547, 180},
            {460, 200},
            {393, 127},
            {463, 122},
            {628, 227},
            {679, 332},
            {572, 338},
            {861, 213},
            {645, 152},      // 20
            {763, 70},
            {827, 94},
            {751, 360},
            {750, 140},
            {695, 108},
            {760, 216},
            {735, 277},
            {889, 537},
            {850, 429},
            {813, 526},       // 30
            {771, 454},
            {213, 352},
            {221, 426},
            {289, 415},
            {233, 523},
            {496, 462},
            {440, 393},
            {510, 532},
            {499, 354},
            {547, 432},        // 40
            {586, 545}
    };

}


