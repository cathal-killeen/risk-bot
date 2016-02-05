import com.sun.xml.internal.ws.resources.DispatchMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Cathal on 05/02/16.
 */
public class GameFrame {
    private static Dimension frameDim = new Dimension(1000, 900);
    private static Dimension mapDim = new Dimension(1000, 600);
    private static Dimension bottomDim = new Dimension(1000, 300);
    private static Dimension bottomPanelDim = new Dimension(500, 300);

    private JFrame frame;           // main window frame for
    public JPanel mapPanel;         // panel for displaying map
    public JPanel logPanel;         //
    public JPanel commandPanel;

    public GameFrame(){
        initJFrame();

        JPanel mainContainer = createContainer(frameDim, false);
        JPanel bottomContainer = createContainer(bottomDim, true);

        mapPanel = createPanel(mapDim, Color.blue);
        logPanel = createPanel(bottomPanelDim, Color.red);
        commandPanel = createPanel(bottomPanelDim, Color.green);

        mapPanel.add(new JLabel("Map panel"));
        logPanel.add(new JLabel("Log panel"));
        commandPanel.add(new JLabel("Command panel"));

        bottomContainer.add(logPanel);
        bottomContainer.add(commandPanel);

        mainContainer.add(mapPanel);
        mainContainer.add(bottomContainer);

        frame.add(mainContainer, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    // initialise main Jframe
    private void initJFrame(){
        frame = new JFrame("RISK");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(1000, 900);
        frame.setMaximumSize(frameDim);
        frame.setMinimumSize(frameDim);
        frame.setLayout(new BorderLayout());
    }

    //create a content panel
    private JPanel createPanel(Dimension d, Color color){
        JPanel p = new JPanel();
        p.setPreferredSize(d);
        p.setOpaque(true);
        p.setBackground(color);

        return p;
    }

    //create a JPanel container. x_axis box layout = true, y_axis box layout = false;
    private JPanel createContainer(Dimension d, boolean XY){
        JPanel c = new JPanel();
        int axis;
        if(XY){
            axis = BoxLayout.X_AXIS;
        }else{
            axis = BoxLayout.Y_AXIS;
        }
        c.setPreferredSize(d);
        c.setLayout(new BoxLayout(c, axis));


        return c;
    }


}
