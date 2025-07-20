package gameComponents.character;

import gameComponents.essential.Component;
import graphics.Util;

import java.awt.*;

public class Projectile extends Component {
    // Constructor
    public Projectile(double coordinateX, double coordinateY, double speedX, double speedY, double radius, Color color) {
        super(Util.ACTIVE, coordinateX, coordinateY, speedX, speedY, radius, color);
    }
}