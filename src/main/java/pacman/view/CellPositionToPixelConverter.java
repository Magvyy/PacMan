package pacman.view;

import java.awt.geom.Rectangle2D;
import grid.CellPosition;
import grid.GridDimension;

// This class is pretty much ripped as is, with a few modifications, from assignment 1
public class CellPositionToPixelConverter {
	private Rectangle2D box;
	private GridDimension gd;
	
	public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd) {
		this.box = box;
		this.gd = gd;
	}

    public Rectangle2D box() {
        return box;
    }

    public GridDimension gd() {
        return gd;
    }

	public Rectangle2D getBoundsForCell(CellPosition pos) {
		int rows = gd.getRows();
		int cols = gd.getCols();
		int row = pos.getRow();
		int col = pos.getCol();
		double width = box.getBounds().getWidth();
		double height = box.getBounds().getHeight();
		double cellWidth = width / cols;
		double cellHeight = height / rows;
		double cellx = box.getX() + cellWidth * col;
		double celly = box.getY() + cellHeight * row;
		Rectangle2D.Double cell = new Rectangle2D.Double(cellx, celly, cellWidth, cellHeight);
		return cell;
	}
}
