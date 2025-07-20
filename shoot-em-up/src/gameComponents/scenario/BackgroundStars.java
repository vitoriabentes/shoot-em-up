package gameComponents.scenario;

import graphics.GameLib;
import graphics.Util;

import java.awt.Color;
import java.util.ArrayList;

public class BackgroundStars {
    private final ArrayList<Star> stars;
    private double count;


    public BackgroundStars(double speed, double count, int numStars, Color color) {
        this.stars = new ArrayList<>(numStars);
        for (int i = 0; i < numStars; i++) {
            var star = new Star((Math.random() * Util.WIDTH), (Math.random() * Util.HEIGHT), speed, color);
            stars.add(star);
        }
        this.count = count;
    }

    public double getCount() {
        return count;
    }


    public void updateCount(long delta) {
        this.count += stars.getFirst().getSpeed() * delta;
    }

    public void update(long delta) {
        GameLib.setColor(stars.getFirst().getColor());
        updateCount(delta);
        for (Star star : stars) {
            GameLib.fillRect(star.getCoordinateX(), (star.getCoordinateY() + getCount()) % Util.HEIGHT, 2, 2);
        }
    }
}
