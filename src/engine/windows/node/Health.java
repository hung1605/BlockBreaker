package engine.windows.node;

import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Health extends GameObject{
    protected int lifePoint;
    public Health(Position position) {
        super(position);
        this.setCollidable(false);
        try {
            this.image = ImageIO.read(new File("Resources/60-Breakout-Tiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void collideWith(GameObject target) {

    }
}
