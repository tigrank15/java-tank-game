package tankgame.gameobjects;

import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.TankGameDriver;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends Moveable {

    private final float rotationSpeed;
    private int health = 100;
    private int lives = 3;
    private static ArrayList<Bullet> ammo;
    private Camera camera;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    public Tank(int x, int y, BufferedImage img, int vx, int vy, int angle, int speed, float rotationSpeed) {
        super(x, y, img, vx, vy, angle, speed);
        this.rotationSpeed = rotationSpeed;
        this.ammo = new ArrayList<>();
        this.camera = new Camera(this);
    }

    public boolean isDead() {
        return this.lives == 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }

    public void setX(int x) {
        super.setX(x);
    }

    public void setY(int y) {
        super.setY(y);
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    @Override
    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TankGameDriver.getTick() % 20 == 0) {
            int bulletX = super.getX() + 50 * (int)Math.round(Math.cos(Math.toRadians(super.getAngle())));
            int bulletY = super.getY() + 50 * (int)Math.round(Math.sin(Math.toRadians(super.getAngle())));
            Bullet b = new Bullet(bulletX, bulletY, Resources.getResourceImage("bullet2"), getVx(), getVy(), getAngle(), getSpeed());
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
        this.camera.setCamera(this);
        super.getHitBox().setLocation(super.getX(), super.getY());
    }

    public Camera getCamera() {
        return this.camera;
    }

    private void rotateLeft() {
        this.setAngle((int)(getAngle() - this.rotationSpeed));
    }

    private void rotateRight() {
        this.setAngle((int)(getAngle() + this.rotationSpeed));
    }

    @Override
    public void moveForwards() {
        setVx((int) Math.round(getSpeed() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getSpeed() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX() + getVx());
        setY(getY() + getVy());
        checkBorder();
    }

    @Override
    public void moveBackwards() {
        setVx((int) Math.round(getSpeed() * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(getSpeed() * Math.sin(Math.toRadians(getAngle()))));
        setX(getX() - getVx());
        setY(getY() - getVy());
        checkBorder();
    }

    @Override
    public void checkBorder() {
        if (getX() < 30) {
            setX(30);
        }
        if (getX() >= GameConstants.WORLD_WIDTH - 88) {
            setX(GameConstants.WORLD_WIDTH - 88);
        }
        if (getY() < 35) {
            setY(35);
        }
        if (getY() >= GameConstants.WORLD_HEIGHT - 80) {
            setY(GameConstants.WORLD_HEIGHT - 80);
        }
    }

    @Override
    public String toString() {
        return "x=" + getX() + ", y=" + getY() + ", angle=" + getAngle();
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(getX(), getY());
        rotation.rotate(Math.toRadians(getAngle()), this.getImg().getWidth() / 2.0, this.getImg().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getImg(), rotation, null);
        for (int i = 0; i < this.ammo.size(); i++) {
            this.ammo.get(i).drawImage(g);
        }

        // Draw health bar and lives count here
        g2d.setColor(Color.GREEN);
        g2d.drawRect(getX() - this.getImg().getWidth() / 2 + 3, getY() - 40 , 100, 7);
        g2d.fillRect(getX() - this.getImg().getWidth() / 2 + 3, getY() - 40, this.health, 7); // - this.getImg().getWidth() / 2
        g2d.setColor(Color.RED);
        g2d.drawRect(getX(), getY() - 20, 7, 7);
        g2d.drawRect(getX() + this.getImg().getWidth() / 2 - 4, getY() - 20, 7, 7);
        g2d.drawRect(getX() + 2 * (this.getImg().getWidth() / 2 - 4), getY() - 20, 7, 7);
        int livesSeparation = this.getImg().getWidth() / 2 - 4;
        for (int i = 0; i < this.lives; i++) {
            g2d.fillRect(getX() + i * (livesSeparation), getY() - 20, 7, 7);
        }

    }

    public static ArrayList<Bullet> getAmmo() {
        return ammo;
    }

}
