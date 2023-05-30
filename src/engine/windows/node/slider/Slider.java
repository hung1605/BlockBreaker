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
    public Slider(Position position, int speed){
        super(position);
        this.speed = speed;
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
                        goLeft = 1;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
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
        this.position.x += (goRight - goLeft) * speed;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }
}
