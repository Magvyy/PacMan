package pacman.view;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import pacman.model.units.Unit;
import pacman.view.tiles.*;

public class SpriteImageLoader {
    String basePath = (new File("")).getAbsolutePath();
    
    /**
     * Attempts to retrieve the sprite image of a Tile Object
     * @param tile the Tile Object
     * @return the sprite image of the Tile Object
     */
    public BufferedImage getTileImage(Tile tile) {
        try {
            if (!tile.isWall()) {
                BufferedImage sprite = switch(tile.getSprite()) {
                    case FRUIT     -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walkable\\fruit.png"));
                    case OPEN      -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walkable\\open.png"));
                    case SCORE     -> switch (tile.getScore()) {
                        case "0"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\0.png"));
                        case "1"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\1.png"));
                        case "2"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\2.png"));
                        case "3"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\3.png"));
                        case "4"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\4.png"));
                        case "5"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\5.png"));
                        case "6"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\6.png"));
                        case "7"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\7.png"));
                        case "8"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\8.png"));
                        case "9"   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\score\\9.png"));
                        default -> throw new IllegalArgumentException("No available color for '" + tile.getSprite() + "'");
                    };
                    default -> throw new IllegalArgumentException("No available color for '" + tile.getSprite() + "'");
                };
                return sprite;
            }
            BufferedImage sprite = switch(tile.getConnections()) {
                case "0000" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\lone.png"));
                case "0001" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\single\\right.png"));
                case "0010" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\single\\down.png"));
                case "0100" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\single\\left.png"));
                case "1000" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\single\\up.png"));
                case "0101" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\straight\\horizontal.png"));
                case "1010" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\straight\\vertical.png"));
                case "0011" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\bend\\downRight.png"));
                case "0110" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\bend\\downLeft.png"));
                case "1100" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\bend\\upLeft.png"));
                case "1001" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\bend\\upRight.png"));
                case "0111" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\triple\\down.png"));
                case "1110" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\triple\\left.png"));
                case "1101" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\triple\\up.png"));
                case "1011" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\triple\\right.png"));
                case "1111" -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\walls\\intersect.png"));
                default -> throw new IllegalArgumentException("No available color for '" + tile.getSprite() + "'");
            };
            return sprite;
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Attempts to retrieve the sprite image of a Unit Object
     * @param unit the Unit Object
     * @return the sprite image of the Unit Object
     */
    public BufferedImage getUnitImage(Unit unit) {
        try {
            BufferedImage sprite = switch(unit.getSprite()) {
                case PACMAN_UP -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\pacmanUp.png"));
                case PACMAN_LEFT -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\pacmanLeft.png"));
                case PACMAN_DOWN -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\pacmanDown.png"));
                case PACMAN_RIGHT -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\pacmanRight.png"));
                case BLINKY -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\blinky.png"));
                case PINKY  -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\pinky.png"));
                case INKY   -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\inky.png"));
                case CLYDE  -> ImageIO.read(new File(basePath + "\\src\\main\\java\\pacman\\view\\visuals\\sprites\\units\\clyde.png"));
                default -> throw new IllegalArgumentException("No available color for '" + unit.getSprite() + "'");
            };
            return sprite;
        }
        catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }
}
