package engine.windows.node;

import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Heart extends GameObject{
    public static Heart prototype() {
        return new Heart(new Position(0,0));
    }
    public Heart(Position position) {
        super(position);
        this.setCollidable(false);
        try {
            this.image = ImageIO.read(new File("Resources/item3_t.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void collideWith(GameObject target) {

    }
}
