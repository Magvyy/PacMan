package pacman.model.units;

import grid.CellPosition;
import pacman.view.tiles.Sprite;

public class Unit  {
    UnitType type;
    CellPosition pos;
    int health;
    Sprite sprite;
    
    /**
     * The constructor for a Unit object
     * @param type the type of the unit
     * @param pos the position of the unit
     * @param health the health of the unit
     * @param sprite the sprite of the unit
     */
    public Unit(UnitType type, CellPosition pos, int health, Sprite sprite) {
        this.type = type;
        this.pos = pos;
        this.health = health;
        this.sprite = sprite;
    }

    /**
     * Gets the type of the Unit object, which is either a UnitType.PLAYER or UnitType.ENEMY
     * @return the type of the unit
     */
    public UnitType getType() {
        return type;
    }

    /**
     * Returns the CellPosition of the Unit object
     * @return the position of the unit
     */
    public CellPosition getPos() {
        return pos;
    }

    /**
     * Sets the CellPosition of the Unit object
     * @param pos the new position of the unit
     */
    public void setPos(CellPosition pos) {
        this.pos = pos;
    }

    /**
     * Gets the health of the Unit object
     * @return the health of the unit
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the Unit object
     * @param health the new health of the unit
     */
    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * Gets the Sprite of the Unit object
     * @return the sprite of the unit
     */
    public Sprite getSprite() {
        return sprite;
    }
    
    /**
     * Sets the Sprite of the Unit object
     * @param sprite the new sprite of the unit
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
