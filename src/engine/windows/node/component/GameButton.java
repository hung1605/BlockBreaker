package engine.windows.node.component;

import engine.windows.common.Position;
import engine.windows.node.GameObject;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
// dùng interface để mỗi object tạo ra có thể định nghĩa lại 1 hàm con trong interface
public class GameButton extends GameObject {
    ISimpleClickListener GBCListener;
    MouseListener mouseListener;
    private boolean isPressed;
    public static GameButton prototype(String path){
        return new GameButton(new Position(0,0),null, path);
    }
    public GameButton(Position position, ISimpleClickListener GBCListener, String path) {
        super(position);
        this.GBCListener = GBCListener;
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getX() < position.x + image.getWidth()
                && e.getX() > position.x
                && e.getY() < position.y + image.getHeight()
                && e.getY() > position.y) {
                    isPressed = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getX() < position.x + image.getWidth()
                && e.getX() > position.x
                && e.getY() < position.y + image.getHeight()
                && e.getY() > position.y && isPressed){
                    TriggerClick();
                }
                isPressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
    @Override
    public void collideWith(GameObject target) {

    }
    public void TriggerClick(){
        GBCListener.onClick();
    }

    public ISimpleClickListener getGBCListener() {
        return GBCListener;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }
}
