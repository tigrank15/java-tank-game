package tankgame.gameobjects;

import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject {
    public Stationary (int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void update() { }
}