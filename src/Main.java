import javax.swing.*;
import java.awt.*;

/**
 * SuckyBeigeFish
 *  Cathal Killeen: 14300066
 *  Stan O'Neill: 14368166
 *  Adam Doran: 14325416
 */
public class Main {
    public static void main(String[] args){
        JFrame myFrame = new JFrame("RISK");
        myFrame.setSize(1200,700);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setBackground(Color.blue);
        panel2.setBackground(Color.red);

        panel1.setOpaque(true);
        panel2.setOpaque(true);

        //panel1.set[Preferred/Maximum/Minimum]Size()

        container.add(panel1);
        container.add(panel2);


        myFrame.setLayout(new BorderLayout());
        myFrame.add(container, BorderLayout.CENTER);

        myFrame.setVisible(true);



    }


}
//testtsetste