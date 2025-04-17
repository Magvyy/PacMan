package pacman.view.tiles;

public class Tile {
    private Sprite sprite;
    private int [] connections;
    private String score;

    /**
     * A constructor for wall Tile objects
     * @param sprite the sprite of the wall Tile object
     * @param connections an indicator of neighbouring wall Tile objects in all 4 directions
     */
    public Tile(Sprite sprite, int [] connections) {
        this.sprite = sprite;
        this.connections = connections;
    }

    /**
     * A constructor for score Tile objects
     * @param sprite the sprite of the score Tile object
     * @param score the score the tile represents
     */
    public Tile(Sprite sprite, String score) {
        this.sprite = sprite;
        this.score = score;
    }

    /**
     * A constructor for walkable Tile objects
     * @param sprite the sprite of the walkable Tile object
     */
    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Sets the sprite of a tile
     * @param sprite the new sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * @return the sprite of a Tile object
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Sets the connections of a wall Tile
     * @param connections the connections to surrounding wall Tile objects
     */
    public void setConnections(int [] connections) {
        this.connections = connections;
    }

    /**
     * @return the connection of a wall Tile object
     */
    public String getConnections() {
        String cons = "";
        for (int i : connections) {
            cons += i;
        }
        return cons;
    }

    /**
     * @return the score of a score Tile object
     */
    public String getScore() {
        return score;
    }

    /**
     * Checks if a Tile object is a wall
     * @return true or false
     */
    public boolean isWall() {
        if (sprite == Sprite.WALL) {
            return true;
        }
        return false;
    }
}
