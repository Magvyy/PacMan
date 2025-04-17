package pacman.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import grid.CellPosition;
import grid.GridCell;
import grid.GridDimension;
import pacman.controller.ControllableModel;
import pacman.model.units.Unit;
import pacman.model.units.UnitType;
import pacman.view.ViewableModel;
import pacman.view.tiles.Sprite;
import pacman.view.tiles.Tile;

public class PacManModel implements ViewableModel, ControllableModel {
    private PacManMaze maze;
    private Unit pacMan;
    private ArrayList <Unit> ghosts;
    private GameState state;
    private boolean ghostsReleased;
    private int tickCount;
    private int score;

    /**
     * Constructor for a PacManModel object
     * @param maze a PacManMaze object used by the model
     * @param cols the number of columns
     * @throws Exception if the number of rows and columns are not fit for generating a proper maze
     */
    public PacManModel(int rows, int cols) throws Exception {
        this.maze = new PacManMaze(rows, cols);;
        ghostsReleased = false;
        tickCount = 0;
        score = 0;
        state = GameState.ACTIVE_GAME;
        initializePacMan();
        initializeGhosts();
    }

    /**
     * @return a GridDimension object with the dimensions of the PacManModel object
     */
    public GridDimension getDimension() {
        return maze;
    }

    /** 
     * @return the PacMan Unit 
     */
    public Unit getPacMan() {
        return pacMan;
    }

    /** 
     * @return the Array of ghost Units 
     */
    public ArrayList <Unit> getGhosts() {
        return ghosts;
    }

    /** 
     * @return the GameState
     */
    public GameState getGameState() {
        return state;
    }

    /**
     * Sets the GameState
     * @param state the new GameState
     */
    public void setGameState(GameState state) {
        this.state = state;
    }

    /** 
     * @return an iterable of the GridCells relating a tile to its position in the maze 
     */
    public Iterable <GridCell <Tile>> getTiles() {
        return maze.getTiles();
    }

    
    /** 
     * @return the score of the player in order to display it 
     */
    public int getScore() {
        return score;
    }

    /* 
     * Initializes the PacMan Unit
     */
    private void initializePacMan() {
        ArrayList <CellPosition> positions = new ArrayList<>();
        for (GridCell <Tile> cell : getTiles()) {
            CellPosition pos = cell.getPos();
            Tile tile = cell.getValue();
            Sprite sprite = tile.getSprite();
            if (sprite == Sprite.FRUIT) {
                positions.add(pos);
            }
        }
        Random rand = new Random();
        int index = rand.nextInt(positions.size());

        UnitType type = UnitType.PLAYER;
        CellPosition unitPos = positions.get(index);
        int health = 1;
        Sprite sprite = Sprite.PACMAN_RIGHT;
        this.pacMan = new Unit(type, unitPos, health, sprite);
        maze.setValue(unitPos, new Tile(Sprite.OPEN));
        score++; // In the original submission this was not here and caused a bug where a new level was not generated once all fruits were eaten
    }

    /* 
     * Initializes the ghost units
     */
    private void initializeGhosts() {
        ghosts = new ArrayList<>(4);
        int midRow = (maze.getRows() - 1) / 2;
        int midCol = (maze.getCols() - 1) / 2;
        UnitType type = UnitType.ENEMY;
        CellPosition blinkyPos = new CellPosition(midRow - 2, midCol);
        CellPosition pinkyPos  = new CellPosition(midRow, midCol);
        CellPosition inkyPos   = new CellPosition(midRow, midCol - 1);
        CellPosition clydePos  = new CellPosition(midRow, midCol + 1);
        int health = 1;
        Sprite blinkySprite = Sprite.BLINKY;
        Sprite pinkySprite  = Sprite.PINKY;
        Sprite inkySprite   = Sprite.INKY;
        Sprite clydeSprite  = Sprite.CLYDE;
        Unit blinky = new Unit(type, blinkyPos, health, blinkySprite);
        Unit pinky  = new Unit(type, pinkyPos,  health, pinkySprite);
        Unit inky   = new Unit(type, inkyPos,   health, inkySprite);
        Unit clyde  = new Unit(type, clydePos,  health, clydeSprite);
        ghosts.add(blinky);
        ghosts.add(pinky);
        ghosts.add(inky);
        ghosts.add(clyde);
    }

    /**
     * Moves the PacMan Unit in some direction if possible
     * and updates the sprite accordingly
     * @param dir the direction to move the PacMan Unit
     */
    public void movePacMan(Direction dir) {
        CellPosition pos = pacMan.getPos();
        CellPosition peekPos = maze.peekPos(pos, dir, 1);
        Tile tile = maze.getValue(peekPos);
        if (tile.isWall()) {
            return;
        }
        if (dir == Direction.UP) {
            pacMan.setSprite(Sprite.PACMAN_UP);
        }
        else if (dir == Direction.LEFT) {
            pacMan.setSprite(Sprite.PACMAN_LEFT);
        }
        else if (dir == Direction.DOWN) {
            pacMan.setSprite(Sprite.PACMAN_DOWN);
        }
        else {
            pacMan.setSprite(Sprite.PACMAN_RIGHT);
        }

        CellPosition newPos = maze.peekPos(pos, dir, 1);
        pacMan.setPos(newPos);
        for (Unit ghost : ghosts) {
            CellPosition ghostPos = ghost.getPos();
            if (ghostPos.equals(newPos)) {
                state = GameState.GAME_OVER;
            }
        }
        eatFruit();
    }

