import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


public class RiskMap {

    public static void main(String[] args){

        JFrame Frame = new JFrame();

        Frame.setSize(new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT));
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel Panel = new Map();
        Panel.setPreferredSize(new Dimension(1000, 700));
        Panel.setBackground(Color.white);
        Panel.setLayout(null);
        Frame.setContentPane(Panel);

        Frame.setVisible(true);



//	Panel.setSize(new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT));
//	Panel.setVisible(true);



    }


}
