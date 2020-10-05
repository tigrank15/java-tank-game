package tankgame.gameobjects;

import tankgame.TankGameDriver;

import java.awt.*;

public class CollisionDetector {

    public CollisionDetector(Tank tank1, Tank tank2) {

    }

    // Collision of two tanks
    public void TankVSTank(Tank tank1, Tank tank2) {
        if (tank1.getHitBox().intersects(tank2.getHitBox())) {
            stopTank(tank1, tank2);
            stopTank(tank2, tank1);
        }
    }

    // Collision of a bullet and a tank
    public void BulletVSTank(Tank tank1, Tank tank2) {
        for (int i = 0; i < Tank.getAmmo().size(); i++) {
            if (Tank.getAmmo().get(i).getHitBox().intersects(tank1.getHitBox())) {
                healthCheck(tank1, Tank.getAmmo().get(i), 200, 200);
//                System.out.println("Tank 1 health: " + tank1.getHealth() + "\tLives: " + tank1.getLives());
            } else if (Tank.getAmmo().get(i).getHitBox().intersects(tank2.getHitBox())) {
                healthCheck(tank2, Tank.getAmmo().get(i), 1800, 1800);
//                System.out.println("Tank 2 health: " + tank2.getHealth() + "\tLives: " + tank2.getLives());
            }
        }
    }

    // Utility method for BulletVSTank collision
    private void healthCheck(Tank tank, Bullet bullet, int newX, int newY) {
        if (!tank.isDead()) {
            if (tank.getHealth() > 0) {
                tank.setHealth(tank.getHealth() - bullet.getDamage());
            } else if (tank.getHealth() == 0) {
                tank.setLives(tank.getLives() - 1);
                tank.setHealth(100);
            }
        }
        Tank.getAmmo().remove(Tank.getAmmo().size() - 1);
    }

    // Collision of a bullet and a wall
    public void BulletVSWall(Tank tank1, Tank tank2) {
        for (int i = 0; i < TankGameDriver.getGameObjects().size(); i++) {
            if (TankGameDriver.getGameObjects().get(i) instanceof BreakableWall) {
                if (!Tank.getAmmo().isEmpty() && Tank.getAmmo().get(Tank.getAmmo().size() - 1).getHitBox().intersects(TankGameDriver.getGameObjects().get(i).getHitBox())) {
                    Tank.getAmmo().remove(Tank.getAmmo().size() - 1);
                    ((BreakableWall) TankGameDriver.getGameObjects().get(i)).setState(((BreakableWall) TankGameDriver.getGameObjects().get(i)).getState() - 1);
                    if (((BreakableWall) TankGameDriver.getGameObjects().get(i)).getState() == 0) {
                        TankGameDriver.getGameObjects().remove(i);
                    }
                }
            } else if (TankGameDriver.getGameObjects().get(i) instanceof UnbreakableWall) {
                if (!Tank.getAmmo().isEmpty() && Tank.getAmmo().get(Tank.getAmmo().size() - 1).getHitBox().intersects(TankGameDriver.getGameObjects().get(i).getHitBox())) {
                    Tank.getAmmo().remove(Tank.getAmmo().size() - 1);
                }
            }
        }
    }

    // Collision of tank and a wall
    public void TankVSWall(Tank tank1, Tank tank2) {
        for (int i = 0; i < TankGameDriver.getGameObjects().size(); i++) {
            if (TankGameDriver.getGameObjects().get(i) instanceof Wall) {
                stopTank(tank1, TankGameDriver.getGameObjects().get(i));
                stopTank(tank2, TankGameDriver.getGameObjects().get(i));
            }
        }
    }

    // Collision of a powerUp and a tank
    public void TankVSPowerUp(Tank tank1, Tank tank2) {
        for (int i = 0; i < TankGameDriver.getGameObjects().size(); i++) {
            if (TankGameDriver.getGameObjects().get(i) instanceof PowerUp) {
                if (tank1.getHitBox().intersects(TankGameDriver.getGameObjects().get(i).getHitBox())) {
                    ((SpeedBoost) TankGameDriver.getGameObjects().get(i)).activate(tank1);
                    TankGameDriver.getGameObjects().remove(TankGameDriver.getGameObjects().get(i));
                } else if (tank2.getHitBox().intersects(TankGameDriver.getGameObjects().get(i).getHitBox())) {
                    ((SpeedBoost) TankGameDriver.getGameObjects().get(i)).activate(tank2);
                    TankGameDriver.getGameObjects().remove(TankGameDriver.getGameObjects().get(i));
                }
            }
        }
    }

    // Utility method that stops the tank. It is used if either tank has hit a wall or another tank
    private void stopTank(Tank tank, GameObject gameObject) {
        if (tank.getHitBox().intersects(gameObject.getHitBox())) {
            Rectangle intersection = tank.getHitBox().intersection(gameObject.getHitBox());
            if (intersection.height > intersection.width && tank.getX() < intersection.getX()) {
                tank.setX(tank.getX() - intersection.width / 2);
            } else if (intersection.height > intersection.width && tank.getX() > gameObject.getHitBox().getX()) {
                tank.setX(tank.getX() + intersection.width / 2);
            } else if (intersection.height < intersection.width && tank.getY() < intersection.getY()) {
                tank.setY(tank.getY() - intersection.height / 2);
            } else if (intersection.height < intersection.width && tank.getY() > gameObject.getHitBox().getY()) {
                tank.setY(tank.getY() + intersection.height / 2);
            }
        }
    }


}
