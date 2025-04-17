package grid;


/* 
 * This code, as well as the other classes within this package are taken from the first assignment
 */
public class CellPosition {
    private final int row;
    private final int col;

    public CellPosition (int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Returns the row of the CellPosition object
    public int getRow() {
        return row;
    }
    
    // Returns the column of the CellPosition object
    public int getCol() {
        return col;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CellPosition)) {
            return false;
        }

        CellPosition b = (CellPosition) o;

        if (this.row == b.getRow() && this.col == b.getCol()) {
            return true;
        }
        return false;
    }

    // Prime factorization permits each cellPosition to have a unique hash
    public int hashCode() {
        int result = (int) (Math.pow(2, row) * Math.pow(3, col));
        return result;
    }
}
