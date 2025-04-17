package pacman.model;

import grid.CellPosition;
import grid.Grid;
import grid.GridCell;
import pacman.view.tiles.Sprite;
import pacman.view.tiles.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;;

public class PacManMaze extends Grid <Tile> {
    private HashMap <CellPosition, Integer> visited;
    private ArrayList <Direction> directions;
    private int fruits;

    /**
    * Constructor for a PacManMaze object
    * @param rows the number of rows
    * @param cols the number of columns
    * @throws Exception if the number of rows and columns are not fit for generating a proper maze
    */
    public PacManMaze(int rows, int cols) throws Exception {
        super(rows, cols);
        if (rows < 7 || cols < 9) {
            throw new Exception("Maze is too small to fit center.");
        }
        if (rows % 4 != 3 || cols % 4 != 1) {
            throw new Exception("Maze must have 11 + 4n rows and 13 + 4m columns for integers n and m.");
        }
        fruits = 0;
        initializeVariables();
        mazeGenerator();
    }

    /* 
     * Initializes the variables of the object
     */
    private void initializeVariables() {
        visited = new HashMap<>(rows * cols);
        directions = new ArrayList <Direction> ();
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.DOWN);
        directions.add(Direction.RIGHT);
    }

    /** 
     * @return the fruits variable of the object 
     */
    public int getFruits() {
        return fruits;
    }

    /** 
     * @return an iterable of the GridCells relating a tile to its position in the maze
     */
    public Iterable <GridCell <Tile>> getTiles() {
        ArrayList <GridCell <Tile>> tiles = new ArrayList<>();
        Iterator <GridCell <Tile>> iterator = this.iterator();
        while (iterator.hasNext()) {
            tiles.add(iterator.next());
        }
        return tiles;
    }

    /*
     * Generates the maze using helper methods
     */
    private void mazeGenerator() {
        initializeTiles();
        initializeVisited();
        CellPosition startPos = new CellPosition(1, 1);
        addCenter();
        DFS(startPos);
        removeDeadEnds();
        finalizeMaze();
        setFruits();
    }

    /* 
     * Sets all tiles as wall tiles, except for the tiles in a position of even row and column
     * This is to allow the procedural generation of the maze. 
     */
    private void initializeTiles() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                setValue(pos, new Tile(Sprite.WALL));
                if (i % 2 == 1 && j % 2 == 1) {
                    setValue(pos, new Tile(Sprite.FRUIT));
                }
            }
        }
    }

    /*
     * Initializes the visited hashmap, and assigns the border tiles as visited
     */
    private void initializeVisited() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                visited.put(pos, 0);
                if (isBorder(pos)) {
                    visited.put(pos, 1);
                }
            }
        }
    }

    /*
     * Adds the center structure which is 5 rows and 7 columns into the maze
     */
    private void addCenter() {
        int topRow = ((rows - 1) / 2) - 2;
        int leftCol = ((cols - 1) / 2) - 3;
        int midRow = topRow + 2;
        int midCol = leftCol + 3;
        int botRow = ((rows - 1) / 2) + 2;
        int rightCol = ((cols - 1) / 2) + 3;
        for (int i = topRow; i <= botRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                CellPosition pos = new CellPosition(i, j);
                setValue(pos, new Tile(Sprite.OPEN));
                if (i == topRow || i == botRow) {
                    setValue(pos, new Tile(Sprite.OPEN));
                }
                else if (j == leftCol || j == rightCol) {
                    setValue(pos, new Tile(Sprite.OPEN));
                }
                else if (i == midRow && midCol - 1 <= j && j <= midCol + 1) {
                    setValue(pos, new Tile(Sprite.OPEN));
                    visited.put(pos, 1);
                }
                else {
                    setValue(pos, new Tile(Sprite.WALL));
                    visited.put(pos, 1);
                }
            }
        }
    }

    /**
     * A DFS algorithm used to procedurally generate a maze, which after this step has deadends
     * @param pos the start position from which the DFS-algorithm propogates
     */
    private void DFS(CellPosition pos) {
        visited.put(pos, 1);
        ArrayList <CellPosition> neighbours = getNeighbours(pos, 2);
        Random rand = new Random();
        while (neighbours.size() != 0) {
            int index = rand.nextInt(neighbours.size());
            CellPosition neighPos = neighbours.get(index);
            neighbours.remove(index);
            if (visited.get(neighPos) == 1) {
                continue;
            }
            makePath(pos, neighPos);
            DFS(neighPos);
        }
    }

    /**
    * Retrieves the CellPositions of all tiles within the maze
    * in all four directions (up, left, down, right)
    * that are a distance dist from the Tile object at pos
    * @param pos the position of the original Tile object
    * @param dist the distance in each direction to retrieve a neighbouring Tile object
    * @return the neighbouring Tile objects of the Tile object at position pos
    */
    public ArrayList <CellPosition> getNeighbours(CellPosition pos, int dist) {
        ArrayList <CellPosition> neighbours = new ArrayList<>(4);
        for (Direction dir : directions) {
            CellPosition neighPos = peekPos(pos, dir, dist);
            if (!positionIsOnGrid(neighPos)) {
                continue;
            }
            neighbours.add(neighPos);
        }
        return neighbours;
    }

    
    /**
    * During the generation of the maze, as the DFS algorithm
    * procedurally goes from one open tile to another
    * this method removes the walls between these tiles
    * @param pos1 the position of an open Tile object
    * @param pos2 the position of a neighbouring open Tile object
    */
    private void makePath(CellPosition pos1, CellPosition pos2) {
        int row1 = pos1.getRow();
        int col1 = pos1.getCol();
        int row2 = pos2.getRow();
        int col2 = pos2.getCol();
        CellPosition wallPos = new CellPosition((row1 + row2) / 2, (col1 + col2) / 2);
        Tile tile = getValue(wallPos);
        if (tile.isWall()) {
            setValue(wallPos, new Tile(Sprite.FRUIT));
        }
    }
    
    /**
    * @param pos the position of a Tile object
    * @param dir the Direction in which we're checking
    * @param dist the distance from the pos object
    * @return the CellPosition object at a distance dist away from pos in direction dir
    */
    public CellPosition peekPos(CellPosition pos, Direction dir, int dist) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (dir == Direction.UP)    {return new CellPosition(row - dist, col       );}
        if (dir == Direction.LEFT)  {return new CellPosition(row       , col - dist);}
        if (dir == Direction.DOWN)  {return new CellPosition(row + dist, col       );}
        if (dir == Direction.RIGHT) {return new CellPosition(row       , col + dist);}
        System.out.println("Undefined direction.");
        return null;
    }

    /*
     * Removes any deadends in the maze, since those are unwanted in a PacMan game
     */
    private void removeDeadEnds() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                Tile tile = getValue(pos);
                if (!tile.isWall() && !isInCenter(pos)) {
                    removeDeadEnd(pos);
                }
            }
        }
    }

    
    /**
    * Checks if a Tile object is within the 3 open tiles in the center
    * to avoid removing the surrounding walls in the process of removing deadends
    * @param pos the position of a Tile object
    * @return a boolean indicating whether the Tile object is in the center
    */
    private boolean isInCenter(CellPosition pos) {
        int topRow = ((rows - 1) / 2) - 2;
        int leftCol = ((cols - 1) / 2) - 3;
        int botRow = ((rows - 1) / 2) + 2;
        int rightCol = ((cols - 1) / 2) + 3;
        int row = pos.getRow();
        int col = pos.getCol();
        if (topRow <= row && row <= botRow && leftCol <= col && col <= rightCol) {
            return true;
        }
        return false;
    }

    /**
    * Checks if a Tile object is part of the border (outer layer of walls)
    * to avoid removing any part of the border in the process of removing deadends
    * @param pos the position of a Tile object
    * @return a boolean indicating whether the Tile object is part of the border
    */
    private boolean isBorder(CellPosition pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row == 0 || row == rows - 1) {
            return true;
        }
        if (col == 0 || col == cols - 1) {
            return true;
        }
        return false;
    }

    /**
    * Chekcs if an open Tile object at position pos is a deadend
    * then removes on of the non-border tiles around it to remove the deadend
    * @param pos the position of an open Tile object
    */
    private void removeDeadEnd(CellPosition pos) {
        ArrayList <CellPosition> neighWalls = new ArrayList<>();
        int wallNum = 0;
        int bordNum = 0;
        for (int i = 0; i < directions.size(); i++) {
            Direction dir = directions.get(i);
            CellPosition neighPos = peekPos(pos, dir, 1);
            Tile newTile = getValue(neighPos);
            if (newTile.isWall()) {
                if (!isBorder(neighPos)) {
                    neighWalls.add(neighPos);
                    wallNum++;
                }
                else {
                    bordNum++;
                }
            }
        }
        if (wallNum + bordNum == 3) {
            Random rand = new Random();
            int index = rand.nextInt(wallNum);
            CellPosition wallPos = neighWalls.get(index);
            setValue(wallPos, new Tile(Sprite.FRUIT));
        }
    }

    /*
     * Checks all wall tiles to see in which directions they are connected
     * to other wall tiles. Used for the graphical view of the maze.
     */
    private void finalizeMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellPosition pos = new CellPosition(i, j);
                Tile tile = getValue(pos);
                if (tile.isWall()) {
                    updateWall(pos);
                }
            }
        }
    }

    /**
    * Does the work of finding the connections of a wall Tile object
    * @param pos the position of a wall Tile object
    */
    private void updateWall(CellPosition pos) {
        List <Direction> directions = Arrays.asList(Direction.values());
        int [] connections = new int[] {0, 0, 0, 0};
        for (int i = 0; i < directions.size(); i++) {
            Direction dir = directions.get(i);
            CellPosition newPos = peekPos(pos, dir, 1);
            if (!positionIsOnGrid(newPos)) {
                continue;
            }
            Tile newTile = getValue(newPos);
            if (newTile.isWall()) {
                connections[i] = 1;
            }
        }
        Tile tile = new Tile(Sprite.WALL, connections);
        setValue(pos, tile);
    }

    /*
     * Increases the fruits variable by the number of fruit tiles in the finished maze
     */
    private void setFruits() {
        for (GridCell <Tile> cell : cells) {
            Tile tile = cell.getValue();
            if (tile.getSprite() == Sprite.FRUIT) {
                fruits++;
            }
        }
    }

    /**
     * Opens the center when the ghosts are released
     */
    public void openCenter() {
        int midRow = (rows - 1) / 2;
        int midCol = (cols - 1) / 2;
        CellPosition tilePos = new CellPosition(midRow - 1, midCol);
        Tile tile = new Tile(Sprite.OPEN);
        setValue(tilePos, tile);
    }

    /** 
     * Generates a new maze with (probably) new dimensions
     * @param rows the amount of rows
     * @param cols the amount of columns
     */
    public void newMaze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        visited = new HashMap<>(rows * cols);
        mazeGenerator();
    }
    
    /**
     * Generates a new maze with the same dimensions
     */
    public void newMaze() {
        visited = new HashMap<>(rows * cols);
        mazeGenerator();
    }
}
