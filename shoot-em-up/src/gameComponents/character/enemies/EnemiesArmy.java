package gameComponents.character.enemies;

import gameComponents.character.Player;
import gameComponents.character.Projectile;
import gameComponents.essential.Component;

import java.lang.reflect.Constructor;
import java.time.Instant;
import java.util.ArrayList;

public class EnemiesArmy {
    private final ArrayList<Enemy> enemies;
    private Instant nextEnemy;
    private final int maxActive;
    Class<? extends Enemy> enemyClass;

    // Constructor
    public EnemiesArmy(int quantity, Class<? extends Enemy> enemyClass, Instant currentTime, int timeToFirstEnemy) {
        this.enemies = new ArrayList<>();
        this.maxActive = quantity;
        this.nextEnemy = currentTime.plusMillis(timeToFirstEnemy);
        this.enemyClass = enemyClass;
    }

    private static Enemy createEnemyFromInstance(Class<? extends Enemy> enemyClass, Instant currentTime) {
        try {
            Constructor<? extends Enemy> constructor = enemyClass.getConstructor(Instant.class);
            return constructor.newInstance(currentTime);
        } catch (Exception e) {
            System.out.println("Inimigo construido sem construtor");
            return null;
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void castEnemies(Instant currentTime) {
        if (countActiveEnemies() < maxActive  && currentTime.isAfter(nextEnemy)) {
            Enemy enemy = createEnemyFromInstance(enemyClass, currentTime);

            if(enemy == null) return; //instancia

            enemies.add(enemy);

            nextEnemy = enemy.nextCast(currentTime);
        }
    }

    public void atack(Player player, Instant currentTime, long delta) {
        for (Enemy enemy : getEnemies()) {
            enemy.attack(player, currentTime, delta);
        }
    }

    public void updateProjectiles(long delta) {
        for (Enemy enemy : getEnemies()) {
            enemy.updateProjectiles(delta);
        }
    }

    public void drawEnemies(Instant currentTime) {
        for (Enemy enemy : getEnemies()) {
            enemy.draw(currentTime);
        }
    }

    public void checkCollisions(Player player, Instant currentTime) {
        for (Projectile projectile : player.getProjectiles()) {
            for (Enemy enemy : getEnemies()) {
                enemy.colide(projectile, currentTime);
            }
        }
    }

    public int countActiveEnemies() {
        int activeCount = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                activeCount++;
            }
        }
        return activeCount;
    }

    public void cleanInactive(){
        enemies.removeIf(Component::isInactive);
    }
}