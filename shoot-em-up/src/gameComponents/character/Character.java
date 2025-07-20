package gameComponents.character;

import gameComponents.essential.Component;
import graphics.GameLib;
import graphics.Util;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

public class Character extends Component {
    // Additional attributes
    private Instant explosionStart;
    private Instant explosionEnd;
    private Instant nextShoot;
    private final ArrayList<Projectile> projectiles;
    private Color colorProjectile;
    private int projectileRadius;
    private int maxProjectiles;

    public Character(int state, double coordinateX, double coordinateY, double speedX, double speedY,
                     double radius, Instant explosionStart, Instant explosionEnd, Instant nextShoot,
                     int maxProjectiles, int projectileRadius, Color colorCharacter, Color colorProjectile) {
        super(state, coordinateX, coordinateY, speedX, speedY, radius, colorCharacter);
        this.explosionStart = explosionStart;
        this.explosionEnd = explosionEnd;
        this.nextShoot = nextShoot;
        this.projectiles = new ArrayList<>();
        this.colorProjectile = colorProjectile;
        this.projectileRadius = projectileRadius;
        this.maxProjectiles = maxProjectiles;
    }


    public int getProjectileRadius(){
        return projectileRadius;
    }
    public Color getColorProjectiles(){
        return colorProjectile;
    }

    public Instant getExplosionStart() {
        return explosionStart;
    }

    public void setMaxProjectiles(int maxProjectiles) {
        this.maxProjectiles = maxProjectiles;
    }

    public void setProjectileRadius(int projectileRadius) {
        this.projectileRadius = projectileRadius;
    }

    public void setColorProjectile(Color colorProjectile) {
        this.colorProjectile = colorProjectile;
    }
    public void setExplosionStart(Instant explosionStart) {
        this.explosionStart = explosionStart;
    }

    public Instant getExplosionEnd() {
        return explosionEnd;
    }

    public void setExplosionEnd(Instant explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public Instant getNextShoot() {
        return nextShoot;
    }

    public void setNextShoot(Instant nextShoot) {
        this.nextShoot = nextShoot;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addProjectiles(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void prepareExplosion( Instant currentTime){
        setState(Util.EXPLODE);
        explosionStart = currentTime;
        explosionEnd = explosionStart.plusMillis(2000);
    }
    public void drawExplosion(Instant currentTime){
        double alpha = (double) Duration.between(getExplosionStart(), currentTime).toMillis() / Duration.between(getExplosionStart(), getExplosionEnd()).toMillis();
        GameLib.drawExplosion(getCoordinateX(), getCoordinateY(), alpha);
    }
    public void colide(Component opponent, Instant currentTime) {
        if (isInactive())
            return;

        double dx = getCoordinateX() - opponent.getCoordinateX();
        double dy = getCoordinateY() - opponent.getCoordinateY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius() + opponent.getRadius()) * 0.8) {
            prepareExplosion(currentTime);
        }
    }
    public void updateProjectiles(long delta) {
        Iterator<Projectile> iterator = this.getProjectiles().iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();

            if (projectile.getCoordinateY() < 0) {
                iterator.remove();
            } else {
                projectile.setCoordinateX(projectile.getCoordinateX() + (projectile.getSpeedX() * delta));
                projectile.setCoordinateY(projectile.getCoordinateY() + (projectile.getSpeedY() * delta));
            }
        }
    }

}