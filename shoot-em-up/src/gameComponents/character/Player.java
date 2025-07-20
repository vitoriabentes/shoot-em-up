package gameComponents.character;

import gameComponents.character.enemies.EnemiesArmy;
import gameComponents.character.enemies.Enemy;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;
import java.time.Instant;
import java.util.Scanner;

public class Player extends Character {
    private int life;
    private int maxLife;
    private boolean immune;
    private Instant immunityEndTime;

    public Player(Instant currentTime) {
        super(Util.ACTIVE, (double) Util.WIDTH / 2,
                Util.HEIGHT * 0.90, 0.25, 0.25, 12.0, null, null,
                currentTime, Util.PROJECTILE_QUANTITY, 0, Color.BLUE, Color.GREEN);
        life = lifeFromComamndLine();
        this.immune = false;
    }

    public void initialPosition(Instant currentTime){
        this.setState(Util.ACTIVE);
        this.setCoordinateX((double) Util.WIDTH / 2);
        this.setCoordinateY(Util.HEIGHT * 0.90);
        this.setSpeedX(0.25);
        this.setSpeedY(0.25);
        this.setRadius(12.0);
        this.setExplosionStart(null);
        this.setExplosionEnd(null);
        this.setNextShoot(currentTime);
        this.setMaxProjectiles(Util.PROJECTILE_QUANTITY);
        this.setProjectileRadius(0);  // Defina o valor correto
        this.setColor(Color.BLUE);
        this.setColorProjectile(Color.GREEN);
        this.immune = false;
    }

    @Override
    public void prepareExplosion(Instant currentTime) {
        if (!immune) {
            life--;
            this.setColor(Color.WHITE);
            if (life == 0) {
                super.prepareExplosion(currentTime);
            }
        }
    }

    // Verificando se a explosão do player já acabou. Ao final da explosão, o player se torna inativo
    public void setInactive(Instant currentTime) {
        if (getState() == Util.EXPLODE && currentTime.isAfter(getExplosionEnd())) {
            setState(Util.INACTIVE);
        }
    }

    //Verificação se as coordenadas do player estão dentro da tela
    public void keepInScren() {
        if (this.getCoordinateX() < 0) this.setCoordinateX(0);
        if (this.getCoordinateX() >= Util.WIDTH) this.setCoordinateX(Util.WIDTH - 1);
        if (this.getCoordinateY() < 25) this.setCoordinateY(25);
        if (this.getCoordinateY() >= Util.HEIGHT) this.setCoordinateY(Util.HEIGHT - 1);
    }

    public void atack(Instant currentTime) {
        Projectile projectile = new Projectile(getCoordinateX(), getCoordinateY() - 2 * getRadius(), 0, -1.0, getRadius(), getColor());
        addProjectiles(projectile);
        setNextShoot(currentTime.plusMillis(100));
    }

    public void backToLife(Instant currentTime) {
        setLife(this.maxLife);
        initialPosition(currentTime);
    }

    public void verifyActions(Instant currentTime, long delta) {
        if (isActive()) {
            if (GameLib.isKeyPressed(Util.KEY_UP))
                setCoordinateY(getCoordinateY() - delta * getSpeedY());
            if (GameLib.isKeyPressed(Util.KEY_DOWN))
                setCoordinateY(getCoordinateY() + delta * getSpeedY());
            if (GameLib.isKeyPressed(Util.KEY_LEFT))
                setCoordinateX(getCoordinateX() - delta * getSpeedX());
            if (GameLib.isKeyPressed(Util.KEY_RIGHT))
                setCoordinateX(getCoordinateX() + delta * getSpeedX());
            if (GameLib.isKeyPressed(Util.KEY_CONTROL) &&
                    currentTime.isAfter(getNextShoot())) {
                atack(currentTime);
            }
        } else if (GameLib.isKeyPressed(Util.KEY_R) && life == 0) {
            backToLife(currentTime);
        }
    }

    public void drawProjectiles() {
        for (Projectile projectile : getProjectiles()) {
            if (projectile.isActive()) {
                GameLib.setColor(projectile.getColor());
                GameLib.drawLine(projectile.getCoordinateX(), projectile.getCoordinateY() - 5, projectile.getCoordinateX(), projectile.getCoordinateY() + 5);
                GameLib.drawLine(projectile.getCoordinateX() - 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() - 1, projectile.getCoordinateY() + 3);
                GameLib.drawLine(projectile.getCoordinateX() + 1, projectile.getCoordinateY() - 3, projectile.getCoordinateX() + 1, projectile.getCoordinateY() + 3);
            }
        }
    }

    public void draw(Instant currentTime) {
        if (getState() == Util.EXPLODE) {
            drawExplosion(currentTime);
        } else if (getLife() > 0) {
            GameLib.setColor(getColor());
            GameLib.drawPlayer(getCoordinateX(), getCoordinateY(), getRadius());
            drawProjectiles();
            if (this.getColor() == Color.WHITE) {
                this.setColor(Color.blue);
            }
        }

    }

    public void checkCollisions(EnemiesArmy army, Instant currentTime) {
        if (!immune && isActive()) {
            checkCollisionsWithProjectiles(army, currentTime);
            checkCollisionsWithEnemys(army, currentTime);
        }
    }

    private void checkCollisionsWithProjectiles(EnemiesArmy army, Instant currentTime) {
        for (Enemy enemy : army.getEnemies()) {
            for (Projectile projectile : enemy.getProjectiles()) {
                colide(projectile, currentTime);
            }
        }
    }

    private void checkCollisionsWithEnemys(EnemiesArmy army, Instant currentTime) {
        for (Enemy enemy : army.getEnemies()) {
            colide(enemy, currentTime);
        }
    }

    //getter do atributo life que sera utilizado para printar a barra de vida
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int lifeFromComamndLine() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira a quantidade de vidas desejada!");
        maxLife = scanner.nextInt();
        return maxLife;
    }

    public void activateImmunity() {
        immune = true;
        immunityEndTime = Instant.now().plusSeconds(5);
    }

    public void updateImmunity(Instant currentTime) {
        if (immune && currentTime.isAfter(immunityEndTime)) {
            immune = false;
        }
    }
}

