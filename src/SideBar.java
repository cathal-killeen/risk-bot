
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public JPanel commandPrompt;
    public JPanel commandLog;
    public JPanel playerNamesPanel;

    public SideBar(){
        super();
        commandPrompt = new CommandPrompt();
        playerNamesPanel = new PlayerNamesPanel();
        commandLog = new CommandLog();

        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        add(playerNamesPanel, BorderLayout.NORTH);
        add(commandLog, BorderLayout.CENTER);
        add(commandPrompt, BorderLayout.SOUTH);
    }
}

class CommandLog extends JPanel{
    public static JTextArea log;

    public CommandLog() {
        setLayout(new BorderLayout());
        log = new JTextArea();
        log.setEditable(false);
        log.setLineWrap(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(log, BorderLayout.SOUTH);
        JScrollPane scroll = new JScrollPane(panel);
        add(scroll, BorderLayout.CENTER);
    }
}

// Command prompt class contains:
//     - commandField: to take user input
class CommandPrompt extends JPanel{
    public JPanel commandWrapper;
    public JTextField commandField;
    public JButton sendButton;
    public JTextArea chatBox;


    public CommandPrompt(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300,25));
        commandField = new JTextField("Enter command here", 20);
        commandField.addActionListener(enterKeyPressed);
        sendButton = new JButton("Enter");
        sendButton.addActionListener(enterKeyPressed);


        add(commandField, BorderLayout.WEST);
        add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatBox), BorderLayout.CENTER);
    }

    //listener for when user presses enter key in commandField
    private Action enterKeyPressed = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            CommandLog.log.append(commandField.getText() + "\n");
            commandField.setText("");
            System.out.println(commandField.getText());
        }
    };
}

class PlayerNamesPanel extends JPanel{
    public PlayerNamesPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 220));
        setBackground(Color.LIGHT_GRAY);

        for(Player player: Main.players){
            JLabel nameLabel = new JLabel(player.name);
            nameLabel.setForeground(player.color);
            nameLabel.setBorder(new EmptyBorder(10,10,10,10));
            nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(nameLabel);

            nameLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    // you can open a new frame here as
                    // i have assumed you have declared "frame" as instance variable
                    String territories = "OWNED COUNTRIES:\n";
                    for(Country country: Main.players.get(player.index).getOwnedTerritories()){
                        territories += country.getName() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, territories, player.name, JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }



}