import javax.swing.*;
import java.awt.*;

/**
 * Created by Cathal on 09/02/16.
 */
public class MapPanel extends JPanel {
    public MapPanel() {
        super();
        setPreferredSize(Constants.MAP_DIM);
        setOpaque(true);
        setBackground(Color.CYAN);

    }
}
