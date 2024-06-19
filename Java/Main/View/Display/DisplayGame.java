package Java.Main.View.Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Java.Main.Model.Grid.Cell;
import Java.Main.Model.Grid.CellPosition;
import Java.Main.Model.Grid.Dimensions;
import Java.Main.Model.Maze.Maze;
import Java.Main.View.Sprites.Sprite;


public class DisplayGame extends JPanel {
    private final static CellBoxer boxer = new CellBoxer();
    private Maze maze;

    public DisplayGame(Maze maze) {
        this.maze = maze;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = this.getBounds();
        int xPixels = (int) bounds.getWidth();
        int yPixels = (int) bounds.getHeight();
        Dimensions dimensions = maze.getDimensions();
        for (Cell cell : maze.getCells()) {
            CellPosition pos = cell.getPos();
            Rectangle cellBox = boxer.cellBox(dimensions, pos, xPixels, yPixels);
            Sprite sprite = cell.getSprite();
            sprite.setImage();
            BufferedImage image = sprite.getImage();
            AffineTransform tf = new AffineTransform();
            tf.translate(cellBox.getMinX(), cellBox.getMinY());
            double sx = (double) cellBox.getWidth() / image.getWidth();
            double sy = (double) cellBox.getHeight() / image.getHeight();
            tf.scale(sx, sy);
            g2.drawImage(image, tf, getFocusCycleRootAncestor());
        }
    }
}