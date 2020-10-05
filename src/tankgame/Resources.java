package tankgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resources {
    private static Map<String, BufferedImage> resources;

    // Load resources that will be used in the program
    static {
        Resources.resources = new HashMap<>();
        try {
            Resources.resources.put("tank1", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("tank1.png"))));
            Resources.resources.put("tank2", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("tank2.png"))));
            Resources.resources.put("breakableWall", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("breakableWall.gif"))));
            Resources.resources.put("unbreakableWall", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("unbreakableWall.gif"))));
            Resources.resources.put("floor", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("Background.bmp"))));
            Resources.resources.put("speedBoost", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("tank_arrowEmpty.png"))));
            Resources.resources.put("bullet2", read(Objects.requireNonNull(TankGameDriver.class.getClassLoader().getResource("bulletNew.png"))));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImage(String key) {
        return Resources.resources.get(key);
    }

}