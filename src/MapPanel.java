import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class MapPanel extends JPanel {
    public MapPanel() {
        super();
        setPreferredSize(Constants.MAP_DIM);
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        drawLinks(g2d, Main.countries);
        drawCountryNodes(g2d, Main.countries);


    }

    private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
        for(Country country: countries){
            Ellipse2D circle = new Ellipse2D.Double();
            g2d.setPaint(Constants.CONTINTENT_COLORS[country.continent]);
            circle.setFrameFromCenter(
                    country.coOrds.getX(),
                    country.coOrds.getY(),
                    country.coOrds.getX() + 20,
                    country.coOrds.getY() + 20);
            g2d.fill(circle);
        }
    }

    private void drawLinks(Graphics2D g2d, ArrayList<Country> countries){
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));

        for(int i = 0; i < Constants.ADJACENT.length; i++){
            for(int j = 0; j < Constants.ADJACENT[i].length; j++){
                Line2D link = new Line2D.Double();
                int otherCountry = Constants.ADJACENT[i][j];
                if(i == 8 && otherCountry == 22){
                    link.setLine(
                            countries.get(i).coOrds.getX(),
                            countries.get(i).coOrds.getY(),
                            0,
                            countries.get(otherCountry).coOrds.getY()
                    );
                }else if(i == 22 && otherCountry == 8){
                    link.setLine(
                            countries.get(i).coOrds.getX(),
                            countries.get(i).coOrds.getY(),
                            1000,
                            countries.get(otherCountry).coOrds.getY()

                    );
                }else{
                    link.setLine(
                            countries.get(i).coOrds,
                            countries.get(Constants.ADJACENT[i][j]).coOrds
                    );
                }

                g2d.draw(link);
            }
        }

    }



}
