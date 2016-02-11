
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public static CommandPrompt commandPrompt = new CommandPrompt();

    public SideBar(){
        super();
        JPanel playerNamesPanel = new PlayerNamesPanel();

        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        add(playerNamesPanel, BorderLayout.NORTH);
        add(commandPrompt, BorderLayout.SOUTH);
    }
}

// Command prompt class contains:
//      - promptLabel: to prompt user what to do next
//      - commandField: to take user input
class CommandPrompt extends JPanel{
    public static JLabel promptLabel = new JLabel("Welcome to RISK!");
    public static JTextField commandField = new JTextField("Enter command here", 10);

    public CommandPrompt(){
        setLayout(new BorderLayout());

        //Prompt label - tells user current state/prompts what to do
        promptLabel.setBorder(new EmptyBorder(10,10,10,10));
        promptLabel.setBackground(Color.GRAY);
        promptLabel.setOpaque(true);

        //prompt text field - allows user input
        commandField.addActionListener(enterKeyPressed);


        add(promptLabel, BorderLayout.NORTH);
        add(commandField, BorderLayout.SOUTH);
    }

    //listener for when user presses enter key in commandField
    private Action enterKeyPressed = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(commandField.getText());
        }
    };
}

class PlayerNamesPanel extends JPanel{
    public PlayerNamesPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(Player player: Main.players){
            JLabel nameLabel = new JLabel(player.name);
            nameLabel.setForeground(player.color);
            nameLabel.setBorder(new EmptyBorder(10,10,10,10));
            add(nameLabel);
        }
    }
}