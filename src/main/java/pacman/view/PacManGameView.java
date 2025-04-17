package pacman.view;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import grid.CellPosition;
import grid.GridCell;
import grid.GridDimension;
import pacman.model.GameState;
import pacman.model.units.Unit;
import pacman.view.tiles.Sprite;
import pacman.view.tiles.Tile;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class PacManGameView extends JPanel {
    private ViewableModel model;
    private final int side;
    private int height;
    private int width;
    
    /**
     * Constructs a PacManView object
     * @param model a model to help generate the graphics
     * @param side the size of the individual Tile objects in the game
     */
    public PacManGameView(ViewableModel model, int side) {
        GridDimension gd = model.getDimension();
        int rows = gd.getRows();
        int cols = gd.getCols();
        this.model = model;
        this.side = side;
        height = side * rows;
        width = side * cols;
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Draws the maze, its Tile objects and the Units in the game
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
        if (model.getGameState() == GameState.GAME_OVER) {
            drawGameOver(g2);
        }
    }

    /*
     * Draws the game
     */
    private void drawGame(Graphics2D g2) {
        Rectangle2D board = new Rectangle2D.Double(0, 0, width, height);
        GridDimension gd = model.getDimension();
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(board, gd);
        Iterable <GridCell <Tile>> cells = model.getTiles();
        g2.draw(board);
        drawTiles(g2, cells, converter);
        drawScore(g2, model.getScore(), converter);
        drawUnits(g2, converter);
    }

    /**
     * Draws the Tiles
     * @param cells an iterable of the GridCells in the maze such that we can draw each Tile
     * @param converter an object that helps get the dimensions and position of each Tile or Unit sprite
     */
    private void drawTiles(Graphics2D g2, Iterable <GridCell <Tile>> cells, CellPositionToPixelConverter converter) {
        for (GridCell <Tile> cell : cells) {
            CellPosition pos = cell.getPos();
            Tile tile = cell.getValue();
            drawSprite(g2, pos, tile, null, converter);
        }
        
    }
    
    /**
     * Draws the 3 score Tiles below the center of the maze
     * @param score the score to be drawn
     * @param converter an object that helps get the dimensions and position of each Tile or Unit sprite
     */
    private void drawScore(Graphics2D g2, int score, CellPositionToPixelConverter converter) {
        GridDimension gd = model.getDimension();
        int rows = gd.getRows();
        int cols = gd.getCols();
        int midRow = (rows - 1) / 2;
        int midCol = (cols - 1) / 2;
        String string = "" + score;
        for (int i = string.length() - 1; i >= 0; i--) {
            String c = "" + string.charAt(i);
            CellPosition pos = new CellPosition(midRow + 2, midCol + 1 - (string.length() - (i + 1)));
            Tile tile = new Tile(Sprite.SCORE, c);
            drawSprite(g2, pos, tile, null, converter);
        }  
    }

    /**
     * Draws the units
     * @param converter an object that helps get the dimensions and position of each Tile or Unit sprite
     */
    private void drawUnits(Graphics2D g2, CellPositionToPixelConverter converter) {
        Unit pacman = model.getPacMan();
        CellPosition pos = pacman.getPos();
        drawSprite(g2, pos, null, pacman, converter);
        for (Unit ghost : model.getGhosts()) {
            pos = ghost.getPos();
            drawSprite(g2, pos, null, ghost, converter);
        }
    }

    /**
     * Draws the sprites of either a Tile object or a Unit object
     * @param pos the position of the Tile/Unit object
     * @param tile the Tile object (could be null)
     * @param unit the Unit object (could be null)
     * @param converter an object that helps get the dimensions and position of each Tile or Unit sprite
     */
    private void drawSprite(Graphics2D g2, CellPosition pos, Tile tile, Unit unit, CellPositionToPixelConverter converter) {
        SpriteImageLoader sprites = new SpriteImageLoader();
        Rectangle2D cellBox = converter.getBoundsForCell(pos);
        double x = cellBox.getMinX();
        double y = cellBox.getMinY();
        double scale = (double) side / 16;
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.scale(scale, scale);
        if (tile != null) {
            Image image = sprites.getTileImage(tile);
            g2.drawImage(image, transform, null);
        }
        if (unit != null) {
            Image image = sprites.getUnitImage(unit);
            g2.drawImage(image, transform, null);
        }
    }

    // If the game is over the paintComponent() method draws a game over screen instead of the game
    private void drawGameOver(Graphics2D g2) {
        GridDimension gd = model.getDimension();
        int rows = gd.getRows();
        int cols = gd.getCols();
        int midRow = (rows - 1) / 2;
        int midCol = (cols - 1) / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(midCol * side, midRow * side);
        try {
            String basePath = (new File("")).getAbsolutePath();
            Image image = ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\gameover.png"));
            int dx = image.getWidth(null);
            int dy = image.getHeight(null);
            transform.translate(-dx / 2, -dy / 2);
            g2.drawImage(image, transform, null);
        }
        catch (IOException | IllegalArgumentException e) {}
    }
}
