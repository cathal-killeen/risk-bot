import java.awt.*;

/**
 * Created by Cathal on 07/02/16.
 */

//ABSTRACT ClASS - players will be constructed differently depending on if they are human or neutral
public abstract class Player {
    public String name;
    public Color color;

    public void setName(String name){this.name = name;}


    public void allocateTerritory(Country country){
        country.setOwner(this);
    }
}


