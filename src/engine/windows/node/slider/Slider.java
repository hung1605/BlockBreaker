package engine.windows.node.slider;

import engine.windows.common.Position;
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
    public Slider(Position position, int speed, int moveRange){
        super(position);
        this.speed = speed;
        this.moveRange = moveRange;
        this.goLeftAble = this.goRightAble = true;
        try {
            this.image = ImageIO.read(new File("Resources/48-Breakout-Tiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    }
    @Override
    public void update() {
        if(0 < this.position.x && this.position.x + this.image.getWidth() < moveRange) goLeftAble = goRightAble = true;
        if(!goRightAble) goRight = 0;
        if(!goLeftAble) goLeft = 0;
        if(this.position.x + this.image.getWidth() >= moveRange) goRightAble = false;
        if(this.position.x <= 0) goLeftAble = false;
        this.position.x += (goRight - goLeft) * speed;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }
}
