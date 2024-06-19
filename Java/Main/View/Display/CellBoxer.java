package Java.Main.View.Display;

import java.awt.Rectangle;
import Java.Main.Model.Grid.CellPosition;
import Java.Main.Model.Grid.Dimensions;

public class CellBoxer {
    
    public CellBoxer() {

    }

    public Rectangle cellBox(Dimensions dimensions, CellPosition pos, int xPixels, int yPixels) {
        int row = pos.getRow();
        int col = pos.getCol();
        int rows = dimensions.getRows();
        int cols = dimensions.getCols();
        int width = xPixels / cols;
        int height = yPixels / rows;
        int xPos = col * width;
        int yPos = row * height;
        return new Rectangle(xPos, yPos, width, height);
    }
}
