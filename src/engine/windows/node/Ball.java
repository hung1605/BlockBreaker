package engine.windows.node;

import engine.windows.common.Position;
import engine.windows.node.block.Block;
import engine.windows.node.power.Power;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
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
    private float speed = 1f;
    private int resetFrame = 1;
    private int currentFrame;
    List<GameObject> frameCollisionGOs = new ArrayList<>();
    GameObject lastBouncedGO = null;
    public static Ball prototype() {
        return new Ball(new Position(0, 0));
    }
    public Ball cloneSetAngle(int setedAngle) {
        Ball newBall = new Ball(this.position.clone());
        newBall.setIsMove(true);
        newBall.setAngle(setedAngle);
        return newBall;
    }

    public Ball(Position position) {
        super(position);
        try {
            this.image = ImageIO.read(new File("Resources/ball.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.angle = Math.abs(new Random().nextInt() % 90) + 45;
//        this.angle = 83.899635f;
        this.ismove = false;
    }

    @Override
    public void collideWith(GameObject target) {
        if(target instanceof Ball) return;
        if(target instanceof Power) return;
        frameCollisionGOs.add(target);
    }

    void calculateBallReflectAngle(GameObject gameObject) {
//        this.collidable = false;
        List<Position> points = new ArrayList<>();
        points.add(gameObject.getPosition());
        points.add(new Position(gameObject.getPosition().x + gameObject.getWidth(), gameObject.getPosition().y));
        points.add(new Position(gameObject.getPosition().x + gameObject.getWidth(), gameObject.getPosition().y + gameObject.getHeight()));
        points.add(new Position(gameObject.getPosition().x, gameObject.getPosition().y + gameObject.getHeight()));

        Position center = new Position(this.position.x + this.getWidth() / 2, this.position.y + this.getHeight() / 2);

        if(center.x > gameObject.position.x && center.x < gameObject.position.x + gameObject.getWidth()
        && center.y > gameObject.position.y && center.y < gameObject.position.y + gameObject.getHeight()
        ) {
            return;
        }
        Position closestPoint = points.stream().min(
                (a, b) -> (int) (
                        a.distantSquare(center) - b.distantSquare(center)
                )
        ).get();
//        System.out.println("center y: " + center.y);
//        System.out.println("center y: " + center.x);
//        System.out.println("center b: " + (closestPoint.y - closestPoint.x));
        switch (points.indexOf(closestPoint)) {
            case 0:
//                System.out.println("TOP_LEFT");
                if (center.y > center.x + (closestPoint.y - closestPoint.x)) {
//                    System.out.println("LEFT");
                    angle = 180 - angle;
                } else {
//                    System.out.println("UP");
                    angle = -angle;
                }
                break;
            case 1:
//                System.out.println("TOP_RIGHT");
                if (center.y > -center.x + (closestPoint.y + closestPoint.x)) {
//                    System.out.println("RIGHT");
                    angle = 180 - angle;
                } else {
//                    System.out.println("UP");
                    angle = -angle;
                }
                break;
            case 2:
//                System.out.println("BTM_RIGHT");
                if (center.y > center.x + (closestPoint.y - closestPoint.x)) {
//                    System.out.println("DOWN");
                    angle = -angle;
                } else {
//                    System.out.println("RIGHT");
                    angle = 180 - angle;
                }
                break;
            case 3:
//                System.out.println("BTM_LEFT");
                if (center.y > -center.x + (closestPoint.y + closestPoint.x)) {
//                    System.out.println("DOWN");
                    angle = -angle;
                } else {
//                    System.out.println("LEFT");
                    angle = 180 - angle;
                }
                break;
        }
//        if(gameObject instanceof Block) {
//            ((Block) gameObject).position.y += 100;
//        }
    }
    public static float angleFromPointtoBall(Ball ball, Position position){
        float x_ball = ball.getCenter().x;
        float y_ball = ball.getCenter().y;
        float vertical = Math.abs(y_ball - position.y) ;
        float horizontal = Math.abs(x_ball - position.x);
        float tanOfAngle = vertical / horizontal;
        float arcTanOfAngle = (float) Math.toDegrees (Math.atan(tanOfAngle));
        if(y_ball < position.y)
            return x_ball < position.x ? 180 - arcTanOfAngle : arcTanOfAngle;
        else
            return x_ball < position.x ? - 180 + arcTanOfAngle : - arcTanOfAngle;
    }
    public static float angleFromBalltoPoint(Ball ball, Position position){
        float x_ball = ball.getCenter().x;
        float y_ball = ball.getCenter().y;
        float vertical = Math.abs(y_ball - position.y) ;
        float horizontal = Math.abs(x_ball - position.x);
        float tanOfAngle = vertical / horizontal;
        float arcTanOfAngle = (float) Math.toDegrees (Math.atan(tanOfAngle));
        if(y_ball < position.y)
            return x_ball < position.x ? arcTanOfAngle : 180 - arcTanOfAngle;
        else
            return x_ball < position.x ? - arcTanOfAngle : - 180 + arcTanOfAngle;
    }
    public void bounce(GameObject gameObject) {
        /**
         * angle
         * x,y,w,h ball
         * x,y,w,h gameObject
         */
        System.out.println(gameObject.getClass().getSimpleName());
        System.out.println(this.angle);
        if (gameObject instanceof Slider) {
            angle = angleFromPointtoBall(this,gameObject.getCenter());
            this.collidable = false;
            afterCollision(gameObject);
            return;
        }
        calculateBallReflectAngle(gameObject);
        afterCollision(gameObject);
    }

    private void afterCollision(GameObject gameObject) {
        angle += angle < -180 ? 360 : angle > 180 ? -360 : 0;
        System.out.println(angle);
    }

    public void setIsMove(boolean ismove) {
        this.ismove = ismove;
    }

    public float getSpeed() {
        return speed;
    }

    public void fly() {
        this.position.x += speed * Math.cos(Math.toRadians(angle));
        this.position.y -= speed * Math.sin(Math.toRadians(angle));
    }
    public void setAngle(float angle){
        this.angle = angle;
    }
    @Override
    public void update() {
        if(this.position.y < 283) {
            currentFrame = currentFrame;
        }
        if(!frameCollisionGOs.isEmpty()) {
            GameObject obj = frameCollisionGOs.get(0);
            double mindistant = Double.MAX_VALUE;
            for(GameObject gameObject : frameCollisionGOs){
                if(this.getCenter().distant(gameObject.getCenter()) < mindistant){
                    obj = gameObject;
                    mindistant = this.getCenter().distant(gameObject.getCenter());
                }
            }
            if(lastBouncedGO != obj) {
                bounce(obj);
                lastBouncedGO = obj;
            }
            frameCollisionGOs.clear();

        }
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

        frameCollisionGOs.clear();
    }
}
