import javax.swing.*;
import java.awt.*;

/**
 * Created by Cathal on 05/02/16.
 */
public class GameFrame extends JFrame {
    public static JPanel mapPanel = new MapPanel();
    public static JPanel sideBar = new SideBar();

    public GameFrame(){
        super("RISK");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //ensure fixed size of window
        setMinimumSize(Constants.FRAME_DIM);
        setMaximumSize(Constants.FRAME_DIM);

        add(mapPanel, BorderLayout.WEST);
        add(sideBar, BorderLayout.EAST);

        // done last when everything has been setup
        setVisible(true);
    }
}
