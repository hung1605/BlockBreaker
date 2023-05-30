package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.*;
import engine.windows.node.slider.Slider;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class PlayScene extends Scene{
    Background background;
    Slider slider;
    Ball ball;
    public PlayScene(GameWindows gameWindows) {
        super(gameWindows);
        background = new Background(new BufferedImage(gameWindows.getWidth(),gameWindows.getHeight(),BufferedImage.TYPE_BYTE_GRAY));
        this.slider = new Slider(new Position(500, 600), 10);
        this.ball = new Ball(new Position(500, 500), 5);
        this.listGameObject.add(this.slider);
        this.listGameObject.add(this.ball);
        this.listGameObject.add(new Wall(new Position(0,0), 5,gameWindows.getHeight()));// left wall
        this.listGameObject.add(new Wall(new Position(0,0), gameWindows.getWidth(),5));// up wall
        this.listGameObject.add(new Wall(new Position(gameWindows.getWidth(),0), 5,gameWindows.getHeight()));// right wall
        for(int i = 1; i <= 7; i++){
            for (int j = 1; j <= 8; j++){
                this.listGameObject.add(new Block(new Position(100*j,32*i)));
            }
        }
        this.getKeyListenerList().add(slider.getKeyListener());
        this.getKeyListenerList().add(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        ball.setIsMove(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
//        gameWindows.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                gameWindows.popScene();
//            }
//        });
    }
    public void draw(Graphics g){
        background.draw(g);
        super.draw(g);
    }

    @Override
    public void update() {
        super.update();
        checkCollide();
    }
}
