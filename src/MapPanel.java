import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Cathal on 09/02/16.
 */
public class MapPanel extends JPanel {
    public MapPanel() {
        super();
        setPreferredSize(Constants.MAP_DIM);
        setOpaque(true);
        setBackground(Color.CYAN);

    }

//    private void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D)g;
//
//        for(int i = 0; i < Main.countries.length; i++){
//            Ellipse2D circle = new Ellipse2D.Float();
//            circle.setFrameFromCenter(
//
//            );
//        }
//        g2d.setColor(Constants.CONTINTENT_COLORS[country.continent]);
//        g2d.fillOval((int)country.coOrds.getX(), (int)country.coOrds.getY(), 10, 10);
//    }
//
//    private void paintAllCountries()


}
