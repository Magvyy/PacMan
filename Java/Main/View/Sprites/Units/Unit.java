package Java.Main.View.Sprites.Units;

import Java.Main.View.Sprites.Sprite;

public abstract class Unit extends Sprite {

    public Unit() {
        super();
    }

    @Override
    public boolean isUnit() {
        return true;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isWalk() {
        return false;
    }
}
