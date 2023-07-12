package engine.windows;


import engine.windows.node.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Tdh4vn
 */
public class GameWindows extends Frame implements Runnable {

    private static final int UPDATE_PER_SECOND = 480;
    private Stack<Scene> sceneStack;
    private Image image;
    private Graphics second;

    public GameWindows() {
        super();
        sceneStack = new Stack<>();
        this.setSize(960, 540);
        this.setTitle("Block braker");
        this.setFocusable(true);
        this.setVisible(true);
        this.setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                super.windowClosing(e);
                dispose();
            }
        });
    }

    @Override
    public void update(Graphics g) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().update();
        }
        drawBufferImage(g);
    }

    private void drawBufferImage(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }
        paint(second);
        g.drawImage(image, 0, 0, null);
    }


    public void pushScene(Scene newScene) {
        sceneStack.push(newScene);
        refreshSceneListeners();
    }

    public void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop();
            refreshSceneListeners();
        }
    }

    public void refreshSceneListeners() {
        Scene currentScene = sceneStack.peek();
        for (KeyListener keyListener : this.getKeyListeners()) {
            this.removeKeyListener(keyListener);
        }
        for (MouseListener mouseListener : this.getMouseListeners()) {
            this.removeMouseListener(mouseListener);
        }
        for (EventListener listener : currentScene.getEventListeners()) {
            if (listener instanceof KeyListener) {
                this.addKeyListener((KeyListener) listener);
            }
            if (listener instanceof MouseListener) {
                this.addMouseListener((MouseListener) listener);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().draw(g);
        }
    }


    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000 / UPDATE_PER_SECOND);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        Thread mainThread = new Thread(this);
        mainThread.start();
    }
}