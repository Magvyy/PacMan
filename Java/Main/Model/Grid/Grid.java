package Java.Main.Model.Grid;

import java.util.ArrayList;

import Java.Main.View.Sprites.Sprite;

public class Grid extends Dimensions {
    private ArrayList <Cell> cells;

    public Grid(int rows, int cols) {
        super(rows, cols);
        cells = new ArrayList<>(rows * cols);
    }

    public Sprite getSprite(CellPosition pos) {
        int index = getIndex(pos);
        Cell cell = cells.get(index);
        return cell.getSprite();
    }

    public void setSprite(CellPosition pos, Sprite sprite) {
        int index = getIndex(pos);
        Cell cell = cells.get(index);
        cell.setSprite(sprite);
    }

    private int getIndex(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row < 0 || row >= getRows()) {
            System.out.println("Out of bounds row.");
        }
        if (col < 0 || col >= getCols()) {
            System.out.println("Out of bounds column.");
        }
        return row * getCols() + col;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public Iterable <Cell> getCells() {
        return cells;
    }
}
