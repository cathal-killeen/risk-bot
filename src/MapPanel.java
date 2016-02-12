

import com.sun.javafx.collections.ElementObservableListDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Cathal on 09/02/16.
 */
public class MapPanel extends JPanel {

    private Boolean nodesPainted = false;
    private Boolean linksPainted = false;


    public MapPanel() {
        super();
        setPreferredSize(Constants.MAP_DIM);
        setOpaque(true);
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                for (Country country: Main.countries) {

                    if (country.mapNode.contains(me.getPoint())) {//check if mouse is clicked within shape

                        //we can either just print out the object class name
                        System.out.println("Clicked a " + country.mapNode.getClass().getName());

                        //or check the shape class we are dealing with using instance of with nested if
                        if (country.mapNode instanceof Ellipse2D) {
                            JOptionPane.showMessageDialog(null, "hello", "clicked a country", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Clicked a circle");
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //todo: change cursor when hover over a node on map
            }


        });
    }


    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        if(!linksPainted){ drawLinks(g2d, Main.countries); }
        if(!nodesPainted){ drawCountryNodes(g2d, Main.countries); }
        paintOwnerNode(g2d, Main.countries);
    }

    private void paintOwnerNode(Graphics2D g2d, ArrayList<Country> countries){
        for(Country country: countries){
            if(country.owner != null){
                Ellipse2D circle = new Ellipse2D.Double();
                g2d.setPaint(country.owner.color);
                circle.setFrameFromCenter(
                        country.coOrds.getX(),
                        country.coOrds.getY(),
                        country.coOrds.getX() + 15,
                        country.coOrds.getY() + 15);
                g2d.fill(circle);
            }
        }
    }

    //draw country nodes with continent colors
    private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
        for(Country country: countries){
            g2d.setPaint(Constants.CONTINTENT_COLORS[country.continent]);
            g2d.fill(country.mapNode);
        }

        nodesPainted = true;
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

        linksPainted = true;
    }

}



