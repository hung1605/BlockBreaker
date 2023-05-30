package engine.windows.node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage image;
    public Background(BufferedImage image){
        this.image = image;
    }
    public void draw(Graphics g){
        g.drawImage(this.image,0,0,null);
    }
}
