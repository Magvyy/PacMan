package grid;

public class GridCell <E> {
    private final CellPosition pos;
    private final E value;

    public GridCell(CellPosition pos, E value) {
        this.pos = pos;
        this.value = value;
    }

    // Returns the CellPosition of the GridCell object
    public CellPosition getPos() {
        return pos;
    }

    // Returns the value of the GridCell object
    public E getValue() {
        return value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GridCell)) {
            return false;
        }

        GridCell <E> b = (GridCell <E>) o;
        return this.pos.equals(b.getPos()) && this.value.equals(b.getValue());
    }

    public int hashCode() {
        int result = pos.hashCode() + value.hashCode();
        return result;
    }
}
