package pacman;

import javax.swing.JButton;
import javax.swing.JFrame;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pacman.controller.Controller;
import pacman.model.PacManModel;
import pacman.view.PacManGameView;

// Note that the general layout, that is, the controller-view-model approach is taken from the first assignment
// Some parts, like the grid package, are taken directly with few to no modifications
// This also counts for a couple of the interfaces (ControllableModel and ViewableModel), as well as CellPositionToPixelConverter
// Note I couldn't find midi files of the pacman theme that worked so I used the tetris midi from the previous assignment
public class Main {
  public static final String WINDOW_TITLE = "PacMan";
  
  public static void main(String[] args) throws Exception {
    // The JFrame is the "root" application window.
    // We here set som properties of the main window, 
    // and tell it to display our tetrisView
    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setPreferredSize(new Dimension(665, 630));
    frame.setVisible(true);

    int width = 150;
    int margin = 50;
    Container container = frame.getContentPane();
    container.setLayout(null);
    JButton smlButton = new JButton("Small Level");
    JButton medButton = new JButton("Medium Level");
    JButton bigButton = new JButton("Large Level");
    smlButton.addMouseListener(chosenMap(frame, 15, 17));
    medButton.addMouseListener(chosenMap(frame, 19, 21));
    bigButton.addMouseListener(chosenMap(frame, 23, 25));
    smlButton.setBounds(margin + 0 * (width + margin), 300, width, margin);
    medButton.setBounds(margin + 1 * (width + margin), 300, width, margin);
    bigButton.setBounds(margin + 2 * (width + margin), 300, width, margin);
    container.add(smlButton);
    container.add(medButton);
    container.add(bigButton);

    JButton quit = new JButton("Quit?");
    quit.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { System.exit(0); }});
    quit.setBounds(margin + 1 * (width + margin), 400, width, margin);
    container.add(quit);
    frame.pack();
  }

  /**
   * A MouseAdapter Object used by the above
   * main method tocreate the buttons
   * @param frame the frame in which a button will be placed
   * @param rows the number of rows of the maze created by clicking the button
   * @param cols the number of columns of the maze created by clicking the button
   * @return the MouseAdapter Object used by a JButton object
   * @throws Exception because PacManMaze can throw an exception of the number of rows or columns doesn't fit
   */
  private static MouseAdapter chosenMap(JFrame frame, int rows, int cols) throws Exception {
      return new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          try {
            int side = 25;
            PacManModel model = new PacManModel(rows, cols);
            PacManGameView game = new PacManGameView(model, side);
            Controller controller = new Controller(model, game);
            Container container = frame.getContentPane();
            frame.addKeyListener(controller);
            container.removeAll();
            frame.revalidate();
            frame.setContentPane(game);
            int width = cols * side + 15;
            int heigth = rows * side + 40;
            frame.setPreferredSize(new Dimension(width, heigth));
            frame.pack();
          } catch (Exception except) {System.out.println(except);}
        }
    };
  }
}
