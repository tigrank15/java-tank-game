package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private int x, y;
    private BufferedImage img;
    private Rectangle hitBox;

    public GameObject (int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public abstract void drawImage(Graphics g);
    public abstract void update();

}