    /*
     * Increases the score and updates the sprite of a Fruit Tile if the PacMan Unit crosses it  
     */
    private void eatFruit() {
        CellPosition pos = pacMan.getPos();
        Tile tile = maze.getValue(pos);
        if (tile.getSprite() == Sprite.FRUIT) {
            tile.setSprite(Sprite.OPEN);
            maze.setValue(pos, tile);
            score++;
        }
    }

    /*
     * Moves a ghost Unit to a new position
     */
    private void moveGhost(Unit ghost, CellPosition newPos) {
        for (Unit otherGhost : ghosts) {
            if (otherGhost.equals(ghost)) {
                continue;
            }
            CellPosition ghostPos = otherGhost.getPos();
            if (newPos.equals(ghostPos)) {
                return;
            }
        }

        ghost.setPos(newPos);
        CellPosition pacPos = pacMan.getPos();
        if (pacPos.equals(newPos)) {
            state = GameState.GAME_OVER;
        }
    }

    /* 
     * Moves each available ghost unit along the shortest path towards the PacMan unit
     * if the ghosts are not yet released, only BLINKY will chase after the PacMan unit
     */
    private void ghostChase() {
        if (ghostsReleased) {
            for (Unit ghost : ghosts) {
                CellPosition ghostPos = ghost.getPos();
                CellPosition newPos = nextPosition(ghostPos);
                moveGhost(ghost, newPos);
            }
            return;
        }
        for (Unit ghost : ghosts) {
            if (ghost.getSprite() == Sprite.BLINKY) {
                CellPosition ghostPos = ghost.getPos();
                CellPosition newPos = nextPosition(ghostPos);
                moveGhost(ghost, newPos);
            }
        }
    }

    /*
     * Uses a breadth-first search algorithm and returns the next position
     * along the shortest path towards the PacMan Unit from the given
     * position, which in this case is the position of some ghost unit
     */
    private CellPosition nextPosition(CellPosition ghostPos) {
        // The original submission included a class with helping methods because I originally thought I'd need to use a bfs algorithm so I made it a little cleaner
        ArrayList <CellPosition> queue;
        HashMap <CellPosition, Integer> visited;
        HashMap <CellPosition, CellPosition> closestPos;
        queue = new ArrayList<>();
        closestPos = new HashMap<>(maze.getRows() * maze.getCols());
        visited = new HashMap<>(maze.getRows() * maze.getCols());
        for (GridCell <Tile> cell : getTiles()) {
            CellPosition pos = cell.getPos();
            Tile tile = cell.getValue();
            Sprite sprite = tile.getSprite();
            visited.put(pos, 0);
            if (sprite == Sprite.WALL) {
                visited.put(pos, 1);
            }
        }

        CellPosition pacPos = pacMan.getPos();
        visited.put(pacPos, 1);
        queue.add(pacPos);
        while (!queue.isEmpty()) {
            CellPosition currPos = queue.get(0);
            queue.remove(0);
            ArrayList <CellPosition> neighbours = maze.getNeighbours(currPos, 1);
            for (CellPosition neighPos : neighbours) {
                if (visited.get(neighPos) == 1) {
                    continue;
                }
                closestPos.put(neighPos, currPos);
                queue.add(neighPos);
                visited.put(neighPos, 1);
            }
        }
        return closestPos.get(ghostPos);
    }

    /* 
     * After 10 ticks, the trapped ghost units in the center are released
     */
    private void releaseGhosts() {
        ghostsReleased = true;
        maze.openCenter();
    }

    /** 
     * Generates a new maze with (probably) new dimensions
     * @param rows the amount of rows
     * @param cols the amount of columns
     */
    public void newLevel(int rows, int cols) {
        maze.newMaze(rows, cols);
        ghostsReleased = false;
        initializePacMan();
        initializeGhosts();
    }

    /*
     * Generates a new maze with the same dimensions
     */
    private void newLevel() {
        this.maze.newMaze();
        ghostsReleased = false;
        initializePacMan();
        initializeGhosts();
    }

    /** 
     * Sets the timer delay between each tick in the game
     * @return the time-interval between each tick in milliseconds
     */
    public int getTimerDelay() {
        return 500;
    }   

    /**
     * Runs a set of commands at each tick in the game
     */
    public void clockTick() {
        if (score == maze.getFruits()) {
            tickCount = 0;
            newLevel();
        }
        if (tickCount < 10) {
            tickCount++;
        }
        else if (!ghostsReleased) {
            releaseGhosts();
        }
        ghostChase();
    }
}
