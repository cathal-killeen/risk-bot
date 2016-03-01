import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.FormView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Map extends JPanel{
	public PlayerNamesBar PlayerNamesBar;

	public Map(){
		super();
		setPreferredSize(Constants.MAP_DIM);
		setOpaque(true);
		PlayerNamesBar = new PlayerNamesBar(new GridLayout());
		add(PlayerNamesBar);
		PlayerNamesBar.setLocation(100,10);

        addMouseListener(myMouseAdapter);
        addMouseMotionListener(mouseMotionListener);
	}

    private MouseAdapter myMouseAdapter = new MouseAdapter() {

        public void mouseClicked(MouseEvent e){
            for(Country country: Country.countries){
                if(country.getMapNode().contains(e.getX(), e.getY())){
                    Main.GameFrame.SideBar.CommandField.setText(country.getName());
                }
            }
        }
    };

    private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            for (Country country : Country.countries) {
                if (country.getMapNode().contains(e.getX(), e.getY())) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                if (!country.getMapNode().contains(e.getX(), e.getY())){
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }
    };

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setOpaque(true);
		Graphics2D g2d = (Graphics2D)g;
		drawBackground(g2d);
		drawNodeLinks(g2d, Country.countries);
		drawCountryNodes(g2d, Country.countries);
		if(Player.players.size() != 0){
			drawOwnerNodes(g2d, Country.countries);
		}
	}

	private void drawBackground(Graphics2D g2d){
		//read in image file.
		Boolean gotImage = false;
		BufferedImage buffBackgroundImage = null;
		try {
			buffBackgroundImage = ImageIO.read(new File("./src/images/map_grey.jpg"));
			gotImage = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		//scale image to fit coordinates of nodes
		if(gotImage){
			Image backgroundImage = buffBackgroundImage;
			backgroundImage = backgroundImage.getScaledInstance(1100, 600, Image.SCALE_DEFAULT);

			g2d.drawImage(backgroundImage, 0, 0, null);
		}else{
			System.out.println("Error getting image");
		}

	}

	private void drawCountryNodes(Graphics2D g2d, ArrayList<Country> countries){
		for(Country country: countries){
			g2d.setColor(Constants.CONTINTENT_COLORS[country.getContinent()]);
            g2d.fill(country.getMapNode());
			int x = (int)country.getCoOrds().getX();
			int y = (int)country.getCoOrds().getY();

			g2d.setPaint(Color.DARK_GRAY);
			g2d.drawString(country.getName(), x-20, y-20);
		}
	}

	private void drawOwnerNodes(Graphics2D g2d, ArrayList<Country> countries){

		for (Country country: countries){
			if(country.hasOwner()) {
				g2d.setColor(country.getOwner().color);
				g2d.fill(country.getOwnershipNode());
				int x = (int) country.getCoOrds().getX();
				int y = (int) country.getCoOrds().getY();
				g2d.setPaint(Color.WHITE);
				g2d.drawString(Integer.toString(country.getTroopCount()), x - 5, y + 5);
			}
		}

	}
	private void drawNodeLinks(Graphics2D g2d, ArrayList<Country> countries) {
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
	}
}

class PlayerNamesBar extends JPanel{
	public PlayerNamesBar(GridLayout gridLayout) {
        super(gridLayout);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(750, 22));
		setBackground(Color.WHITE);
	}

	public void putPlayerNames(){
		int labelWidth = (750/Player.players.size());

		this.removeAll();
		for(Player player: Player.players){
			JLabel nameLabel = new JLabel(player.name, SwingConstants.CENTER);
			nameLabel.setMinimumSize(new Dimension(labelWidth, 15));
			nameLabel.setMaximumSize(new Dimension(labelWidth, 15));
			nameLabel.setForeground(player.color);
			nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			add(nameLabel);

			nameLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					String territories = "OWNED COUNTRIES:\n";
					for(Country country: Player.players.get(player.index).getOwnedTerritories()){
						territories += country.getName() + "\n";
					}
					JOptionPane.showMessageDialog(null, territories, player.name, JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}

		setVisible(true);
		revalidate();
	}
}