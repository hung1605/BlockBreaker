package engine.windows.node;

import engine.windows.GameWindows;
import engine.windows.common.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{
    int width;
    int height;
    public Wall(Position position, int witdh, int height){
        super(position);
        this.width = witdh;
        this.height = height;
        this.image = new BufferedImage(witdh, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void collideWith(GameObject target) {
    }
}

