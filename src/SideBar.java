import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.LinkedList;


/**
 * Created by Cathal on 09/02/16.
 */
public class SideBar extends JPanel {
    public JScrollPane ChatBoxScrollPane;
    public JTextPane ChatBox = new JTextPane();
    public JTextField CommandField = new JTextField();
    public StyledDocument styledDoc = ChatBox.getStyledDocument();

    public LinkedList<String> commandBuffer = new LinkedList<>();

    //all styles
    Style error;
    Style prompt;
    Style userInput;
    Style info;

    public SideBar() {
        super();
        setPreferredSize(Constants.SIDEBAR_DIM);
        setLayout(new BorderLayout());

        initStyles();

        ChatBox.setEditable(false);
        ChatBoxScrollPane = new JScrollPane(ChatBox);
        ChatBoxScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ChatBoxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        ChatBoxScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

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

    public void log(String message, Style style){
        try { styledDoc.insertString(styledDoc.getLength(), message + "\n", style); }
        catch (BadLocationException ex){}
    }

    public void error(String message){
        log(message, error);
    }

    public void prompt(String message){
        log(message, prompt);
    }

    public void userInput(String message){
        log(message, userInput);
    }

    public void info(String message){
        log(message, info);
    }

    //listener for when user presses enter key in CommandField
    private Action enterKeyPressed = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            synchronized (commandBuffer){
                String command = CommandField.getText();

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
        log(command + "\n", userInput);
        return command;
    }


}