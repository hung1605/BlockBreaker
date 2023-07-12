package engine.windows.node.scenes;

import engine.windows.GameWindows;
import engine.windows.common.Position;
import engine.windows.node.*;
import engine.windows.node.block.BlockType;
import engine.windows.node.block.Block;
import engine.windows.node.slider.Slider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayScene extends Scene {
    private Background background;
    private Slider slider;
    private List<Ball> ballList;
    private Health health;
    private Integer[][] matrix;
    private int row;
    private int col;
    private int level;
    public PlayScene(GameWindows gameWindows) {
        super(gameWindows);
        try {
            this.background = new Background(ImageIO.read(new File("Resources/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.level = 1;
        this.ballList = new ArrayList<>();
        this.slider = new Slider(gameWindows, 5);
        this.health = new Health(3);
        this.matrix = LevelUtils.getLevelMatrix(level);

    }
    @Override
    protected void initScene() {
        this.listGameObject.clear();
        this.ballList.clear();
//        Ball ball = new Ball(new Position(gameWindows.getWidth()/2 - 14, slider.getPosition().y - 30));
//        this.ballList.add(ball);
        for (float i = 30f; i < 150f;) {
            Ball ball = new Ball(new Position(gameWindows.getWidth()/2 - 14, slider.getPosition().y - 30));
            ball.setAngle(i);
            ball.setCollidable(true);
            this.ballList.add(ball);
            i+= 0.1f;
        }
        this.listGameObject.addAll(health.getListLifePoint());
        this.listGameObject.add(this.slider);
        this.listGameObject.addAll(ballList);
        this.matrix = LevelUtils.getLevelMatrix(level);
        this.eventListeners.add(slider.getKeyListener());
        this.eventListeners.add(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        for (Ball ball : ballList) {
                            ball.setIsMove(true);
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        EscScene escScene = new EscScene(gameWindows);
                        gameWindows.pushScene(escScene);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.eventListeners.add(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for(Ball ball : ballList){
                    ball.setAngle(-Ball.angleFromBalltoPoint(ball, new Position(e.getX(),e.getY())));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.listGameObject.add(new Wall(gameWindows, Wall.RIGHT));// right wall
        this.listGameObject.add(new Wall(gameWindows, Wall.LEFT));// left wall
        this.listGameObject.add(new Wall(gameWindows, Wall.UP));// up wall
        Block blockPrototype = Block.prototype();
        this.col = (gameWindows.getWidth()) / (blockPrototype.getWidth()) - 2;
        this.row = (gameWindows.getHeight() / 2) / (blockPrototype.getHeight()) - 2;
        Position leftCorner = new Position((gameWindows.getWidth() - blockPrototype.getWidth() * col) / 2,
                (gameWindows.getHeight()/2 - blockPrototype.getHeight() * row) / 2 + 60);
        // khoi tao block
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                switch (this.matrix[j][i]) {
                    case 1:
                        this.listGameObject.add(new Block(new Position(
                                blockPrototype.getWidth() * i + leftCorner.x,
                                blockPrototype.getHeight() * j + leftCorner.y)
                                , BlockType.WOOD_BLOCK, this));
                        break;
                    case 2:
                        this.listGameObject.add(new Block(new Position(
                                blockPrototype.getWidth() * i + leftCorner.x,
                                blockPrototype.getHeight() * j + leftCorner.y)
                                , BlockType.STEEL_BLOCK, this));
                        break;
                    case 0:
                    default:
                        break;
                }
            }
        }
    }

    public void draw(Graphics g) {
        background.draw(g);
        super.draw(g);
        g.drawLine(0,287,1000,287);
    }

    public void checkLose() {
        for(Ball ball : ballList){
            if (ball.getPosition().y >= gameWindows.getHeight()
             || ball.getPosition().x >= gameWindows.getWidth()
             || ball.getPosition().x <= 0
             || ball.getPosition().y <= 0) {
                ball.destroyGameObject();
            }
            if(!ball.isDestroy()) return;
        }
        if (health.getListLifePoint().size() > 1) {
            health.decreaseLifePoint();
            ballList.add(new Ball(new Position(slider.getPosition().x + slider.getWidth()/2 - 14, slider.getPosition().y - 30) ));
            listGameObject.addAll(ballList);
        } else {
            for(Ball ball : ballList) ball.destroyGameObject();
            OverScene overScene = new OverScene(gameWindows);
            gameWindows.pushScene(overScene);
        }
    }
    public void checkWin() {
        for (GameObject gameObject : listGameObject){
            if(gameObject instanceof Block) {
                if(((Block)gameObject).isBreakable()) {
                    return;
                }
            }
        }
//        WinScene winScene = new WinScene(gameWindows, this);
//        gameWindows.pushScene(winScene);
    }
    public void checkCollide(){
        List<GameObject> movingGOs = new ArrayList<>();
        for (int i = 0; i < listGameObject.size(); i++) {
            for (int j = i+1; j < listGameObject.size(); j++) {
                GameObject gameObjectA = listGameObject.get(i);
                GameObject gameObjectB = listGameObject.get(j);
                if(gameObjectB.isCollide(gameObjectA) && gameObjectA.isCollidable() && gameObjectB.isCollidable()) {
                    gameObjectB.collideWith(gameObjectA);
                    gameObjectA.collideWith(gameObjectB);
//                    if(gameObjectB instanceof Ball){
//                        movingGOs.add(gameObjectB);
//                        gameObjectB.setCollidable(false);
//                    }
//                    if(gameObjectA instanceof Ball) {
//                        movingGOs.add(gameObjectA);
//                        gameObjectA.setCollidable(false);
//                    }
                }
            }
        }
        for(GameObject gameObject : movingGOs){
            gameObject.setCollidable(true);
        }

    }
    @Override
    public void update() {
        checkCollide();
        super.update();
        List<GameObject> toBeRemoved = new ArrayList<>();
        for (Ball ball : ballList) {
            if(ball.isDestroy()) toBeRemoved.add(ball);
        }
        ballList.removeAll(toBeRemoved);
        checkWin();
        checkLose();
    }
    public void tripleBallUpgrade(){
        if(ballList.size() > 300) return;
        List<Ball> newBalls = new ArrayList<>();
        for(Ball ball : ballList){
            newBalls.add(ball.cloneSetAngle(75));
            newBalls.add(ball.cloneSetAngle(105));
        }
        ballList.addAll(newBalls);
        listGameObject.addAll(newBalls);
    }
    public void rocketUpgrade(){

    }
    public void widenSlider(){
        this.slider.upgradeBar(500);
    }
    public void addHealth(){
        this.health.increaseLifePoint(this);
    }

    public void nextLevel() {
        level++;
        if(level == LevelUtils.lastLevel){
            System.out.println("you won");
            return;
        }
        initScene();
    }

    public void restart() {
        PlayScene level1 = new PlayScene(gameWindows);
        gameWindows.pushScene(level1);
    }
}
