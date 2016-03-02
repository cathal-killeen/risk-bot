import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;


/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public static JScrollPane ChatBoxScrollPane;
    public static JTextPane ChatBox = new JTextPane();
    public static JTextField CommandField = new JTextField();
    public static StyledDocument styledDoc = ChatBox.getStyledDocument();

    public LinkedList<String> commandBuffer = new LinkedList<>();

    //all styles
    static Style error;
    static Style prompt;
    static Style userInput;
    static Style info;

    public SideBar() {
        super();
        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        initStyles();

        ChatBox.setEditable(false);
        ChatBoxScrollPane = new JScrollPane(ChatBox);
        ChatBoxScrollPane.scrollRectToVisible(new Rectangle(0,getBounds(null).height,1,1));
        ChatBoxScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ChatBoxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        CommandField.addActionListener(enterKeyPressed);

        add(ChatBoxScrollPane, BorderLayout.CENTER);
        add(CommandField, BorderLayout.SOUTH);
    }

    public void initStyles(){
        error = ChatBox.addStyle("error",null);
        prompt = ChatBox.addStyle("prompt",null);
        userInput = ChatBox.addStyle("userInput", null);
        info = ChatBox.addStyle("info", null);

        StyleConstants.setForeground(error, Color.RED);
        StyleConstants.setForeground(prompt, new Color(0, 77, 0));
        StyleConstants.setForeground(userInput, new Color(115, 115, 115));
        StyleConstants.setForeground(info, Color.BLUE);
    }

    public static void log(String message, Style style){
        try {
            message += "\n";
            styledDoc.insertString(styledDoc.getLength(), message, style);
            ChatBox.setCaretPosition(ChatBox.getCaretPosition() + message.length());
        }
        catch (BadLocationException ex){}
    }

    //listener for when user presses enter key in CommandField
    private Action enterKeyPressed = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            synchronized (commandBuffer){
                String command = CommandField.getText();
                //log(command, error);

                commandBuffer.add(command);
                CommandField.setText("");
                commandBuffer.notify();
            }
        }
    };

    public String getCommand(){
        String command;
        synchronized (commandBuffer){
            while(commandBuffer.isEmpty()){
                try{
                    commandBuffer.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            command = commandBuffer.pop();
        }
        return command;
    }


}