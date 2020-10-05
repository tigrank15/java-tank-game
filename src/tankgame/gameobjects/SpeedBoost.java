package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp {
    private int tankSpeed = 7;

    public SpeedBoost (int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void activate(Tank tank) {
        tank.setSpeed(this.tankSpeed);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(super.getImg(), getX(), getY(), null);
    }
}