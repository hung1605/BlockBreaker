/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.windows;

import engine.windows.node.scenes.MenuScene;
import engine.windows.node.scenes.PlayScene;

import java.io.IOException;

/**
 * @author Tdh4vn
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //Start Game Windows
        GameWindows gameWindows = new GameWindows();
//        MenuScene menuScene = new MenuScene(gameWindows);
        PlayScene menuScene = new PlayScene(gameWindows);
        gameWindows.pushScene(menuScene);
        gameWindows.start();
    }
}
