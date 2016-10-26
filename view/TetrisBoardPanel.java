/*
 * TCSS 305
 * Assignment 6 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Block;

/**
 * The TetrisBoardPanel displays the board.
 * 
 * @author Arrunn Chhouy
 * @version 1.0
 */
public class TetrisBoardPanel extends JPanel implements Observer {
    
    /**
     * A serial version ID.
     */
    private static final long serialVersionUID = -8047953575537861425L;
    
    /**
     * The background color of the panel.
     */
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    /** 
     * The default pane width. 
     */
    private static final int WIDTH = 200;
    
    /** 
     * The default pane height. 
     */
    private static final int HEIGHT = 400;
    
    /**
     * The size of the blocks.
     */
    private static final int SIZE = 20;
    
    /**
     * The number to subtract from the list of blocks.
     */
    private static final int NUM_ONE = 5;
    
    /**
     * The number to subtract from the list of blocks.
     */
    private static final int NUM_TWO = 4;
    
    /**
     * A list of block. 
     */
    private List<Block[]> myBlock;
    
    /**
     * Boolean to set grid on.
     */
    private boolean myGrid;
    
    /**
     * Constructs a TetrisBoardPanel object and set fields.
     * 
     */
    public TetrisBoardPanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
    }
    
    /**
     * Sets the grid of the board on/off.
     * 
     * @param theGrid is a boolean
     */
    public void setGrid(final boolean theGrid) {
        myGrid = theGrid;
    }
    
    /**
     * Paints the tetris piece that is in played and frozen pieces.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draws the border
        g2d.drawRect(0, 0, myBlock.get(0).length * SIZE, myBlock.size() * SIZE);
        
        g2d.drawRect(0, 0, myBlock.get(0).length * SIZE, (myBlock.size() - NUM_TWO) * SIZE);
        
        for (int i = myBlock.size() - 1; i >= 0; i--) {
            final Block[] row = myBlock.get(i);
            if (myGrid) {
                for (int k = 0; k < myBlock.get(i).length; k++) {
                    g2d.drawLine(k * SIZE, 0 , k * SIZE , HEIGHT);
                    g2d.drawLine(0, i * SIZE , WIDTH , i * SIZE);
                }
            }
            for (int j = 0; j < row.length; j++) {
                if (row[j] != null) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect(j * SIZE, (SIZE * (myBlock.size() - NUM_ONE)) - (i * SIZE),
                                 SIZE, SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(j * SIZE, (SIZE * (myBlock.size() - NUM_ONE)) - (i * SIZE),
                                 SIZE, SIZE);
            
                }  
            }
        }
        
    }

    /**
     * Updates the Board Panel.
     * 
     * @param theObservable is the observable object
     * @param theObject is the object
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof List<?>) {
            myBlock = (List<Block[]>) theObject;
        }
        repaint();   
    }
}

