/*
 * TCSS 305
 * Assignment 6 - Tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**
 * The Tetris GUI sets up the frame and board.
 * 
 * @author Arrunn Chhouy
 * @version 1.0
 */
public class TetrisGUI implements ActionListener, Observer  {
    /**
     * The initial frames per second at which the simulation will run.
     */
    private static final int INITIAL_FRAMES_PER_SECOND = 1;
    
    /**
     * The numerator for delay calculations.
     */
    private static final int MY_DELAY_NUMERATOR = 1000;
    
    /**
     * The window for Tetris.
     */
    private JFrame myFrame;
    
    /**
     * The board for Tetris.
     */
    private Board myBoard;
    
    /**
     *  Tetris board panel.
     */
    private ScoringPanel myScorePanel;
    
    /**
     * A timer used to update the state of the simulation.
     */
    private final Timer myTimer;
    
    /**
     * The pause timer.
     */
    private boolean myPause;
    
    /**
     * Creates a tetris GUI and starts the timer.
     */
    public TetrisGUI() {
        myTimer = new Timer(MY_DELAY_NUMERATOR / INITIAL_FRAMES_PER_SECOND, this);
        myPause = false;
    }
    
    /**
     * Starts the frame.
     */
    public void start() {
        setUpFrame();
        adjustFrame();
    }

    /**
     * Creates the JFrame.
     */
    private void setUpFrame() {
        // Creates a new JFrame object which will be the main window.
        myFrame = new JFrame("Tetris");
        
        // Sets the frame to a border layout
        myFrame.setLayout(new BorderLayout());
        
        // Creates a new Board
        myBoard = new Board();
        
        final TetrisBoardPanel boardPanel = new TetrisBoardPanel();
        
        final TetrisPiecePanel piecePanel = new TetrisPiecePanel();
        
        myScorePanel = new ScoringPanel(myTimer);
        
        // Creates the menu bar
        final TetrisMenuBar menuBar = new TetrisMenuBar(myBoard, myFrame, myTimer,
                                                        myScorePanel, boardPanel);
        myFrame.setJMenuBar(menuBar);
        
        
        final JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(piecePanel);
        panel.add(myScorePanel);
        
        // Adds the observers to the Board so that they can be notified. 
        myBoard.addObserver(this);
        myBoard.addObserver(boardPanel);
        myBoard.addObserver(piecePanel);
        myBoard.addObserver(myScorePanel);
        
        // Adds the panel to the frame.
        myFrame.add(boardPanel, BorderLayout.CENTER);
        myFrame.add(panel, BorderLayout.EAST);
        
        myFrame.setFocusable(true);
        myFrame.requestFocus();
        myFrame.addKeyListener(new MyKeyListener());
        
        // Initializes the first piece
        myBoard.newGame();
        
        // Starts the timer
        myTimer.start();
        
        // Exits the application
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Compacts and centers the frames and allows visibility.
     */
    private void adjustFrame() {
        myFrame.setResizable(false);
        
        // Compacts the window to the size of the content
        myFrame.pack();
        
        // Centers the window to the screen
        myFrame.setLocationRelativeTo(null);
        
        // Allows visibility of the window
        myFrame.setVisible(true);
    }

    /**
     * Causes the board to step through the timer.
     * 
     * @param theEvent is the event.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myBoard.step();
    }

    @Override
    public void update(final Observable theObservable, final Object theObject) {
        boolean gameOver = false;
        if (theObject instanceof Boolean) {
            gameOver = ((Boolean) theObject).booleanValue();
            if (gameOver) {
                JOptionPane.showMessageDialog(myFrame,
                            "Game Over!");
                myTimer.stop();
            }
        }
    }
    
    /**
     * Inner KeyListener class.
     *
     */
    private class MyKeyListener extends KeyAdapter {
        /**
         * Handles a press event.
         * 
         * @param theEvent The event.
         */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                myPause = !myPause;
                if (myPause) {
                    myTimer.stop();
                } else {
                    myTimer.start();
                }
            }
            newGame(theEvent);
            endGame(theEvent);
            if (!myPause) {
                if (theEvent.getKeyCode() == KeyEvent.VK_A) {
                    myBoard.left();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_D) {
                    myBoard.right();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_Q) {
                    myBoard.rotateCW();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_E) {
                    myBoard.rotateCCW();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_S) {
                    myBoard.down();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    myBoard.drop();
                }
            }
        }
        
        /**
         * Ends the Game.
         * 
         * @param theEvent is a keyboard event.
         */
        private void newGame(final KeyEvent theEvent) {
            if (theEvent.getKeyCode() == KeyEvent.VK_N) {
                myPause = false;
                if (!myPause) {
                    myBoard.newGame();
                    myScorePanel.resetScore();
                    myTimer.start();
                } 
            }
        }
        
        /**
         * Ends the Game.
         * 
         * @param theEvent is a keyboard event.
         */
        private void endGame(final KeyEvent theEvent) {
            if (theEvent.getKeyCode() == KeyEvent.VK_U) {
                myPause = true;
                if (myPause) {
                    myTimer.stop();
                    JOptionPane.showMessageDialog(myFrame,
                                    "End Game");
                } 
            }
        }
    }
}
