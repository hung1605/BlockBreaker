package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.Background;
import engine.windows.node.component.GameButton;
import engine.windows.node.component.ISimpleClickListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OverScene extends Scene{
    private GameButton restartButton;
    // private GameButton homeButton;
    public OverScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            background = new Background(ImageIO.read(new File("Resources/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        restartButton = new GameButton(
                new Position(gameWindows.getWidth() / 2 - 209/2, gameWindows.getHeight() * 5 / 7 ),
                new ISimpleClickListener() {
                    @Override
                    public void onClick() {
                        PlayScene playScene = new PlayScene(gameWindows);
                        gameWindows.pushScene(playScene);
                    }
                },
                "Resources/restart-button.png");
        listGameObject.add(restartButton);
        this.getMouseListenerList().add(restartButton.getMouseListener());

    }

    @Override
    protected void initScene() {

    }

    private Background background;
    public void draw(Graphics g) {
        background.draw(g);
        super.draw(g);
    }

}
