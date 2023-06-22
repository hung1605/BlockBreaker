package engine.windows.node;

import engine.windows.common.Position;
import engine.windows.node.scenes.EscScene;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;

public class Ball extends GameObject {
    private float angle;
    private boolean ismove;
    private float speed;
    private int resetFrame = 3;
    private int currentFrame;
    private boolean collided;
    public static Ball prototype() {
        return new Ball(new Position(0, 0), 0f);
    }

    public Ball(Position position, float speed) {
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/ball.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.angle = 91;
//        this.angle = Math.abs(new Random().nextInt() % 90) + 45;
        this.ismove = false;
        this.speed = speed;
    }

    @Override
    public void collideWith(GameObject target) {
        bounce(target);
    }

    void calculateBallReflectAngle(GameObject gameObject) {
//        this.collidable = false;
        List<Position> points = new ArrayList<>();
        points.add(gameObject.getPosition());
        points.add(new Position(gameObject.getPosition().x + gameObject.getWidth(), gameObject.getPosition().y));
        points.add(new Position(gameObject.getPosition().x + gameObject.getWidth(), gameObject.getPosition().y + gameObject.getHeight()));
        points.add(new Position(gameObject.getPosition().x, gameObject.getPosition().y + gameObject.getHeight()));

        Position center = new Position(this.position.x + this.getWidth() / 2, this.position.y + this.getHeight() / 2);

        Position closestPoint = points.stream().min(
                (a, b) -> (int) (
                        a.distantSquare(center) - b.distantSquare(center)
                )
        ).get();
        System.out.println("center y: " + center.y);
        System.out.println("center y: " + center.x);
        System.out.println("center b: " + (closestPoint.y - closestPoint.x));
        switch (points.indexOf(closestPoint)) {
            case 0:
                System.out.println("TOP_LEFT");
                if (center.y > center.x + (closestPoint.y - closestPoint.x)) {
                    System.out.println("LEFT");
                    angle = 180 - angle;
                } else {
                    System.out.println("UP");
                    angle = -angle;
                }
                break;
            case 1:
                System.out.println("TOP_RIGHT");
                if (center.y > -center.x + (closestPoint.y + closestPoint.x)) {
                    System.out.println("RIGHT");
                    angle = 180 - angle;
                } else {
                    System.out.println("UP");
                    angle = -angle;
                }
                break;
            case 2:
                System.out.println("BTM_RIGHT");
                if (center.y > center.x + (closestPoint.y - closestPoint.x)) {
                    System.out.println("DOWN");
                    angle = -angle;
                } else {
                    System.out.println("RIGHT");
                    angle = 180 - angle;
                }
                break;
            case 3:
                System.out.println("BTM_LEFT");
                if (center.y > -center.x + (closestPoint.y + closestPoint.x)) {
                    System.out.println("DOWN");
                    angle = -angle;
                } else {
                    System.out.println("LEFT");
                    angle = 180 - angle;
                }
                break;
        }
    }

    public void bounce(GameObject gameObject) {
        /**
         * angle
         * x,y,w,h ball
         * x,y,w,h gameObject
         */
        if (gameObject instanceof Slider) {
            System.out.println("SLIDER");
            angle = 360 - angle;
            afterCollision(gameObject);
            return;
        }
        calculateBallReflectAngle(gameObject);
        afterCollision(gameObject);
    }

    private void afterCollision(GameObject gameObject) {
        this.collidable = false;
        if (gameObject instanceof Slider) {
            Slider slider = (Slider) gameObject;
            this.angle += slider.getGoLeft() * 10;
            this.angle -= slider.getGoRight() * 10;
        }
        angle += angle < -180 ? 360 : angle > 180 ? -360 : 0;
    }

    public void setIsMove(boolean ismove) {
        this.ismove = ismove;
    }

    public void setReset() {
        this.setIsMove(false);
        this.position.y = 420;
        this.position.x = 466;
        this.angle = Math.abs(new Random().nextInt() % 90) + 45;
        ;
    }

    public void fly() {
        this.position.x += speed * Math.cos(Math.toRadians(angle));
        this.position.y -= speed * Math.sin(Math.toRadians(angle));
    }
    public void setAngle(int angle){
        this.angle = angle;
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
}
