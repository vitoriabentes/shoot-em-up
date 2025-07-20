package gameComponents.scenario;

import gameComponents.essential.GameObject;

import java.awt.Color;

public class Star extends GameObject {
    private final double speed;

    public Star(double coordinateX, double coordinateY, double speed, Color color) {
        super(coordinateX, coordinateY, color);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

}
