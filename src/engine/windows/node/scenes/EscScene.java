package engine.windows.node.scenes;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.windows.GameWindows;
import engine.windows.node.Background;

public class EscScene extends Scene{
    Background background;
    public EscScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            this.background = new Background(ImageIO.read(new File("Resources/pause-page.png")));
        } catch (IOException e) {
            System.out.println("image unfound");
            e.printStackTrace();
        }
    }

    @Override
    protected void initScene() {

    }
    @Override
    public void draw(Graphics g){
        g.drawImage(background.getImage(), 
        gameWindows.getWidth() / 2 - background.getImage().getWidth() / 2, 
        gameWindows.getHeight()/2 - background.getImage().getHeight() / 2,
        null);
    }
}
