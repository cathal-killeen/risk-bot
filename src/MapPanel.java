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

    //draw country nodes with continent colors
    private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
        for(Country country: countries){
            Ellipse2D circle = new Ellipse2D.Double();
            g2d.setPaint(Constants.CONTINTENT_COLORS[country.continent]);
            circle.setFrameFromCenter(
                    country.coOrds.getX(),
                    country.coOrds.getY(),
                    country.coOrds.getX() + 25,
                    country.coOrds.getY() + 25);
            g2d.fill(circle);
        }
    }

    //draw links between countries
    private void drawLinks(Graphics2D g2d, ArrayList<Country> countries) {
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));

        for (Country country : countries) {
            for (int i = 0; i < country.adjacents.length; i++) {
                Line2D link = new Line2D.Double();
                Country otherCountry = countries.get(country.adjacents[i]);

                if (country.name == "Alaska" && otherCountry.name == "Kamchatka") {
                    link.setLine(
                            country.coOrds.getX(),
                            country.coOrds.getY(),
                            0,
                            otherCountry.coOrds.getY()
                    );
                } else if (country.name == "Kamchatka" && otherCountry.name == "Alaska") {
                    link.setLine(
                            country.coOrds.getX(),
                            country.coOrds.getY(),
                            1000,
                            otherCountry.coOrds.getY()
                    );
                } else {
                    link.setLine(
                            country.coOrds,
                            otherCountry.coOrds
                    );
                }

                g2d.draw(link);
            }
        }
    }



}
