package tankgame.gameobjects;

import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary {

    public Wall (int x, int y, BufferedImage img) {
        super(x, y, img);
    }

}