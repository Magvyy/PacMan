package pacman.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import pacman.midi.PacManSong;
import pacman.model.Direction;
import pacman.model.GameState;
import pacman.view.PacManGameView;

public class Controller implements KeyListener {
    private ControllableModel controller;
    private PacManGameView game;
    private Timer timer;
    private PacManSong song;
    
    /**
     * The constructor of a controller Object that ties together
     * a ControllableModel Object and a PacManGameView Object
     * in order to let the player play the game
     * @param controller the ControllableModel Object
     * @param game the PacManGameView Object
     */
    public Controller(ControllableModel controller, PacManGameView game) {
        this.controller = controller;
        this.game = game;
        game.addKeyListener(this);
        this.timer = new Timer(controller.getTimerDelay(), this::clockTick);
        song = new PacManSong();
        song.run();
        timer.start();
    }
    
    // Not in use
    public void keyTyped(KeyEvent key) {
    }

    // Not in use
    public void keyReleased(KeyEvent key) {
    }

    /**
     * Provides a set of keyboard keys methods that run once pressed
     */
    public void keyPressed(KeyEvent key) {
        if (controller.getGameState() == GameState.GAME_OVER) {
            return;
        }
        if (key.getKeyCode() == KeyEvent.VK_UP) {
            controller.movePacMan(Direction.UP);
        }
        else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
            controller.movePacMan(Direction.LEFT);
        }
        else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
            controller.movePacMan(Direction.DOWN);
        }
        else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
            controller.movePacMan(Direction.RIGHT);
        }
        game.repaint();
    }

    /**
     * Sets the initial and in-between event delay for the timer.
     * Could be used to increase or decrease the tick-rate to
     * decrease or increase difficulty.
     * @param timerDelay the new timer-intervall between each tick of the game
     */
    public void setNewTimerDelay(int timerDelay) {
        timer.setDelay(timerDelay);
        timer.setInitialDelay(timerDelay);
    }

    /**
     * This implementation checks the GameState and terminates the timer
     * if the game is over. Else it calls to the clockTick() in the ControllableModel class
     * which moves the ghost Units one block closer to the PacMan Unit
     * @param tick the tick of the Timer Object
     */
    public void clockTick(ActionEvent tick) {
        GameState state = controller.getGameState();
        if (state == GameState.GAME_OVER) {
            game.repaint();
            song.doStopMidiSounds();
            timer.stop();
            return;
        }
        controller.clockTick();
        game.repaint();
    }
}
