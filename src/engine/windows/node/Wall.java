package engine.windows.node;

import engine.windows.GameWindows;
import engine.windows.common.Position;

import java.awt.image.BufferedImage;

public class Wall extends GameObject{
    public Wall(Position position, int witdh, int height){
        super(position);
        this.image = new BufferedImage(witdh, height, BufferedImage.TYPE_INT_RGB);
    }
    @Override
    public void collideWith(GameObject target) {

    }
}
