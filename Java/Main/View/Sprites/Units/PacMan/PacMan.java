package Java.Main.View.Sprites.Units.PacMan;

import Java.Main.Model.Maze.Direction;
import Java.Main.View.Sprites.Units.Unit;

public class PacMan extends Unit {
    Direction direction;

    public PacMan() {
        direction = Direction.RIGHT;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setImage() {
        image = loader.getPacImage(this);
    }
}
