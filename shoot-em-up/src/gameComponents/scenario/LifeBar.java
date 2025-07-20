package gameComponents.scenario;

import gameComponents.essential.GameObject;
import graphics.GameLib;
import graphics.Util;

import java.awt.Color;

public class LifeBar extends GameObject {
    private int height = 10;
    private int width = (Util.WIDTH * 2);
    int maxSize = 0;

    public LifeBar(int maxLife) {
        super(0, 710, Color.magenta);
        maxSize = maxLife;
    }

    public void update(int life){
        GameLib.setColor(Color.black);
        GameLib.fillRect(this.getCoordinateX(), this.getCoordinateY(), this.width, this.height);
        GameLib.setColor(this.getColor());
        GameLib.fillRect(this.getCoordinateX(), this.getCoordinateY(), (this.width/maxSize * life), this.height );
    }
}
