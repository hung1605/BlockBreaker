package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.*;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class PlayScene extends Scene {
    private Background background;
    private Slider slider;
    private Ball ball;
    private Health health;

    public PlayScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            background = new Background(ImageIO.read(new File("Resources/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.slider = new Slider(new Position(500, 600), 10, gameWindows.getWidth());
        this.ball = new Ball(new Position(500, 500), 10);
        this.health = new Health(3);
        this.listGameObject.addAll(health.getListLifePoint());
        this.listGameObject.add(this.slider);
        this.listGameObject.add(this.ball);

        this.getKeyListenerList().add(slider.getKeyListener());
        this.getKeyListenerList().add(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        ball.setIsMove(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    @Override
    protected void initScene() {
        this.listGameObject.add(new Wall(gameWindows, Wall.RIGHT));// right wall
        this.listGameObject.add(new Wall(gameWindows, Wall.LEFT));// left wall
        this.listGameObject.add(new Wall(gameWindows, Wall.UP));// up wall
        Block blockPrototype = Block.prototype();
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 10; j++) {
                this.listGameObject.add(new Block(new Position(blockPrototype.getWidth() * j - 63, blockPrototype.getHeight() * i), i % 3 + 1));
            }
        }
    }

    public void draw(Graphics g) {
        background.draw(g);
        super.draw(g);
    }

    public void checkLose() {
        if (ball.getPosition().y >= gameWindows.getHeight()
                || ball.getPosition().x >= gameWindows.getWidth()
                || ball.getPosition().x <= 0
                || ball.getPosition().y <= 0) {
            if (health.getListLifePoint().size() == 1) {
                ball.destroyGameObject();
                OverScene overScene = new OverScene(gameWindows);
                gameWindows.pushScene(overScene);
            } else {
                health.decreaseLifePoint();
                this.ball.getPosition().x = 500;
                this.ball.getPosition().y = 500;
                this.ball.setIsMove(false);
                this.ball.setResetAngle();
            }
        }
    }

    @Override
    public void update() {
        super.update();
        checkCollide();
        checkLose();
    }
}
