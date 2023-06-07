package engine.windows.node;

import engine.windows.GameWindows;
import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends GameObject{
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public Wall(GameWindows gameWindows, int side){
        super(new Position(-100, -100));
        switch (side){
            case UP -> {
                this.position.x = this.position.y = 0;
                this.image = new BufferedImage(gameWindows.getWidth(), gameWindows.getHeight(), BufferedImage.TYPE_INT_RGB);
            }
            case DOWN -> {
                this.position.x = 0;
                this.position.y = gameWindows.getHeight();
                this.image = new BufferedImage(gameWindows.getWidth(), gameWindows.getHeight(), BufferedImage.TYPE_INT_RGB);
            }
            case LEFT -> {
                try {
                    this.image = ImageIO.read(new File("Resources/wall-left.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.position.x = this.position.y = 0;
            }
            case RIGHT -> {
                try {
                    this.image = ImageIO.read(new File("Resources/wall-right.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.position.y = 0;
                this.position.x = gameWindows.getWidth() - this.image.getWidth();
            }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }
    @Override
    public void collideWith(GameObject target) {
    }
}

