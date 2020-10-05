package tankgame.gameobjects;

import tankgame.GameConstants;

public class Camera {
    int cameraX, cameraY;

    public Camera(Tank tank) {
        setCamera(tank);
    }

    // Sets a camera object follow the tank
    public void setCamera(Tank tank) {
        if (tank.getX() - GameConstants.GAME_SCREEN_WIDTH / 4 <= 0) {
            this.cameraX = 0;
        }
        else if (tank.getX() - GameConstants.GAME_SCREEN_WIDTH / 4 >= GameConstants.GAME_SCREEN_WIDTH + 474) {
            this.cameraX = GameConstants.GAME_SCREEN_WIDTH + 474;
        }
        else {
            this.cameraX = tank.getX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        }

        if (tank.getY() - (GameConstants.WORLD_HEIGHT / 4) + 100 <= 0) {
            this.cameraY = 0;
        }
        else if (tank.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2 >= GameConstants.GAME_SCREEN_HEIGHT + 474) { // 474
            this.cameraY = GameConstants.GAME_SCREEN_HEIGHT + 474;
        }
        else {
            this.cameraY = tank.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }
}