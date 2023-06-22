package engine.windows.node.power;

import engine.windows.common.Position;
import engine.windows.node.GameObject;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Power extends GameObject {
    private IUpgrade iUpgrade;
    public Power(Position position, String path, IUpgrade iUpgrade){
        super(position);
        this.iUpgrade = iUpgrade;
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void collideWith(GameObject target) {
        if(target instanceof Slider){
            TriggerPower();
        }
    }
    public void TriggerPower(){
        iUpgrade.buff();
    }
    public void fall(){
        this.position.y += 5;
    }
}
