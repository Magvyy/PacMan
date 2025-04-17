package pacman.view;

import java.util.ArrayList;

import grid.GridCell;
import grid.GridDimension;
import pacman.model.GameState;
import pacman.model.units.Unit;
import pacman.view.tiles.*;

public interface ViewableModel {

    /**
     * @return a GridDimension object with the dimensions of the PacManModel object
     */
    public GridDimension getDimension();

    /** 
     * @return the PacMan Unit 
     */
    public Unit getPacMan();

    /** 
     * @return the Array of ghost Units 
     */
    public ArrayList <Unit> getGhosts();

    /** 
     * @return the GameState
     */
    public GameState getGameState();

    /**
     * Sets the GameState
     * @param state the new GameState
     */
    public void setGameState(GameState state);

    /** 
     * @return an iterable of the GridCells relating a tile to its position in the maze 
     */
    public Iterable <GridCell <Tile>> getTiles();

    /** 
     * @return the score of the player in order to display it 
     */
    public int getScore();

    /** 
     * Generates a new maze with (probably) new dimensions
     * @param rows the amount of rows
     * @param cols the amount of columns
     */
    public void newLevel(int rows, int cols);
}
