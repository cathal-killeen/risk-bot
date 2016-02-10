
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public static CommandPrompt commandPrompt = new CommandPrompt();

    public SideBar(){
        super();
        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        add(commandPrompt, BorderLayout.SOUTH);
    }
}

class CommandPrompt extends JPanel{
    public CommandPrompt(){
        setLayout(new BorderLayout());
        JLabel promptLabel = new JLabel("Welcome to RISK!");
        promptLabel.setBorder(new EmptyBorder(10,10,10,10));
        promptLabel.setBackground(Color.GRAY);
        promptLabel.setOpaque(true);
        JTextField commandField = new JTextField("Enter command here", 10);


        add(promptLabel, BorderLayout.NORTH);
        add(commandField, BorderLayout.SOUTH);
    }
}