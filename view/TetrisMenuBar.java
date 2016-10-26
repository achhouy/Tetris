/*
 * TCSS 305
 * Assignment 6 - Tetris
 */

package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Board;

/**
 * TetrisMenuBar is the menu bar that the TetrisGUI uses.
 * 
 * @author Arrunn Chhouy
 * @version 1.0
 */
public class TetrisMenuBar extends JMenuBar {

    /**
     * A generated serial ID.
     */
    private static final long serialVersionUID = -8436264661459312329L;
    
    /**
     * The board.
     */
    private final Board myBoard;
    
    /**
     * A frame.
     */
    private final JFrame myFrame;
    
    /**
     * A File Menu.
     */
    private JMenu myFileMenu;
    
    /**
     * A Option Menu.
     */
    private JMenu myOptionMenu;
    
    /**
     * A scoring panel.
     */
    private final ScoringPanel myScoringPanel;
    
    /**
     * 
     */
    private final TetrisBoardPanel myBoardPanel;
    
    /**
     * A timer.
     */
    private final Timer myTimer;
    
    /**
     * Creates a Tetris Menu Bar object.
     * 
     * @param theBoard is a board.
     * @param theFrame is the main frame
     * @param theTimer is a timer.
     * @param thePanel is the scoring panel.
     * @param theTetrisBoard is the game board panel.
     */
    public TetrisMenuBar(final Board theBoard, final JFrame theFrame, final Timer theTimer, 
                         final ScoringPanel thePanel, final TetrisBoardPanel theTetrisBoard) {
        super();
        myBoard = theBoard;
        myFrame = theFrame;
        myTimer = theTimer;
        myScoringPanel = thePanel;
        myBoardPanel = theTetrisBoard;
        createMenuButton();
    }

    /**
     * Creates all the button for the Menu Bar.
     */
    private void createMenuButton() {
        // Creates the file menu item and adds it to the file menu.
        myFileMenu = new JMenu("File"); 
        fileMenuItem();
        
        myOptionMenu = new JMenu("Option");
        optionMenuItem();
        
        // Creates the help menu item and adds it to the help menu.
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.add(helpMenuItem());
        
        add(myFileMenu);
        add(myOptionMenu);
        add(helpMenu);
    }
    
    /**
     * Creates file menu Items along with actionListener.
     * 
     */
    private void fileMenuItem() {
        final JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new ActionListener() {           
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.newGame();
                myTimer.start();
                myScoringPanel.resetScore();
            }
        });

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {           
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        myFileMenu.add(newGameItem);
        myFileMenu.addSeparator();
        myFileMenu.add(exitItem);
    }
    
    /**
     * Creates file menu Items along with actionListener.
     * 
     */
    private void optionMenuItem() {
        final JCheckBoxMenuItem gridItem = new JCheckBoxMenuItem("Grid");
        gridItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent theEvent) {
                myBoardPanel.setGrid(gridItem.isSelected());
                myBoardPanel.repaint();
            }
        });
        myOptionMenu.add(gridItem);

    }
    
    /**
     * Creates an help menu Item along with actionListener.
     * 
     * @return a JMenuItem
     */
    private JMenuItem helpMenuItem() {
        final JMenuItem helpItem = new JMenuItem("Instructions to Play");
        
        helpItem.addActionListener(new ActionListener() {           
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null,
                                              "Instructions to play: \n" 
                                                              + "KEYS : FUNCTION \n"
                                                              + "A : MOVE LEFT \n" 
                                                              + "D : MOVE RIGHT \n"
                                                              + "S : MOVE DOWN \n" 
                                                              + "Q : ROTATE CLOCKWISE \n" 
                                                              + "E : ROTATE COUNTER \n"
                                                              + "SPACE_BAR : DROP  \n"
                                                              + "P : PAUSE GAME  \n"
                                                              + "U : END GAME  \n"
                                                              + "N : NEW GAME  \n"
                                                              , "Instructions",
                                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return helpItem;
    }
    
}
