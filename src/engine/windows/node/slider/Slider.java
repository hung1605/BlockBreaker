package engine.windows.node.slider;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.Ball;
import engine.windows.node.GameObject;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Slider extends GameObject {
    private int goLeft;
    private int goRight;
    private KeyListener keyListener;
    private int speed;
    private int moveRange;
    private boolean goLeftAble;
    private boolean goRightAble;
    int currentFrame;
    int currentTime;
    boolean ballCollidable = true;

    public Slider(GameWindows gameWindows, int speed){
        super(new Position(0,0));
        this.currentTime = 0;
        this.speed = speed;
        this.moveRange = gameWindows.getWidth();
        this.goLeftAble = true;
        this.goRightAble = true;
        try {
            this.image = ImageIO.read(new File("Resources/player.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.position.x = gameWindows.getWidth() / 2 - this.image.getWidth() / 2;
        this.position.y = gameWindows.getHeight() * 5 / 6;
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        if(goLeftAble)
                            goLeft = 1;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        if(goRightAble)
                            goRight = 1;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        goLeft = 0;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        goRight = 0;
                        break;
                }
            }
        };
    }
    @Override
    public void collideWith(GameObject target) {
        if(target instanceof Ball) {
            this.ballCollidable = false;
        }
    }
    public void upgradeBar(int time){
        this.currentTime = time;
        try {
            this.setImage(ImageIO.read(new File("Resources/scroll-bar.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void decreaseUpgrade(){
        this.currentTime--;
        if(this.currentTime == 0){
            try {
                this.setImage(ImageIO.read(new File("Resources/player.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void update() {
        decreaseUpgrade();
        if(0 < this.position.x && this.position.x + this.image.getWidth() < moveRange) goLeftAble = goRightAble = true;
        if(!goRightAble) goRight = 0;
        if(!goLeftAble) goLeft = 0;
        if(this.position.x + this.image.getWidth() >= moveRange - 40) {
            goRightAble = false;
            goRight = 0;
        }
        if(this.position.x <= 40){
            goLeftAble = false;
            goLeft = 0;
        }
        this.position.x += (goRight - goLeft) * speed;
        if (!this.ballCollidable) {
            currentFrame++;
            if (currentFrame == 10) {
                currentFrame = 0;
                this.ballCollidable = true;
            }
        }
    }


    public KeyListener getKeyListener() {
        return keyListener;
    }

    public int getGoLeft() {
        return goLeft;
    }

    public int getGoRight() {
        return goRight;
    }

}
