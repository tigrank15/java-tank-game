package tankgame.gameobjects;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary {

    public PowerUp (int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    public abstract void activate(Tank tank);
}