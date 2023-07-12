package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.Background;
import engine.windows.node.component.GameButton;
import engine.windows.node.component.ISimpleClickListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class MenuScene extends Scene {
    private Background background;
    private GameButton startButton;

    public MenuScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            this.background = new Background(ImageIO.read(new File("Resources/home-page.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameButton gameButtonPrototype = GameButton.prototype("Resources/start-button.png");
        this.startButton = new GameButton(
                new Position(gameWindows.getWidth() / 2 - gameButtonPrototype.getWidth() / 2, gameWindows.getHeight() * 5 / 6),
                new ISimpleClickListener() {
                    @Override
                    public void onClick() {
                        PlayScene level1 = new PlayScene(gameWindows);
                        gameWindows.pushScene(level1);
                    }
                },
                "Resources/start-button.png");
        this.listGameObject.add(startButton);
        this.eventListeners.add(startButton.getMouseListener());
        this.eventListeners.add(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        EscScene escScene = new EscScene(gameWindows);
                        gameWindows.pushScene(escScene);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
            }
            
        });
    }

    @Override
    protected void initScene() {

    }

    public void draw(Graphics g) {
        background.draw(g);
        super.draw(g);
    }
}
