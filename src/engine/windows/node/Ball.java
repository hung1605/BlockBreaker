package engine.windows.node;

import engine.windows.common.Position;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ball extends GameObject {
    private int angle;
    private boolean ismove;
    private int speed;

    public Ball(Position position, int speed) {
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/58-Breakout-Tiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.angle = 170;
        this.ismove = false;
        this.speed = speed;
    }

    @Override
    public void collideWith(GameObject target) {
        bounce(target);
    }

    public void bounce(GameObject gameObject) {
        /**
         * angle
         * x,y,w,h ball
         * x,y,w,h gameObject
         */
        Random r = new Random();

        if (position.x + image.getWidth() >= gameObject.getPosition().x
                && position.x + image.getWidth() <= gameObject.getPosition().x + 3
        ) {
            //LEFT
            System.out.println("LEFT");
            angle = 180 - angle + (r.nextInt() % 5);
        }
        if (position.y + image.getHeight() >= gameObject.getPosition().y
                && position.y + image.getHeight() <= gameObject.getPosition().y + 3
        ) {
            //UP
            System.out.println("UP");
            angle = 360 - angle + (r.nextInt() % 5);
        }
        if (position.x >= gameObject.getPosition().x + gameObject.getWidth() - 3
                && position.x <= gameObject.getPosition().x + gameObject.getWidth()
        ) {
            //RIGHT
            System.out.println("RIGHT");
            angle = 180 - angle + (r.nextInt() % 5);
        }
        if (position.y >= gameObject.getPosition().y + gameObject.getHeight() - 3
                && position.y <= gameObject.getPosition().y + gameObject.getHeight()
        ) {
            //DOWN
            System.out.println("DOWN");
            angle = 360 - angle + (r.nextInt() % 5);
        }
    }

    public void setIsMove(boolean ismove) {
        this.ismove = ismove;
    }

    public void fly() {
        this.position.x += speed * Math.cos(degToRad(angle));
        this.position.y -= speed * Math.sin(degToRad(angle));
    }

    @Override
    public void update() {
        if (ismove) fly();
    }

    private float degToRad(int deg) {
        return (float) (deg * Math.PI / 180);
    }
}
