



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

    private int xx, yy;
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

                    if (country.getMapNode().contains(me.getPoint())) {//check if mouse is clicked within shape
                        //or check the shape class we are dealing with using instance of with nested if
                        if (country.getMapNode() instanceof Ellipse2D) {
                            JOptionPane.showMessageDialog(null, "Owned by " + country.getOwner().name, country.getName(), JOptionPane.INFORMATION_MESSAGE);
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
            if(country.getOwner() != null){
                Ellipse2D circle = new Ellipse2D.Double();
                g2d.setPaint(country.getOwner().color);
                circle.setFrameFromCenter(
                        country.getCoOrds().getX(),
                        country.getCoOrds().getY(),
                        country.getCoOrds().getX() + 15,
                        country.getCoOrds().getY() + 15);
                xx = (int)country.getCoOrds().getX();
                yy = (int)country.getCoOrds().getY();

                g2d.drawString(country.getName(), xx - 20, yy - 20);
                g2d.fill(circle);
				
				//Display Troop Counts
                g2d.setPaint(Color.WHITE);
                g2d.drawString(Integer.toString(country.getTroopCount()), xx-5, yy+5);
            }
        }
    }

    //draw country nodes with continent colors
    private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
        for(Country country: countries){
            g2d.setPaint(Constants.CONTINTENT_COLORS[country.getContinent()]);
            g2d.fill(country.getMapNode());
        }

        nodesPainted = true;
    }

    //draw links between countries
    private void drawLinks(Graphics2D g2d, ArrayList<Country> countries) {
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));

        for (Country country : countries) {
            for (int i = 0; i < country.getAdjacents().length; i++) {
                Line2D link = new Line2D.Double();
                Country otherCountry = countries.get(country.getAdjacents()[i]);

                if (country.getName().equals("Alaska") && otherCountry.getName().equals("Kamchatka")) {
                    link.setLine(
                            country.getCoOrds().getX(),
                            country.getCoOrds().getY(),
                            0,
                            otherCountry.getCoOrds().getY()
                    );
                } else if (country.getName().equals("Kamchatka") && otherCountry.getName().equals("Alaska")) {
                    link.setLine(
                            country.getCoOrds().getX(),
                            country.getCoOrds().getY(),
                            1000,
                            otherCountry.getCoOrds().getY()
                    );
                } else {
                    link.setLine(
                            country.getCoOrds(),
                            otherCountry.getCoOrds()
                    );
                }

                g2d.draw(link);
            }
        }

        linksPainted = true;
    }

}



