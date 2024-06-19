package Java.Main.View.Sprites.Tiles.Walls;

import Java.Main.View.Sprites.Sprite;

public class Wall extends Sprite {
    private int [] connections;

    public Wall(int [] connections) {
        super();
        this.connections = connections;
    }

    public Wall() {
        super();
        connections = new int [4];
    }

    public int [] getConnections() {
        return connections;
    }

    @Override
    public void setImage() {
        image = loader.getWallImage(this);
    }

    @Override
    public boolean isUnit() {
        return false;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }
}
