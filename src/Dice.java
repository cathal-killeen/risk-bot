import java.awt.Dimension;
import javax.swing.*;


// Stan O' Neill

// For rolling dice
public class Dice{
	
	public int roll(){
		int min = 1;
		int max = 6;
		int Rolled;


		//Generating random number
		Rolled= min + (int)(Math.random() * max); 
		
//		JFrame diceFace = new JFrame();
//		diceFace.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		diceFace.setVisible(true);
//		diceFace.setSize(new Dimension(420, 420));
//
//
//
//
//		//Getting images depending on the number generated
//		if(Rolled== 1){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\DiceFace1.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//		if(Rolled== 2){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\diceface2.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//		if(Rolled== 3){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\diceface3.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//		if(Rolled== 4){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\diceface4.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//		if(Rolled== 5){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\diceface5.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//		if(Rolled== 6){
//			ImageIcon face = new ImageIcon("C:\\SoftwareEngineering2\\SuckyBeigeFish\\src\\images\\diceface6.jpg");
//			JLabel label = new JLabel(face);
//			label.setBounds(12, 12, 420, 420);
//			label.setVisible(true);
//			diceFace.add(label);
//		}
//
//
//		diceFace.pack();
		return Rolled;
	}
}