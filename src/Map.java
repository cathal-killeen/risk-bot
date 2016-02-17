import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Map extends JPanel{

	//private static JFrame mapPanel = null;
	private Boolean countryNodesDrawn = false;
	private Boolean ownerNodesDrawn = false;
	private Boolean backgroundDrawn = false;
	private Boolean linksDrawn = false;
	private int x, y;

	public Map(){
		super();
		setPreferredSize(Constants.MAP_DIM);
		setOpaque(true);


	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setOpaque(true);
		Graphics2D g2d = (Graphics2D)g;

		if (!backgroundDrawn){
			drawBackground(g2d);
		}
		if (!linksDrawn){
			linkNodes(g2d, Main.countries);
		}
		if (!countryNodesDrawn){
			drawCountryNodes(g2d, Main.countries);
		}
		if (!ownerNodesDrawn){
			drawOwnerNodes(g2d, Main.countries);
		}

	}

	private void drawBackground(Graphics2D g2d){
		//read in image file.
		Boolean gotImage = false;
		BufferedImage buffBackgroundImage = null;
		try {
			buffBackgroundImage = ImageIO.read(new File("./images/map_grey.jpg"));
			gotImage = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		//scale image to fit coordinates of nodes
		if(gotImage){
			Image backgroundImage = buffBackgroundImage;
			backgroundImage = backgroundImage.getScaledInstance(1100, 600, Image.SCALE_DEFAULT);

			g2d.drawImage(backgroundImage, 0, 0, null);

			backgroundDrawn = true;
		}else{
			System.out.println("Error getting image");
		}

	}

	private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
		for(Country country: countries){
			g2d.setColor(Constants.CONTINTENT_COLORS[country.getContinent()]);
			g2d.fill(country.getMapNode());
			x = (int)country.getCoOrds().getX();
			y = (int)country.getCoOrds().getY();

			g2d.setPaint(Color.BLACK);
			g2d.drawString(country.getName(), x-20, y-20);

		}
		countryNodesDrawn = true;
	}

	private void drawOwnerNodes(Graphics2D g2d, ArrayList<Country> countries){

		for (Country country: countries){
			g2d.setColor(country.getOwner().color);
			g2d.fill(country.getOwnershipNode());

			x = (int)country.getCoOrds().getX();
			y = (int)country.getCoOrds().getY();
			g2d.setPaint(Color.WHITE);
			g2d.drawString(Integer.toString(country.getTroopCount()), x-5, y+5);
		}
		ownerNodesDrawn = true;

	}
	private void linkNodes(Graphics2D g2d, ArrayList<Country> countries) {
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));

		for (Country node: countries){
			for (int i=0; i<node.getAdjacents().length; i++){
				Line2D link = new Line2D.Double();
				Country adjacentNode = countries.get(node.getAdjacents()[i]);
				if (node.getName().equals("Alaska") && adjacentNode.getName().equals("Kamchatka")){
					link.setLine(
							node.getCoOrds().getX(),
							node.getCoOrds().getY(),
							0,
							adjacentNode.getCoOrds().getY()
					);
				} else if (node.getName().equals("Kamchatka") && adjacentNode.getName().equals("Alaska")){
					link.setLine(
							node.getCoOrds().getX(),
							node.getCoOrds().getY(),
							1000,
							adjacentNode.getCoOrds().getY()
					);
				} else {
					link.setLine(
							node.getCoOrds(),
							adjacentNode.getCoOrds()
					);
				}
				g2d.draw(link);
			}
		}
		linksDrawn = true;
	}
}