package Java.Main.View.Sprites;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Java.Main.View.Sprites.Tiles.Walkable.Walkable;
import Java.Main.View.Sprites.Tiles.Walls.Wall;
import Java.Main.View.Sprites.Units.Ghosts.Ghost;
import Java.Main.View.Sprites.Units.PacMan.PacMan;

public class Loader {
    protected static final String basePath = (new File("")).getAbsolutePath() + "\\Java\\Main\\View\\Sprites\\Images\\";
    

    public BufferedImage getWallImage(Wall wall) {
        StringBuilder con = new StringBuilder();
        int [] connections = wall.getConnections();
        for (int i = 0; i < 4; i++) {
            con.append(connections[i]);
        }
        try {
            return switch(con.toString()) {
                case "0000" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\lone.png"));
                case "0001" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\single\\right.png"));
                case "0010" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\single\\down.png"));
                case "0100" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\single\\left.png"));
                case "1000" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\single\\up.png"));
                case "0101" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\straight\\horizontal.png"));
                case "1010" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\straight\\vertical.png"));
                case "0011" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\bend\\downRight.png"));
                case "0110" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\bend\\downLeft.png"));
                case "1100" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\bend\\upLeft.png"));
                case "1001" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\bend\\upRight.png"));
                case "0111" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\triple\\down.png"));
                case "1110" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\triple\\left.png"));
                case "1101" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\triple\\up.png"));
                case "1011" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\triple\\right.png"));
                case "1111" -> ImageIO.read(new File(basePath + "Tiles\\Walls\\intersect.png"));
                default     -> throw new IllegalArgumentException("No available sprite for this wall tile.");
            };
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    public BufferedImage getWalkImage(Walkable tile) {
        try {
            return switch (tile.getSprite()) {
                case OPEN   -> ImageIO.read(new File(basePath + "Tiles\\Walkable\\Open.png"));
                case FRUIT  -> ImageIO.read(new File(basePath + "Tiles\\Walkable\\Fruit.png"));
                default     -> throw new IllegalArgumentException("No available sprite for this open tile.");
            };
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    public BufferedImage getPacImage(PacMan pacMan) {
        try {
            return switch (pacMan.getDirection()) {
                case UP    -> ImageIO.read(new File(basePath + "Units\\PacMan\\pacmanUp.png"));
                case LEFT  -> ImageIO.read(new File(basePath + "Units\\PacMan\\pacmanLeft.png"));
                case DOWN  -> ImageIO.read(new File(basePath + "Units\\PacMan\\pacmanDown.png"));
                case RIGHT -> ImageIO.read(new File(basePath + "Units\\PacMan\\pacmanRight.png"));
                default    -> throw new IllegalArgumentException("No available sprite for this PacMan unit.");
            };
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    public BufferedImage getGhostImage(Ghost ghost) {
        try {
            return switch (ghost.getSprite()) {
                case BLINKY -> ImageIO.read(new File(basePath + "Units\\Ghosts\\blinky.png"));
                case PINKY  -> ImageIO.read(new File(basePath + "Units\\Ghosts\\pinky.png"));
                case INKY   -> ImageIO.read(new File(basePath + "Units\\Ghosts\\inky.png"));
                case CLYDE  -> ImageIO.read(new File(basePath + "Units\\Ghosts\\clyde.png"));
                default     -> throw new IllegalArgumentException("No available sprite for this ghost unit.");
            };
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }
}
