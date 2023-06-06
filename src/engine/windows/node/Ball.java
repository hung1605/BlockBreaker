package engine.windows.node;

import engine.windows.common.Position;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;
import java.io.File;

public class Ball extends GameObject {
    private int angle;
    private boolean ismove;
    private int speed;
    private int resetFrame = 3;
    private int currentFrame;
    public Ball(Position position, int speed) {
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/58-Breakout-Tiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.angle = Math.abs(new Random().nextInt() % 180);
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


        if (position.x + image.getWidth() >= gameObject.getPosition().x &&
            position.x + image.getWidth() <= gameObject.getPosition().x + speed) {
            //LEFT
            System.out.println("LEFT");
            angle = 180 - angle + (r.nextInt() % 180);
        }
        else if (position.y + image.getHeight() >= gameObject.getPosition().y &&
            position.y + image.getHeight() <= gameObject.getPosition().y + speed) {
            //UP
            System.out.println("UP");
            angle = 360 - angle + (r.nextInt() % 180);
        }
        else if (position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
            position.x <= gameObject.getPosition().x + gameObject.getWidth()) {
            //RIGHT
            System.out.println("RIGHT");
            angle = 180 - angle + (r.nextInt() % 180);
        }
        else {
            //DOWN
            System.out.println("DOWN");
            angle = 360 - angle + (r.nextInt() % 180);
        }
        this.collidable = false;
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
        if (ismove) {
            fly();
        }
        if(!this.collidable) {
            currentFrame++;
            if(currentFrame == resetFrame) {
                currentFrame = 0;
                this.collidable = true;
            }
        }
    }
    private float degToRad(int deg) {
        return (float) (deg * Math.PI / 180);
    }
}
