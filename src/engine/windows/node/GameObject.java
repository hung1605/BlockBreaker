package engine.windows.node;

import engine.windows.common.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected Position position;
    protected BufferedImage image;

    protected boolean collidable = true;

    private boolean isDestroyed = false;

    public GameObject(Position position) {
        this.position = position;
    }

    public void update() {
    }

    abstract public void collideWith(GameObject target);

    public void draw(Graphics g) {
        g.drawImage(image, (int)position.x, (int)position.y, null);
    }

    public Position getPosition() {
        return position;
    }

    public void destroyGameObject() {
        this.isDestroyed = true;
    }

    public boolean isDestroy() {
        return isDestroyed;
    }

    public int getWidth(){
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }

    public boolean isCollide(GameObject gameObject) {
        if(!this.collidable || !gameObject.collidable) return false;

        float x1 = this.position.x;
        float y1 = this.position.y;
        int width1 = this.image.getWidth();
        int height1 = this.image.getHeight();

        float x2 = gameObject.position.x;
        float y2 = gameObject.position.y;
        int width2 = gameObject.image.getWidth();
        int height2 = gameObject.image.getHeight();

        return !(x1 + width1 < x2 ||
                y1 + height1 < y2 ||
                x1 > x2 + width2 ||
                y1 > y2 + height2
        );
    }
}
