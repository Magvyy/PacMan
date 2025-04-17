package pacman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import grid.CellPosition;
import grid.GridCell;
import pacman.view.tiles.Sprite;
import pacman.view.tiles.Tile;

public class PacManMazeTest {

    @Test
    /*
     * The maze must have a center, and every open tile must be reachable, other
     * than those in the center where the 3 ghosts are. There must also be no dead-ends.
     * Though unfortunately this requirement does not prevent choke-points within the generated maze.
     */
    void hasCenter() throws Exception {
        PacManMaze maze = new PacManMaze(15, 13);
        int rows = maze.getRows();
        int cols = maze.getCols();
        int midRow = (rows - 1) / 2;
        int midCol = (cols - 1) / 2;
        // The center columns range from -3 to +3 compared to midCol and rows range from -2 to +2 from midRow
        // It has 3 layers, the outer layer consists of open tiles, the middle layer of walls, and inner layer of open tiles.
        for (int i = midRow - 2; i < midRow + 3; i++) {
            for (int j = midCol - 3; j < midCol + 4; j++) {
                CellPosition pos = new CellPosition(i, j);
                if (i == midRow - 2 || i == midRow + 2 || j == midCol - 3 || j == midCol + 3) { // Outer layer
                    Tile tile = maze.getValue(pos);
                    assertTrue(tile.getSprite() == Sprite.OPEN);
                }
                else if (i == midRow && midCol - 1 <= j && j <= midCol + 1) { // Inner layer
                    Tile tile = maze.getValue(pos);
                    assertTrue(tile.getSprite() == Sprite.OPEN);
                }
                else { // Middle layer
                    Tile tile = maze.getValue(pos);
                    assertTrue(tile.getSprite() == Sprite.WALL);
                }
            }
        }
    }


    @Test
    void everyOpenTileReachable() throws Exception {
        PacManMaze maze = new PacManMaze(15, 13);
        HashMap <CellPosition, Integer> visited = new HashMap<>();
        ArrayList <CellPosition> queue = new ArrayList<>();
        int openTiles = -3; // Initialized at -3 for the 3 open tiles in the center
        for (GridCell <Tile> cell : maze.getTiles()) {
            CellPosition tilePos = cell.getPos();
            Tile tile = cell.getValue();
            if (tile.getSprite() != Sprite.WALL) {
                visited.put(tilePos, 0);
                openTiles++;
            }
        }

        CellPosition startPos = new CellPosition(1, 1);
        queue.add(startPos);
        visited.put(startPos, 1);
        int reachedTiles = 1;
        while (!queue.isEmpty()) {
            CellPosition currPos = queue.get(0);
            queue.remove(0);
            ArrayList <CellPosition> neighbours = maze.getNeighbours(currPos, 1);
            for (CellPosition neighPos : neighbours) {
                Tile neighTile = maze.getValue(neighPos);
                Sprite neighSprite = neighTile.getSprite();
                if (neighSprite == Sprite.WALL) {
                    continue;
                }
                if (visited.get(neighPos) == 1) {
                    continue;
                }
                reachedTiles++;
                queue.add(neighPos);
                visited.put(neighPos, 1);
            }
        }
        assertEquals(openTiles, reachedTiles); 
    }

    @Test
    void hasNoDeadEnds() throws Exception {
        PacManMaze maze = new PacManMaze(15, 13);
        int midRow = (maze.getRows() - 1) / 2;
        int midCol = (maze.getCols() - 1) / 2;
        boolean hasDeadEnd = false;
        for (GridCell <Tile> cell : maze.getTiles()) {
            CellPosition tilePos = cell.getPos();
            int row = tilePos.getRow();
            int col = tilePos.getCol();
            if (row == midRow && midCol - 1 <= col && col <= midCol + 1) {
                continue;
            }
            Tile tile = cell.getValue();
            if (tile.getSprite() != Sprite.WALL) {
                int routes = 0;
                ArrayList <CellPosition> neighbours = maze.getNeighbours(tilePos, 1);
                for (CellPosition neighPos : neighbours) {
                    Tile neighTile = maze.getValue(neighPos);
                    if (neighTile.getSprite() != Sprite.WALL) {
                        routes++;
                    }
                }
                if (routes <= 1) {
                    hasDeadEnd = true;
                }
            }
        }
        assertFalse(hasDeadEnd);
    }
}
