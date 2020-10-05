package tankgame.gameobjects;

import tankgame.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Moveable {
    int R = 7;
    private int damage = 5;

    public Bullet(int x, int y, BufferedImage img, int vx, int vy, int angle, int speed) {
        super(x, y, img, vx, vy, angle, speed);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(getX(), getY());
        rotation.rotate(Math.toRadians(getAngle()), super.getImg().getWidth() / 2.0, super.getImg().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(super.getImg(), rotation, null);
    }

    @Override
    public void update() {
        moveForwards();
        super.getHitBox().setLocation(super.getX(), super.getY());
    }

    @Override
    public void moveForwards() {
        setVx((int) Math.round(R * Math.cos(Math.toRadians(getAngle()))));
        setVy((int) Math.round(R * Math.sin(Math.toRadians(getAngle()))));
        super.setX(super.getX() + getVx());
        super.setY(super.getY() + getVy());
        checkBorder();
    }

    @Override
    public void moveBackwards() { }

    @Override
    public void checkBorder() {
        if (super.getX() < 30) {
            super.setX(30);
        }
        if (super.getX() >= GameConstants.WORLD_WIDTH - super.getImg().getWidth()) {
            super.setX(GameConstants.WORLD_WIDTH - super.getImg().getWidth());
        }
        if (super.getY() < 30) {
            super.setY(30);
        }
        if (super.getY() >= GameConstants.WORLD_HEIGHT - super.getImg().getHeight()) {
            super.setY(GameConstants.WORLD_HEIGHT - super.getImg().getHeight());
        }
    }


}