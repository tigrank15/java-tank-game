package tankgame;

import tankgame.gameobjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TankGameDriver extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank tankOne;
    private Tank tankTwo;
    private Launcher lf;
    static long tick = 0;
    static ArrayList<GameObject> gameObjects;
    private ArrayList<Background> background;
    private CollisionDetector CD;

    public static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public TankGameDriver(Launcher lf){
        this.lf = lf;
    }

    public static long getTick() {
        return tick;
    }

    @Override
    public void run(){
        try {
            this.resetGame();
            while (true) {
                tick++;
                gameObjects.forEach(gameObject -> gameObject.update());
                this.repaint();   // redraw game

                CD.TankVSTank(tankOne, tankTwo);
                CD.BulletVSTank(tankOne, tankTwo);
                CD.BulletVSWall(tankOne, tankTwo);
                CD.TankVSWall(tankOne, tankTwo);
                CD.TankVSPowerUp(tankOne, tankTwo);
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
//                if(this.tick > 2000){
//                    this.lf.setFrame("end");
//                    return;
//                }

                // Check to see if either of the tanks are dead. If at least one is, end the game
                if (tankOne.isDead() || tankTwo.isDead()) {
                    this.resetGame();
//                   gameObjects.clear();
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.tankOne.setX(200);
        this.tankOne.setY(200);
        this.tankOne.setHealth(100);
        this.tankOne.setLives(3);
        this.tankTwo.setX(1800);
        this.tankTwo.setY(1800);
        this.tankTwo.setHealth(100);
        this.tankTwo.setLives(3);
        Tank.getAmmo().clear();
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        // Add objects to the array list of game objects
        gameObjects = new ArrayList<>();
        this.background = new ArrayList<>();
        CD = new CollisionDetector(tankOne, tankTwo);

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(TankGameDriver.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("No data found in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    Background tile = new Background((curCol * 320), (curRow * 240), Resources.getResourceImage("floor"));
                    background.add(tile);
                    switch (mapInfo[curCol]) {
                        case "2":
                            BreakableWall br = new BreakableWall(curCol * 30, curRow * 30, Resources.getResourceImage("breakableWall"));
                            gameObjects.add(br);
                            break;
                        case "4":
                            SpeedBoost speedBoost = new SpeedBoost(curCol * 30, curRow * 30, Resources.getResourceImage("speedBoost"));
                            gameObjects.add(speedBoost);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall ubr = new UnbreakableWall(curCol * 30, curRow * 30, Resources.getResourceImage("unbreakableWall"));
                            gameObjects.add(ubr);
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        // Create two tanks and initialize their control keys
        tankOne = new Tank(200, 200, Resources.getResourceImage("tank1"), 0, 0, 0, 3, 2.0f);
        TankControl tc1 = new TankControl(tankOne, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        tankTwo = new Tank(1800, 1800, Resources.getResourceImage("tank2"), 0, 0, 180, 3, 2.0f);
        TankControl tc2 = new TankControl(tankTwo, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc2);
        gameObjects.add(tankOne);
        gameObjects.add(tankTwo);
    }

    // Draw tanks, split screen, and the mini map here
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        this.background.forEach(background -> background.drawImage(buffer));
        gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));

        BufferedImage leftHalf = world.getSubimage(tankOne.getCamera().getCameraX(), tankOne.getCamera().getCameraY(), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(tankTwo.getCamera().getCameraX(), tankTwo.getCamera().getCameraY(), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage miniMap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf, (GameConstants.GAME_SCREEN_WIDTH / 2) + 5, 0, null);

        int miniMapMappedWidth = (int) (GameConstants.WORLD_WIDTH * GameConstants.SCALING_FACTOR);
        g2.drawImage(miniMap, (GameConstants.GAME_SCREEN_WIDTH / 2) - (miniMapMappedWidth / 2), GameConstants.GAME_SCREEN_HEIGHT / 2 + 90, (int)(GameConstants.WORLD_WIDTH * GameConstants.SCALING_FACTOR), (int)(GameConstants.WORLD_HEIGHT * GameConstants.SCALING_FACTOR), null);
        g2.drawImage(miniMap, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);
    }

}