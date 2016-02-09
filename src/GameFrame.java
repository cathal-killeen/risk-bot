import com.sun.tools.internal.jxc.ap.Const;
import com.sun.xml.internal.ws.resources.DispatchMessages;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Cathal on 05/02/16.
 */
public class GameFrame extends JFrame {
    public GameFrame(){
        super("RISK");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        //ensure fixed size of window
        setMinimumSize(Constants.FRAME_DIM);
        setMaximumSize(Constants.FRAME_DIM);
    }
}
