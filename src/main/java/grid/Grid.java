package grid;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid <E> implements IGrid <E>{
    protected int rows;
    protected int cols;
    protected ArrayList <GridCell <E>> cells;
    
    public Grid(int rows, int cols, E defaultValue) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new ArrayList<>(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                GridCell <E> cell = new GridCell <E> (pos, defaultValue);
                cells.add(cell);

            }
        }
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new ArrayList<>(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                GridCell <E> cell = new GridCell <E> (pos, null);
                cells.add(cell);

            }
        }
    }
    
    // Returns the number of rows in the Grid object
    public int getRows() {
        return rows;
    }

    // Returns the number of columns in the Grid object
    public int getCols() {
        return cols;
    }

    // Returns an iterator of the cells array
    public Iterator <GridCell <E>> iterator() {
        return cells.iterator();
    }

    // Returns the index for an element in the cells array from its CellPosition
    protected int indexFromPos(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        int index = row * cols + col;
        return index;
    }

    /**
    * Sets the value of a position in the grid. A subsequent call to {@link #get}
    * with an equal position as argument will return the value which was set. The
    * method will overwrite any previous value that was stored at the location.
    * 
    * @param pos the position in which to store the value
    * @param value the new value
    * @throws IndexOutOfBoundsException if the position does not exist in the grid
    */
    public void setValue(CellPosition pos, E value) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Position is not within the bounds of the grid.");
        }
        int index = indexFromPos(pos);
        GridCell <E> cell = new GridCell <E> (pos, value);
        cells.set(index, cell);
    }
    
    /**
    * Gets the current value at the given coordinate.
    * 
    * @param pos the position to get
    * @return the value stored at the position
    * @throws IndexOutOfBoundsException if the position does not exist in the grid
    */
    public E getValue(CellPosition pos) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Position is not within the bounds of the grid.");
        }
        int index = indexFromPos(pos);
        E value = cells.get(index).getValue();
        return value;
    }
    
    /**
    * Reports whether the position is within bounds for this grid
    * 
    * @param pos position to check
    * @return true if the coordinate is within bounds, false otherwise
    */
    public boolean positionIsOnGrid(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row > rows - 1 || col > cols - 1) {
            return false;
        }
        if (row < 0 || col < 0) {
            return false;
        }
        return true;
    }
    
}