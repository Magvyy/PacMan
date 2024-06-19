package Java.Main.Model.Maze;

import static Java.Main.Model.Maze.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import Java.Main.Model.Grid.Cell;
import Java.Main.Model.Grid.CellPosition;
import Java.Main.Model.Grid.Dimensions;
import Java.Main.Model.Grid.Grid;
import Java.Main.View.Sprites.Sprite;
import Java.Main.View.Sprites.Tiles.Walkable.OpenType;
import Java.Main.View.Sprites.Tiles.Walkable.Walkable;
import Java.Main.View.Sprites.Tiles.Walls.Wall;

public class Maze {
    private static final ArrayList <Direction> directions = new ArrayList<>(Arrays.asList(UP, LEFT, DOWN, RIGHT));
    private Grid grid;
    private int fruits;

    public Maze(int rows, int cols) {
        if (rows % 4 != 3 || cols % 4 != 1 || rows < 11 || cols < 13) {
            System.out.println("Maze must have 11 + 4n rows and 13 + 4m columns.");
        }

        grid = new Grid(rows, cols);
        fruits = 0;
        mazeGenerator();
    }

    public int getFruits() {
        return fruits;
    }

    public void setFruits(int fruits) {
        this.fruits = fruits;
    }

    public Dimensions getDimensions() {
        return grid;
    }

    public Iterable <Cell> getCells() {
        return grid.getCells();
    }

    private void mazeGenerator() {
        initializeGrid();
        HashMap <CellPosition, Integer> visited = initializeVisited();
        CellPosition startPos = new CellPosition(1, 1);
        DFSMaze(visited, startPos);
        DFSClearDeadEnds(visited, startPos, 10);
//        removeDeadEnds();
//        addCenter();
        updateWallConnections();
    }

