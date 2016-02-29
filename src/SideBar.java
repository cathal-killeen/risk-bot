
import com.sun.tools.internal.jxc.ap.Const;
import sun.tools.jconsole.BorderedComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public JScrollPane ChatBoxScrollPane;
    public JTextArea ChatBox = new JTextArea();
    public JTextField CommandField = new JTextField();

    public SideBar() {
        super();
        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        ChatBox.setEditable(false);
        ChatBox.setLineWrap(true);
        ChatBoxScrollPane = new JScrollPane(ChatBox);
        ChatBoxScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ChatBoxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        CommandField.addActionListener(enterKeyPressed);

        add(ChatBoxScrollPane, BorderLayout.CENTER);
        add(CommandField, BorderLayout.SOUTH);
    }

    //listener for when user presses enter key in CommandField
    private Action enterKeyPressed = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            Main.countries.get(0).addTroops(10);
            String command = CommandField.getText();
            ChatBox.append(command + "\n");
            CommandField.setText("");
            System.out.println(CommandField.getText());
        }
    };
}