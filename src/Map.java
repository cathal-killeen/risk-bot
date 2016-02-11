// SuckyBeigeFish
// Stan O' Neill: 14368166
// Adam Doran
// Cathal Killeen

// Importing the necessary libraries to implement the graph mapping.

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class Map extends JPanel{

    ArrayList<Node> nodes;
    ArrayList<Connection> connections;

    public Map() {

        nodes = new ArrayList();
        connections = new ArrayList();

        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            nodes.add(new Node(Constants.COUNTRY_COORD[i][0],Constants.COUNTRY_COORD[i][1], Constants.COUNTRY_NAMES[i], 0));

        }
        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            int x1 = nodes.get(i).x;
            int y1 = nodes.get(i).y;

            for(int k = 0; k < Constants.ADJACENT[i].length; k++) {
                int x2 = nodes.get(Constants.ADJACENT[i][k]).x;
                int y2 = nodes.get(Constants.ADJACENT[i][k]).y;

                connections.add(new Connection(x1, y1, x2, y2));
            }
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        int r = 0;

        for(int i = 0; i < connections.size(); i++){
            g.drawLine(connections.get(i).x1+15, connections.get(i).y1+15, connections.get(i).x2+15, connections.get(i).y2+15);
        }

        for(int i = 0; i <Constants.NUM_COUNTRIES; i++){
            if(i < 9){
                g.setColor(Color.YELLOW);
            }
            if(i >= 9 && i < 16){
                g.setColor(Color.BLUE);
            }
            if(i >= 16 && i < 28){
                g.setColor(Color.GREEN);
            }
            if(i >= 28 && i < 32){
                g.setColor(Color.PINK);
            }
            if(i >= 32 && i < 36){
                g.setColor(Color.RED);
            }
            if(i >= 36 && i <42){
                g.setColor(Color.MAGENTA);
            }
            g.drawOval(nodes.get(i).x, nodes.get(i).y, 30, 30);
            g.fillOval(nodes.get(i).x, nodes.get(i).y, 30, 30);
        }
        g.setColor(Color.BLACK);
        for(int i = 0; i < Constants.NUM_COUNTRIES; i++){
            g.drawString(nodes.get(i).name, nodes.get(i).x, nodes.get(i).y);
        }
    }



    class Node{
        int x, y;
        String name;
        int armies;
        int adjacent;

        public Node(int x, int y, String name, int armies){
            this.x = x;
            this.y = y;
            this.name = name;
            this.armies = armies;
        }
    }

    class Connection{
        int x1, y1;
        int x2, y2;

        public Connection(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

}