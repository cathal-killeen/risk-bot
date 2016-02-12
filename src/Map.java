import java.awt.*;
import javax.swing.star;

package core;

public class Map extends JPanel{
	
	public Map(){
		this = new JFrame(1000, 600);
	}
	public void populate(){
		for (int i=0;i<42;i++){
	
			Graphics g;		
			drawCountry(g);
		
		}
	}
	public void drawCountry(Graphics g){
		g.setColor(Color.CONTINENT_COLORS[CONTINENT_IDS[i]]);
		g.fillOval(5, 5, COUNTRY_COORD[i][0], COUNTRY_COORD[i][1]);
	}
	
	private static final int[][] COUNTRY_COORD = {
    		{191,150},     // 0
            {255,161},
            {146,86},
            {123,144},
            {314,61},
            {205,235},
            {135,219},
            {140,299},
            {45,89},
            {370,199},
            {398,280},      // 10
            {465,270},
            {547,180},
            {460,200},
            {393,127},
            {463,122},
            {628,227},
            {679,332},
            {572,338},
            {861,213},
            {645,152},      // 20
            {763,70},
            {827,94},
            {751,360},
            {750,140},
            {695,108},
            {760,216},
            {735,277},
            {889,537},
            {850,429},
            {813,526},       // 30
            {771,454},
            {213,352},
            {221,426},
            {289,415},
            {233,523},
            {496,462},
            {440,393},
            {510,532},
            {499,354},
            {547,432},        // 40
            {586,545}
    };
	
	public static void main(String[] args){
		Map temp = new Map();
		temp.populate();
	}
}