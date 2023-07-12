package engine.windows.node;

import engine.windows.GameWindows;
import engine.windows.common.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends GameObject{
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public Wall(GameWindows gameWindows, int side){
        super(new Position(0, 0));
        switch (side){
            case UP:
                this.image = new BufferedImage(gameWindows.getWidth(), 38, BufferedImage.TYPE_INT_RGB);
                this.position.x = 0;
                this.position.y = 0;
                System.out.println("build up wall");
                break;
            case DOWN:
                this.position.x = 0;
                this.position.y = gameWindows.getHeight();
                this.image = new BufferedImage(gameWindows.getWidth(), 30, BufferedImage.TYPE_INT_RGB);
                break;
            case LEFT:
                try {
                    this.image = ImageIO.read(new File("Resources/wall-left.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.position.x = this.position.y = 0;
                break;
            case RIGHT:
                try {
                    this.image = ImageIO.read(new File("Resources/wall-right.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.position.y = 0;
                this.position.x = gameWindows.getWidth() - this.image.getWidth();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + side);
        }
    }
    @Override
    public void collideWith(GameObject target) {
    }
}

