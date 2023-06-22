package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.*;
import engine.windows.node.block.BlockType;
import engine.windows.node.block.Block;
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
            this.background = new Background(ImageIO.read(new File("Resources/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.slider = new Slider(gameWindows, 10);
        this.ball = new Ball(new Position(gameWindows.getWidth()/2 - 14, slider.getPosition().y - 30), 7);
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
                    case KeyEvent.VK_ESCAPE:
                        EscScene escScene = new EscScene(gameWindows);
                        gameWindows.pushScene(escScene);
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
        int numberOfBlockHorizontal = (gameWindows.getWidth() - 200) / (blockPrototype.getWidth() + 20);
        int numberOfBlockVertical = (gameWindows.getHeight() / 2) / (blockPrototype.getHeight() + 20);
        Position leftCorner = new Position(150, 63);
        // khoi tao block
        for (int i = 1; i <= numberOfBlockVertical; i++) {
            for (int j = 1; j <= numberOfBlockHorizontal; j++) {
                if((i + j) % 7 == 0)
                    this.listGameObject.add(new Block(new Position(
                            blockPrototype.getWidth() * j + leftCorner.x,
                            blockPrototype.getHeight() * i + leftCorner.y
                    ), BlockType.STEEL_BLOCK, this));
                else
                    this.listGameObject.add(new Block(new Position(
                            blockPrototype.getWidth() * j + leftCorner.x,
                            blockPrototype.getHeight() * i + leftCorner.y)
                            , BlockType.WOOD_BLOCK, this));
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
            System.out.println("bro u suck");
            if (health.getListLifePoint().size() > 1) {
                health.decreaseLifePoint();
                this.ball.setReset();
            } else {
                ball.destroyGameObject();
                OverScene overScene = new OverScene(gameWindows);
                gameWindows.pushScene(overScene);
            }
        }
    }
    public void checkWin() {
        for (GameObject gameObject : listGameObject){
            if(gameObject instanceof Block) return;
        }
        System.out.println("chuc mung nam moi");
    }
    @Override
    public void update() {
        checkCollide();
        super.update();
        checkWin();
        checkLose();
    }


}
