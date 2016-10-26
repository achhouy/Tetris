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
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Point;
import model.TetrisPiece;

/**
 * The TetrisPiecePanel displays the next piece.
 * 
 * @author Arrunn Chhouy
 * @version 1.0
 */
public class TetrisPiecePanel extends JPanel implements Observer {

    /**
     * The generated serial ID.
     */
    private static final long serialVersionUID = -824665334194793723L;
    
    /**
     * The size of the blocks.
     */
    private static final int SIZE = 20;
    
    /**
     * The thickness of the border.
     */
    private static final int THICKNESS = 1;
    
    /**
     * The spacing x-coordinate for the preview block in the panel.
     */
    private static final int SPACING_X = 40;
    
    /**
     * The spacing y-coordinate for the preview block in the panel.
     */
    private static final int SPACING_Y = 25;
    
    /**
     * The int used to flip the pieces.
     */
    private static final int FLIP = 5;
    
    /** 
     * The default pane width. 
     */
    private static final int WIDTH = 150;
    
    /** 
     * The default pane height. 
     */
    private static final int HEIGHT = 200;
    
    /**
     * The next tetris piece.
     */
    private TetrisPiece myPiece;
    
    /**
     * Constructs a TetrisBoardPanel object and set fields.
     */
    public TetrisPiecePanel() {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, THICKNESS));
    }
    
    /**
     * Paints the upcoming tetris piece.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        final Point[] points = myPiece.getPoints();
        for (int i = 0; i < points.length; i++) {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(points[i].x() * SIZE + SPACING_X, 
                         (SIZE * FLIP) - (points[i].y() * SIZE) + SPACING_Y, SIZE, SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(points[i].x() * SIZE + SPACING_X, 
                         (SIZE * FLIP) - (points[i].y() * SIZE) + SPACING_Y, SIZE, SIZE);
        }
    }

    /**
     * Updates the upcoming tetris piece.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        // Print out the next piece of tetris.
        if (theObject instanceof TetrisPiece) {
            myPiece = (TetrisPiece) theObject;
        }
        repaint();
    }

}
