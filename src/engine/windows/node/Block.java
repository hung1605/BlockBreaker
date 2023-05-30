package engine.windows.node;

import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Block extends GameObject{
    private int health;
    public Block(Position position){
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/11-Breakout-Tiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void collideWith(GameObject target) {
        if(target instanceof Ball) this.destroyGameObject();
    }
}
