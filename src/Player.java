import java.awt.*;

/**
 * Created by Cathal on 07/02/16.
 */
public abstract class Player {
    public String name;
    public Color color;

    public void setName(String name){this.name = name;}


    public void allocateTerritory(Country country){
        country.setOwner(this);
    }
}


