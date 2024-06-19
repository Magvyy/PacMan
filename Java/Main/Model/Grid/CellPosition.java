package Java.Main.Model.Grid;

import java.util.Arrays;

public class CellPosition {
    private int row;
    private int col;

    public CellPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object [] {row, col});
    }

    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null) {return false;}
        if (this.getClass() != o.getClass()) {return false;}
        CellPosition that = (CellPosition) o;
        return this.row == that.row && this.col == that.col;
    }

    public String toString() {
        return "(row: " + row + ", col: " + col + ")";
    }
}