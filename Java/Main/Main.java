package Java.Main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Java.Main.Model.Maze.Maze;
import Java.Main.View.Display.DisplayGame;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Maze maze = new Maze(35, 37);
        DisplayGame display = new DisplayGame(maze);
        display.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(display);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.pack();
    }
}