    private void initializeGrid() {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                CellPosition pos = new CellPosition(row, col);
                Cell cell = new Cell(pos, new Wall());
                if (row % 2 == 1 && col % 2 == 1) {
                    cell = new Cell(pos, new Walkable(OpenType.OPEN));
                }
                grid.addCell(cell);
            }
        }
    }

    private HashMap <CellPosition, Integer> initializeVisited() {
        HashMap <CellPosition, Integer> visited = new HashMap<>();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                CellPosition pos = new CellPosition(row, col);
                visited.put(pos, 0);
            }
        }
        return visited;
    }

    private void DFSMaze(HashMap <CellPosition, Integer> visited, CellPosition currPos) {
        ArrayList <CellPosition> neighbours = getNeighbours(currPos, 2);
        Random rand = new Random();
        while (!neighbours.isEmpty()) {
            int index = rand.nextInt(neighbours.size());
            CellPosition neighPos = neighbours.get(index);
            if (visited.get(neighPos) == 1) {
                neighbours.remove(index);
                continue;
            }
            visited.put(neighPos, 1);
            removeWall(currPos, neighPos);
            DFSMaze(visited, neighPos);
        }
    }

    private ArrayList <CellPosition> getNeighbours(CellPosition currPos, int dist) {
        ArrayList <CellPosition> neighbours = new ArrayList<>();
        for (Direction direction : directions) {
            CellPosition peekPos = peek(currPos, direction, dist);
            if (isOutOfBounds(peekPos)) {
                continue;
            }
            neighbours.add(peekPos);
        }
        return neighbours;
    }

    private ArrayList <CellPosition> getNeighWalls(CellPosition currPos, int dist) {
        ArrayList <CellPosition> neighbours = getNeighbours(currPos, dist);
        int size = neighbours.size();
        for (int i = 0; i < size; i++) {
            CellPosition neighPos = neighbours.get(i);
            if (grid.getSprite(neighPos).isWalk()) {
                neighbours.remove(i);
                size--;
                i--;
            }
        }
        return neighbours;
    }

    private ArrayList <CellPosition> getNeighWalks(CellPosition currPos, int dist) {
        ArrayList <CellPosition> neighbours = getNeighbours(currPos, dist);
        int size = neighbours.size();
        for (int i = 0; i < size; i++) {
            CellPosition neighPos = neighbours.get(i);
            if (grid.getSprite(neighPos).isWall()) {
                neighbours.remove(i);
                size--;
                i--;
            }
        }
        return neighbours;
    }

    private CellPosition peek(CellPosition pos, Direction direction, int dist) {
        int row = pos.getRow();
        int col = pos.getCol();
        CellPosition peekPos = switch (direction) {
            case UP    -> new CellPosition(row - dist, col);
            case LEFT  -> new CellPosition(row, col - dist);
            case DOWN  -> new CellPosition(row + dist, col);
            case RIGHT -> new CellPosition(row, col + dist);
            default -> pos;
        };
        if (peekPos.equals(pos)) {
            System.out.println("Invalid direction.");
        }
        return peekPos;
    }

    private boolean isOutOfBounds(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row < 0 || row >= grid.getRows()) {
            return true;
        }
        if (col < 0 || col >= grid.getCols()) {
            return true;
        }
        return false;
    }

    private void removeWall(CellPosition pos1, CellPosition pos2) {
        int row1 = pos1.getRow();
        int row2 = pos2.getRow();
        int col1 = pos1.getCol();
        int col2 = pos2.getCol();
        int midRow = (row1 + row2) / 2;
        int midCol = (col1 + col2) / 2;
        CellPosition midPos = new CellPosition(midRow, midCol);
        grid.setSprite(midPos, new Walkable(OpenType.OPEN));
    }

    private void BFSClearDeadEnds(CellPosition startPos, int threshold) {
        CellPosition currPos = startPos;
        HashMap <CellPosition, Integer> distances = new HashMap<>();
        distances.put(currPos, 0);
        ArrayList <CellPosition> queue1 = new ArrayList<>();
        queue1.add(currPos);
        while (!queue1.isEmpty()) {
            queue1.remove(0);
            ArrayList <CellPosition> neighWalks1 = getNeighWalks(currPos, 2);
            for (CellPosition neighPos1 : neighWalks1) {
                ArrayList <CellPosition> queue2 = new ArrayList<>();
                queue2.add(currPos);
                while (!queue2.isEmpty()) {
                    queue2.remove(0);
                    ArrayList <CellPosition> neighWalks2 = getNeighWalks(currPos, 1);
                    for (CellPosition neighPos2 : neighWalks2) {
                        queue2.add(neighPos2);
                    }

                }
            }
        }
    }

    private int DFSClearDeadEnds(HashMap <CellPosition, Integer> visited, CellPosition currPos, int threshhold) {
        ArrayList <CellPosition> subVisited = new ArrayList<>();
        int subnodes = 0;
        ArrayList <CellPosition> neighbours = getNeighWalks(currPos, 1);
        Random rand = new Random();
        while (!neighbours.isEmpty()) {
            int index = rand.nextInt(neighbours.size());
            CellPosition neighPos = neighbours.get(index);
            if (visited.get(neighPos) == 1) {
                subVisited.add(neighPos);
                neighbours.remove(index);
                continue;
            }
            subnodes++;
            visited.put(neighPos, 1);
            subnodes += DFSClearDeadEnds(visited, neighPos, threshhold);
        }
        if (subnodes % threshhold == 0) {
            ArrayList <CellPosition> subTree = subTree(subVisited, currPos);
            removeDeadEnd(currPos);
        }
        return subnodes;
    }

    private ArrayList <CellPosition> subTree(ArrayList <CellPosition> visited, CellPosition currPos) {
        ArrayList <CellPosition> neighbours = getNeighWalks(currPos, 1);
        visited.add(currPos);
        int index = 0;
        while (!neighbours.isEmpty()) {
            CellPosition neighPos = neighbours.get(index);
            if (visited.contains(neighPos)) {
                neighbours.remove(index);
                index--;
                continue;
            }
            index++;
            subTree(visited, neighPos);
        }
        return neighbours;
    }

    private void removeDeadEnds() {
        for (Cell cell : grid.getCells()) {
            CellPosition pos = cell.getPos();
            Sprite sprite = cell.getSprite();
            if (!sprite.isWalk()) {
                continue;
            }
            if (isCenter(pos)) {
                continue;
            }
            if (isDeadEnd(pos)) {
                removeDeadEnd(pos);
            }

        }
    }

    private boolean isCenter(CellPosition pos) {
        int midRow = (grid.getRows() - 1) / 2;
        int midCol = (grid.getCols() - 1) / 2;
        int row = pos.getRow();
        int col = pos.getCol();
        return row == midRow && midCol - 1 <= col && col <= midCol + 1;
    }

    private boolean isDeadEnd(CellPosition pos) {
        ArrayList <CellPosition> neighbours = getNeighbours(pos, 1);
        int wallCount = 0;
        for (CellPosition neighPos : neighbours) {
            if (grid.getSprite(neighPos).isWall()) {
                wallCount++;
            }
        }
        return wallCount == 3;
    }

    private void removeDeadEnd(CellPosition pos) {
        ArrayList <CellPosition> neighWalls = getNeighWalls(pos, 1);
        int size = neighWalls.size();
        for (int i = 0; i < size; i++) {
            CellPosition wallPos = neighWalls.get(i);
            if (isBorder(wallPos)) {
                neighWalls.remove(i);
                size--;
                i--;
            }
        }
        Random rand = new Random();
        int index = rand.nextInt(neighWalls.size());
        CellPosition wallPos = neighWalls.get(index);
        grid.setSprite(wallPos, new Walkable(OpenType.OPEN));
    }

    private boolean isBorder(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row == 0 || row == grid.getRows() - 1) {
            return true;
        }
        if (col == 0 || col == grid.getCols() - 1) {
            return true;
        }
        return false;
    }

    private void addCenter() {
        int midRow = (grid.getRows() - 1) / 2;
        int midCol = (grid.getCols() - 1) / 2;
        CellPosition centerPos;
        for (int row = midRow - 2; row <= midRow + 2; row++) {
            for (int col = midCol - 3; col <= midCol + 3; col++) {
                centerPos = new CellPosition(row, col);
                grid.setSprite(centerPos, new Walkable(OpenType.OPEN));
            }
        }
        for (int row = midRow - 1; row <= midRow + 1; row++) {
            for (int col = midCol - 2; col <= midCol + 2; col++) {
                centerPos = new CellPosition(row, col);
                grid.setSprite(centerPos, new Wall());
            }
        }
        for (int col = midCol - 1; col <= midCol + 1; col++) {
            centerPos = new CellPosition(midRow, col);
            grid.setSprite(centerPos, new Walkable(OpenType.OPEN));
        }
    }

    private void updateWallConnections() {
        for (Cell cell : grid.getCells()) {
            CellPosition pos = cell.getPos();
            Sprite sprite = cell.getSprite();
            if (sprite.isWall()) {
                getWallConnections(pos);
            }
        }
    }

    private void getWallConnections(CellPosition wallPos) {
        ArrayList <CellPosition> neighWalls = getNeighWalls(wallPos, 1);
        int [] connections = new int [4];
        for (CellPosition neighWall : neighWalls) {
            int index = getIndex(wallPos, neighWall);
            connections[index] = 1;
        }
        grid.setSprite(wallPos, new Wall(connections));
    }

    private int getIndex(CellPosition currPos, CellPosition neighPos) {
        int currRow = currPos.getRow();
        int currCol = currPos.getCol();
        int neighRow = neighPos.getRow();
        int neighCol = neighPos.getCol();
        if (neighRow < currRow) {
            return 0;
        }
        if (neighRow > currRow) {
            return 2;
        }
        if (neighCol < currCol) {
            return 1;
        }
        return 3;
    }
}
