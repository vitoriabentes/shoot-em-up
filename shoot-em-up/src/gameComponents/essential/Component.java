package gameComponents.essential;

import graphics.Util;

import java.awt.Color;

public class Component extends GameObject {
    // Attributes
    private int state;
    private double speedX;
    private double speedY;
    private double radius;

    // Constructor
    public Component(int state, double coordinateX, double coordinateY, double speedX, double speedY, double radius, Color color) {
        super(coordinateX, coordinateY, color);
        this.radius = radius;
        this.state = state;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public int getState() {
        return state;
    }

    public boolean isActive(){
        return state == Util.ACTIVE;
    }
    public boolean isInactive(){
        return state == Util.INACTIVE;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}