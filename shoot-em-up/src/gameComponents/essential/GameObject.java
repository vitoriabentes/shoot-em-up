package gameComponents.essential;

import java.awt.Color;

public class GameObject {
    private double coordinateX;
    private double coordinateY;
    private Color color;

    public GameObject(double coordinateX, double coordinateY, Color color) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.color = color;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
