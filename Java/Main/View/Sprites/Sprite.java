package Java.Main.View.Sprites;

import java.awt.image.BufferedImage;

public abstract class Sprite {
    protected static final Loader loader = new Loader();
    protected BufferedImage image;

    public Sprite() {}

    public BufferedImage getImage() {
        return image;
    }

    abstract public void setImage();

    abstract public boolean isUnit();

    abstract public boolean isWall();

    abstract public boolean isWalk();
}
