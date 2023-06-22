package engine.windows.node.block;

import engine.windows.common.Position;
import engine.windows.node.Ball;
import engine.windows.node.GameObject;
import engine.windows.node.power.Power;
import engine.windows.node.scenes.Scene;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Block extends GameObject {
    private int health;
    Scene gameScene;

    public static Block prototype() {
        return new Block(new Position(0, 0), BlockType.WOOD_BLOCK, null);
    }

    public Block(Position position, BlockType type, Scene gameScene) {
        super(position);
        try {
            switch (type) {
                case WOOD_BLOCK:
                    this.image = ImageIO.read(new File("Resources/black3_t.png"));
                    this.health = 2;
                    break;
                case STEEL_BLOCK:
                    this.image = ImageIO.read(new File("Resources/iron-block.png"));
                    this.health = Integer.MAX_VALUE;
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameScene = gameScene;
    }

    @Override
    public void collideWith(GameObject target) {
        if (target instanceof Ball) {
            health--;
            System.out.println("sound effect go crack crack crack ...");
            if (health == 1) {
                try {
                    this.image = ImageIO.read(new File("Resources/black4_t.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (health == 0) {
                blockDestroy();
            }
        }
    }

    private void blockDestroy() {
        this.destroyGameObject();
        Power powerUp = randomPowerUps();
        if (powerUp != null) {
            gameScene.addGameObject(powerUp);
        }
    }

    private Power randomPowerUps() {
        return null;
    }
}
