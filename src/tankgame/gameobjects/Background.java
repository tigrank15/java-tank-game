package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends Stationary {

    public Background(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.getImg(), super.getX(), super.getY(), null);
    }

}