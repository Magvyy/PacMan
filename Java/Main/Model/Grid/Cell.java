package Java.Main.Model.Grid;

import Java.Main.View.Sprites.Sprite;

public class Cell {
    private CellPosition pos;
    private Sprite sprite;

    public Cell(CellPosition pos, Sprite sprite) {
        this.pos = pos;
        this.sprite = sprite;
    }

    public CellPosition getPos() {
        return pos;
    }

    public void setPos(CellPosition pos) {
        this.pos = pos;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
