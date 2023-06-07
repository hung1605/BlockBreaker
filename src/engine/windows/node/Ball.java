package engine.windows.node;

import engine.windows.common.Position;
import engine.windows.node.slider.Slider;

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

    public static Ball prototype() {
        return new Ball(new Position(0,0),0);
    }
    public Ball(Position position, int speed) {
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/ball.png"));
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
        if (gameObject instanceof Slider) {
            System.out.println("UP");
            angle = 360 - angle;
            afterCollision(gameObject);
            return;
        }
        if (angle >= 0 && angle <= 90) {
            if (position.x + image.getWidth() >= gameObject.getPosition().x &&
                    position.x + image.getWidth() <= gameObject.getPosition().x + speed) {
                //LEFT
                System.out.println("LEFT");
                angle = 180 - angle;
                afterCollision(gameObject);
                return;
            }

            if (position.y >= gameObject.getPosition().y + gameObject.getHeight() - speed &&
                    position.y <= gameObject.getPosition().y + gameObject.getHeight()) {
                //DOWN
                System.out.println("DOWN");
                angle = 360 - angle;
                afterCollision(gameObject);
                return;
            }
        }
        if (angle >= 90 && angle <= 180) {

            if (position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
                    position.x <= gameObject.getPosition().x + gameObject.getWidth()) {
                //RIGHT
                System.out.println("RIGHT");
                angle = 180 - angle;
                afterCollision(gameObject);
                return;
            }
            if (position.y >= gameObject.getPosition().y + gameObject.getHeight() - speed &&
                    position.y <= gameObject.getPosition().y + gameObject.getHeight()) {
                //DOWN
                System.out.println("DOWN");
                angle = 360 - angle;
                afterCollision(gameObject);
                return;
            }
        }
        if (angle >= 180 && angle <= 270) {
            if (position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
                    position.x <= gameObject.getPosition().x + gameObject.getWidth()) {
                //RIGHT
                System.out.println("RIGHT");
                angle = 180 - angle;
                afterCollision(gameObject);
                return;
            }
            if (position.y + image.getHeight() >= gameObject.getPosition().y &&
                    position.y + image.getHeight() <= gameObject.getPosition().y + speed) {
                //UP
                System.out.println("UP");
                angle = 360 - angle;
                afterCollision(gameObject);
                return;
            }
        }
        if (angle >= 270) {
            if (position.x + image.getWidth() >= gameObject.getPosition().x &&
                    position.x + image.getWidth() <= gameObject.getPosition().x + speed) {
                //LEFT
                System.out.println("LEFT");
                angle = 180 - angle;
                afterCollision(gameObject);
                return;
            }
            if (position.y + image.getHeight() >= gameObject.getPosition().y &&
                    position.y + image.getHeight() <= gameObject.getPosition().y + speed) {
                //UP
                System.out.println("UP");
                angle = 360 - angle;
                afterCollision(gameObject);
                return;
            }
        }
    }
//            if (position.x + image.getWidth() >= gameObject.getPosition().x &&
//            position.x + image.getWidth() <= gameObject.getPosition().x + speed &&
//            position.y + image.getHeight() >= gameObject.getPosition().y &&
//            position.y + image.getHeight() <= gameObject.getPosition().y + speed){
//            //LEFT UP CORNER
//            System.out.println("LEFT UP CORNER");
//            angle = angle - 180;
//        }
//        else if(position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
//                position.x <= gameObject.getPosition().x + gameObject.getWidth() &&
//                position.y + image.getHeight() >= gameObject.getPosition().y &&
//                position.y + image.getHeight() <= gameObject.getPosition().y + speed){
//            //RIGHT UP CORNER
//            System.out.println("RIGHT UP CORNER");
//            angle = angle - 180;
//        }
//        else if(position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
//                position.x <= gameObject.getPosition().x + gameObject.getWidth() &&
//                position.y >= gameObject.getPosition().y + gameObject.getHeight() - speed &&
//                position.y <= gameObject.getPosition().y + gameObject.getHeight()){
//            //RIGHT DOWN CORNER
//            System.out.println("RIGHT DOWN CORNER");
//            angle = angle + 180;
//        }
//        else if(position.x + image.getWidth() >= gameObject.getPosition().x &&
//                position.x + image.getWidth() <= gameObject.getPosition().x + speed &&
//                position.y >= gameObject.getPosition().y + gameObject.getHeight() - speed &&
//                position.y <= gameObject.getPosition().y + gameObject.getHeight()) {
//            //LEFT DOWN CORNER
//            System.out.println("LEFT DOWN CORNER");
//            angle = angle + 180;
//        }
//        else if(position.x + image.getWidth() >= gameObject.getPosition().x &&
//            position.x + image.getWidth() <= gameObject.getPosition().x + speed) {
//            //LEFT
//            System.out.println("LEFT");
//            angle = 180 - angle;
//        }
//        else if (position.y + image.getHeight() >= gameObject.getPosition().y &&
//                 position.y + image.getHeight() <= gameObject.getPosition().y + speed) {
//            //UP
//            System.out.println("UP");
//            angle = 360 - angle;
//        }
//        else if (position.x >= gameObject.getPosition().x + gameObject.getWidth() - speed &&
//                 position.x <= gameObject.getPosition().x + gameObject.getWidth()) {
//            //RIGHT
//            System.out.println("RIGHT");
//            angle = 180 - angle;
//        }
//
//        else if(position.y >= gameObject.getPosition().y + gameObject.getHeight() - speed &&
//                position.y <= gameObject.getPosition().y + gameObject.getHeight()){
//            //DOWN
//            System.out.println("DOWN");
//            angle = 360 - angle;
//        }

    private void afterCollision(GameObject gameObject) {
        this.collidable = false;
        if (gameObject instanceof Slider) {
            Slider slider = (Slider) gameObject;
            this.angle += slider.getGoLeft() * 10;
            this.angle -= slider.getGoRight() * 10;
        }
        if (angle < 0) {
            angle += 360;
        }

        if (angle >= 360) {
            angle -= 360;
        }
    }

    public void setIsMove(boolean ismove) {
        this.ismove = ismove;
    }

    public void setResetAngle() {
        this.angle = 90;
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
        if (!this.collidable) {
            currentFrame++;
            if (currentFrame == resetFrame) {
                currentFrame = 0;
                this.collidable = true;
            }
        }
    }

    private float degToRad(int deg) {
        return (float) (deg * Math.PI / 180);
    }
}
