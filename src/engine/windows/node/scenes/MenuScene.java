package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.component.GameButton;
import engine.windows.node.component.ISimpleClickListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class MenuScene extends Scene {
    GameButton gameButton;

    public MenuScene(GameWindows gameWindows) {
        super(gameWindows);
        gameButton = new GameButton(
                new Position(gameWindows.getWidth() / 2 - 64, gameWindows.getHeight() * 5 / 6 - 128),
                new ISimpleClickListener() {
                    @Override
                    public void onClick() {
                        System.out.println("PLAY");
                        PlayScene playScene = new PlayScene(gameWindows);
                        gameWindows.pushScene(playScene);
                    }
                },
                "Resources/59-Breakout-Tiles.png");
        listGameObject.add(gameButton);
        this.getMouseListenerList().add(gameButton.getMouseListener());
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, gameWindows.getWidth(), gameWindows.getHeight());
        super.draw(g);
    }
}
