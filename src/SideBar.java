
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public JPanel commandPrompt;
    public JPanel commandLog;

    public SideBar(){
        super();
        commandPrompt = new CommandPrompt();
        commandLog = new CommandLog();

        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

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
            Main.countries.get(0).addTroops(10);
            CommandLog.log.append(commandField.getText() + "\n");
            commandField.setText("");
            System.out.println(commandField.getText());
        }
    };
}