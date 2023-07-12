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

public class WinScene extends Scene{
    Background background;
    GameButton replayButton;
    GameButton continueButton;
    public WinScene(GameWindows gameWindows, PlayScene playScene){
        super(gameWindows);
        try {
            this.background = new Background(ImageIO.read(new File("Resources/tutorial-page.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        replayButton = new GameButton(new Position(330, 155), new ISimpleClickListener() {
            @Override
            public void onClick() {
                playScene.restart();
                gameWindows.popScene();
            }
        },"Resources/restart-button.png");
        this.listGameObject.add(replayButton);
        this.eventListeners.add(replayButton.getMouseListener());
        continueButton = new GameButton(new Position(500, 155), new ISimpleClickListener() {
            @Override
            public void onClick() {
                playScene.nextLevel();
                gameWindows.popScene();
            }
        },"Resources/play-button.png");
        this.listGameObject.add(continueButton);
        this.eventListeners.add(continueButton.getMouseListener());
    }
    @Override
    protected void initScene() {

    }
    @Override
    public void draw(Graphics g){
        g.drawImage(background.getImage(), 0, 0, null);
        super.draw(g);
    }
}
