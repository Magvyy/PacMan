package Java.Main.View.Sprites.Units.Ghosts;

import Java.Main.View.Sprites.Units.Unit;

public class Ghost extends Unit {
    private GSprite sprite;

    public Ghost(GSprite sprite) {
        super();
        this.sprite = sprite;
    }

    public GSprite getSprite() {
        return sprite;
    }

    @Override
    public void setImage() {
        image = loader.getGhostImage(this);
    }
}
