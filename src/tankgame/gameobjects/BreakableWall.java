package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    int state = 2;

    public BreakableWall(int x, int y, BufferedImage wallImage) {
        super(x, y, wallImage);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(super.getImg(), super.getX(), super.getY(), null);
    }

}
