package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.node.GameObject;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

abstract public class Scene {
    protected List<GameObject> listGameObject;
    protected List<EventListener> eventListeners;
    protected GameWindows gameWindows;
    private boolean initialized = false;

    public Scene(GameWindows gameWindows) {
        this.gameWindows = gameWindows;
        this.listGameObject = new ArrayList<>();
        this.eventListeners = new ArrayList<>();
    }

    private void initSceneInternal() {
        if (!initialized) {
            initialized = true;
            initScene();
            gameWindows.refreshSceneListeners();
        }
    }

    abstract protected void initScene();


    public void draw(Graphics g) {
        for (GameObject gameObject : listGameObject) {
            gameObject.draw(g);
        }
    }
    public void update() {
        initSceneInternal();
        List<GameObject> toBeRemoved = new ArrayList<>();
        for (GameObject gameObject : listGameObject) {
            gameObject.update();
            if(gameObject.isDestroy()) toBeRemoved.add(gameObject);
        }
        listGameObject.removeAll(toBeRemoved);
    }

    public List<EventListener> getEventListeners() {
        return eventListeners;
    }

    public void checkCollide() {
        for (int i = 0; i < listGameObject.size(); i++) {
            for (int j = i+1; j < listGameObject.size(); j++) {
                GameObject gameObjectA = listGameObject.get(i);
                GameObject gameObjectB = listGameObject.get(j);
                if(gameObjectB.isCollide(gameObjectA) && gameObjectA.isCollidable() && gameObjectB.isCollidable()) {
                    gameObjectB.collideWith(gameObjectA);
                    gameObjectA.collideWith(gameObjectB);
                }
            }
        }
    }

    public void addGameObject(GameObject gameObject) {
        this.listGameObject.add(gameObject);
    }
}
