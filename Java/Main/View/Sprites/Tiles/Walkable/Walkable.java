package Java.Main.View.Sprites.Tiles.Walkable;

import Java.Main.View.Sprites.Sprite;

public class Walkable extends Sprite {
    private OpenType sprite;

    public Walkable(OpenType sprite) {
        super();
        this.sprite = sprite;
    }

    public OpenType getSprite() {
        return sprite;
    }

    public void setSprite(OpenType sprite) {
        this.sprite = sprite;
    }

    @Override
    public void setImage() {
        image = loader.getWalkImage(this);
    }

    @Override
    public boolean isUnit() {
        return false;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isWalk() {
        return true;
    }
}
