package engine.windows.node;

import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Block extends GameObject{
    private int type;

    public static Block prototype() {
        return new Block(new Position(0,0),1);
    }
    public Block(Position position, int type){
        super(position);
        this.type = type;
        try {
            switch (type){
                case 1:
                    this.image = ImageIO.read(new File("Resources/black1_t.png"));
                    break;
                case 2:
                    this.image = ImageIO.read(new File("Resources/black2_t.png"));
                    break;
                case 3:
                    this.image = ImageIO.read(new File("Resources/black3_t.png"));
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void collideWith(GameObject target) {
        if(target instanceof Ball) this.destroyGameObject();
    }
}
