package tankgame.gameobjects;

import java.awt.image.BufferedImage;

public abstract class Moveable extends GameObject {
    private int vx, vy, angle, speed;

    public abstract void checkBorder();
    public abstract void moveForwards();
    public abstract void moveBackwards();

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Moveable (int x, int y, BufferedImage img, int vx, int vy, int angle, int speed) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.speed = speed;
    }

}