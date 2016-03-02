import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Cathal on 05/02/16.
 */
public class GameFrame extends JFrame {

    public Map Map;
    public SideBar SideBar;

    public GameFrame(){
        super("RISK");
        Map = new Map();
        SideBar = new SideBar();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //ensure fixed size of window
        setMinimumSize(Constants.FRAME_DIM);
        setMaximumSize(Constants.FRAME_DIM);

        add(Map, BorderLayout.WEST);
        add(SideBar, BorderLayout.EAST);

        // done last when everything has been setup
        setVisible(true);
    }
}


