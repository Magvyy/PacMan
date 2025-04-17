package pacman.controller;

import pacman.model.GameState;
import pacman.model.Direction;

public interface ControllableModel {
    
    /**
    * Moves the PacMan Unit in some direction if possible
    * and updates the sprite accordingly
    * @param dir the direction to move the PacMan Unit
    */
    public void movePacMan(Direction dir);
    
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
     * Sets the timer delay between each tick in the game
     * @return the time-interval between each tick in milliseconds
     */
    public int getTimerDelay();

    /**
     * Runs a set of commands at each tick in the game
     */
    public void clockTick();
}
