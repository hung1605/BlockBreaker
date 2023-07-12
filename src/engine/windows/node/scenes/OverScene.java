package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.Background;
import engine.windows.node.component.GameButton;
import engine.windows.node.component.ISimpleClickListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OverScene extends Scene{
    private GameButton restartButton;
    private Background background;
    private BufferedImage highScore;
    // private GameButton homeButton;
    public OverScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            background = new Background(ImageIO.read(new File("Resources/background.png")));
            highScore = ImageIO.read(new File("Resources/result-page.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        restartButton = new GameButton(
                new Position(gameWindows.getWidth() / 2 - 119/2, gameWindows.getHeight() * 5 / 7 ),
                new ISimpleClickListener() {
                    @Override
                    public void onClick() {
                        PlayScene level1 = new PlayScene(gameWindows);
                        gameWindows.pushScene(level1);
                    }
                },
                "Resources/restart-button.png");
        listGameObject.add(restartButton);
        this.eventListeners.add(restartButton.getMouseListener());

    }
    @Override
    protected void initScene() {

    }
    public void draw(Graphics g) {
        background.draw(g);
        g.drawImage(highScore,gameWindows.getWidth() / 2 - highScore.getWidth() / 2
                ,gameWindows.getHeight() / 3,null);
        super.draw(g);
    }

}
