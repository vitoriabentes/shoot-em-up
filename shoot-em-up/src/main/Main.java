package main;

import gameComponents.character.PowerUp;
import gameComponents.character.enemies.EnemyTypeThree;
import gameComponents.scenario.BackgroundStars;

import java.awt.Color;

import gameComponents.character.enemies.EnemiesArmy;
import gameComponents.character.enemies.EnemyTypeOne;
import gameComponents.character.enemies.EnemyTypeTwo;
import gameComponents.character.Player;
import gameComponents.scenario.LifeBar;
import gameComponents.scenario.Message;
import graphics.Util;
import graphics.GameLib;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void busyWait(Instant endTime) {
        while (Instant.now().isBefore(endTime)) {
            Thread.yield();
        }
    }

    public static void main(String[] args) {

        var currentTime = Instant.now();
        boolean running = true;
        long delta;

        Player player = new Player(currentTime);

        ArrayList<EnemiesArmy> armys = new ArrayList<>();

        armys.add(new EnemiesArmy(Util.ENEMY_QUANTITY, EnemyTypeOne.class, currentTime, 2000));
        armys.add(new EnemiesArmy(Util.ENEMY_QUANTITY, EnemyTypeTwo.class, currentTime, 7000));
        armys.add(new EnemiesArmy(Util.ENEMY3_MAX_ACTIVE, EnemyTypeThree.class, currentTime, 2000));

        BackgroundStars starsFirst = new BackgroundStars(0.07, 0.0, 20, Color.GRAY);
        BackgroundStars starsSecond = new BackgroundStars(0.045, 0.0, 50, Color.DARK_GRAY);

        LifeBar lifeBar = new LifeBar(player.getLife());
        Message gameOver = new Message("Game Over");
        PowerUp powerUp = new PowerUp();

        GameLib.initGraphics();

        while (running) {
            if (GameLib.isKeyPressed(Util.KEY_ESCAPE)) running = false;

            delta = Duration.between(currentTime, Instant.now()).toMillis();
            currentTime = Instant.now();

            for (EnemiesArmy army : armys) {
                player.checkCollisions(army, currentTime);
                army.checkCollisions(player, currentTime);
                army.cleanInactive();
                army.updateProjectiles(delta);
                army.atack(player, currentTime, delta);
                army.castEnemies(currentTime);
                army.drawEnemies(currentTime);
            }

            player.updateProjectiles(delta);
            player.setInactive(currentTime);
            player.verifyActions(currentTime, delta);
            player.keepInScren();
            player.draw(currentTime);

            powerUp.activate(currentTime, delta);
            powerUp.checkCollision(player);
            powerUp.draw();
            player.updateImmunity(currentTime);

            starsSecond.update(delta);
            starsFirst.update(delta);
            lifeBar.update(player.getLife());
            gameOver.update(player);



            GameLib.display();
            busyWait(currentTime.plusMillis(5));
        }

    }
}