package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.component.GameButton;
import engine.windows.node.component.ISimpleClickListener;

import java.awt.*;

public class MenuScene extends Scene {
    GameButton startButton;

    public MenuScene(GameWindows gameWindows) {
        super(gameWindows);
        startButton = new GameButton(
                new Position(gameWindows.getWidth() / 2 - 64, gameWindows.getHeight() * 5 / 6 - 128),
                new ISimpleClickListener() {
                    @Override
                    public void onClick() {
                        PlayScene playScene = new PlayScene(gameWindows);
                        gameWindows.pushScene(playScene);
                    }
                },
                "Resources/59-Breakout-Tiles.png");
        listGameObject.add(startButton);
        this.getMouseListenerList().add(startButton.getMouseListener());
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, gameWindows.getWidth(), gameWindows.getHeight());
        super.draw(g);
    }
}
