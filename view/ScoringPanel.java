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
import javax.swing.Timer;

import model.TetrisPiece;

/**
 * The ScoringPanel keeps track of the score. 
 * 
 * @author Arrunn Chhouy
 * @version 1.0
 */
public class ScoringPanel extends JPanel implements Observer {

    /**
     * The generated serial ID.
     */
    private static final long serialVersionUID = -425679011718241492L;

    /**
     * The numerator for delay calculations.
     */
    private static final int MY_DELAY_NUMERATOR = 1000;
    
    /**
     * The thickness of the border.
     */
    private static final int THICKNESS = 1;

    /** 
     * The default pane width. 
     */
    private static final int WIDTH = 150;
    
    /** 
     * The default pane height. 
     */
    private static final int HEIGHT = 200;
    
    /** 
     * The x-coordinate spacing. 
     */
    private static final int X_SPACE = 35;
    
    /** 
     * The y-coordinate spacing. 
     */
    private static final int Y_SPACE1 = 60;
    
    /** 
     * The y-coordinate spacing. 
     */
    private static final int Y_SPACE2 = 80;
    
    /** 
     * The y-coordinate spacing. 
     */
    private static final int Y_SPACE3 = 100;
    
    /** 
     * The clearing four line score. 
     */
    private static final int FOUR_LINE = 1200;
    
    /** 
     * The clearing three line score. 
     */
    private static final int THREE_LINE = 300;
    
    /** 
     * The clearing two line score. 
     */
    private static final int TWO_LINE = 100;
    
    /** 
     * The clearing one line score. 
     */
    private static final int ONE_LINE = 40;
    
    /** 
     * The default score.
     */
    private static final int DEFAULT_SCORE = 4;
    
    /** 
     * The level 3 multiplier score.
     */
    private static final int NUM_THREE = 3;
    
    /**
     * A timer used to update the state of the simulation.
     */
    private final Timer myTimer;
    
    /**
     * The total score for tetris.
     */
    private int myScore;
    
    /**
     * The level of the tetris.
     */
    private int myLevel;
    
    /**
     * A counter for the lines completed.
     */
    private int myLevelLine;
    
    /**
     * A counter.
     */
    private int myCounter;
    
    /**
     * Start the score.
     */
    private boolean myStartScore;
    
    /**
     * The complete rows block.
     */
    private Integer[] myCompleteRow;
    
    /**
     * Constructs a ScoringPanel object and set fields.
     * 
     * @param theTimer is a timer.
     */
    public ScoringPanel(final Timer theTimer) {
        super();
        myTimer = theTimer;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, THICKNESS));
        myScore = 0;
        myLevel = 1;
        myCompleteRow = new Integer[0];
        myCounter = 0;
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
        
        g2d.drawString("SCORE" , X_SPACE, Y_SPACE1);
        calculateScore();
        g2d.drawString("Points: " + myScore, X_SPACE, Y_SPACE2);
        g2d.drawString("Level: " + myLevel, X_SPACE, Y_SPACE3);
    }
    
    /**
     * Calculates the total Score for Tetris.
     */
    private void calculateScore() {
        myLevelLine += myCompleteRow.length;
        if (myLevelLine > DEFAULT_SCORE) {
            myLevel++;
            myLevelLine = 0;
        }
        myTimer.setDelay(MY_DELAY_NUMERATOR / myLevel);
       
        if (myStartScore) {
            myScore += DEFAULT_SCORE;
        }
        
        if (myCompleteRow.length == DEFAULT_SCORE) {
            myScore += FOUR_LINE * myLevel;
        } else if (myCompleteRow.length == NUM_THREE) {
            myScore += THREE_LINE * myLevel;
        } else if (myCompleteRow.length == 2) {
            myScore += TWO_LINE * myLevel;
        } else if (myCompleteRow.length == 1) {
            myScore += ONE_LINE * myLevel;
        }
        myCompleteRow = new Integer[0];
    } 
    
    /**
     * Updates the score.
     * 
     * @param theObservable is the observable
     * @param theObject is the object
     */
//    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        
        if (theObject instanceof Integer[]) {
            myCompleteRow = (Integer[]) theObject;
            repaint();
        }
        
        if (theObject instanceof TetrisPiece) {   
            myCounter++;
            if (myCounter >= 2) {
                myStartScore = true;
            }
            repaint();
        }
    }

    /**
     * Resets the score to 0.
     */
    public void resetScore() {
        myScore = 0;
        myLevel = 1;
        myCounter = 0;
        myCompleteRow = new Integer[0];
        myStartScore = false;
        
    }

}
