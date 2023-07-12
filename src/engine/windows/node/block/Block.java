package engine.windows.node.block;

import engine.windows.common.Position;
import engine.windows.node.Ball;
import engine.windows.node.GameObject;
import engine.windows.node.power.IUpgrade;
import engine.windows.node.power.Power;
import engine.windows.node.scenes.PlayScene;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Block extends GameObject {
    private int health;
    PlayScene gameScene;
    private boolean breakable = true;

    public static Block prototype() {
        return new Block(new Position(0, 0), BlockType.WOOD_BLOCK, null);
    }

    public Block(Position position, BlockType type, PlayScene gameScene) {
        super(position);
        try {
            switch (type) {
                case WOOD_BLOCK:
                    this.image = ImageIO.read(new File("Resources/black3_t.png"));
                    this.health = 2;
                    break;
                case STEEL_BLOCK:
                    this.image = ImageIO.read(new File("Resources/iron-block.png"));
                    this.health = 5;
                    breakable = false;
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameScene = gameScene;
    }

    @Override
    public void collideWith(GameObject target) {
        if(!breakable) return;
        if (target instanceof Ball) {
            health--;
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
        gameScene.addGameObject(powerUp);
    }

    private Power randomPowerUps() {
        Power power = null;
        int base = new Random().nextInt(4);
        switch (base + 1){
            case 1://triple ball
                power = new Power(this.position, "Resources/item1_t.png", new IUpgrade() {
                    @Override
                    public void buff() {
                        gameScene.tripleBallUpgrade();
                    }
                });
                break;
            case 2://widen slider
                power = new Power(this.position, "Resources/item2_t.png", new IUpgrade() {
                    @Override
                    public void buff() {
                        gameScene.widenSlider();
                    }
                });
                break;
            case 3://health
                power = new Power(this.position, "Resources/item3_t.png", new IUpgrade() {
                    @Override
                    public void buff() {
                        gameScene.addHealth();
                    }
                });
                break;
            case 4://rocket
                power = new Power(this.position, "Resources/item4_t.png", new IUpgrade() {
                    @Override
                    public void buff() {
                        gameScene.tripleBallUpgrade();
                    }
                });
                break;
            default:
                break;
        }
        if(power == null) {
            System.out.println("WTF");
        }
        return power;
    }

    public boolean isBreakable() {
        return breakable;
    }
}
