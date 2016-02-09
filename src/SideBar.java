
import javax.swing.*;
import java.awt.*;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public SideBar(){
        super();
        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        String message = "Enter commdands here";

        JTextField commandField = new JTextField("Enter command here", 10);

        add(commandField, BorderLayout.SOUTH);
    }
}