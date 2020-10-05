package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(int x, int y, BufferedImage wallImage) {
        super(x, y, wallImage);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(super.getImg(), getX(), getY(), null);
    }

}