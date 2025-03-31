package edu.grinnell.csc207.soundsofsorting;

import java.util.Random;
import java.util.Arrays;

/**
 * A collection of indices into a Scale object.
 */
public class NoteIndices {
    private Integer[] indices;
    private boolean[] highlighted;
    
    /**
     * Construct a NoteIndices object of the given size.
     * Initially, the indices are in sorted order.
     * @param n the size 
     */
    public NoteIndices(int n) {
        indices = new Integer[n];
        highlighted = new boolean[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
            highlighted[i] = false;
        }
    }
    
    /**
     * Reinitialize the indices for a scale of size n and shuffle them.
     * @param n the new number of indices
     */
    public void initializeAndShuffle(int n) {
        indices = new Integer[n];
        highlighted = new boolean[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
            highlighted[i] = false;
        }
        shuffle();
    }
    
    /**
     * Return the array of note indices.
     * @return the indices array.
     */
    public Integer[] getNotes() {
        return indices;
    }
    
    /**
     * Highlight a specific note index.
     * @param index the index to highlight.
     */
    public void highlightNote(int index) {
        if (index >= 0 && index < highlighted.length) {
            highlighted[index] = true;
        }
    }
    
    /**
     * Check if the given index is highlighted.
     * @param index the index to check.
     * @return true if highlighted.
     */
    public boolean isHighlighted(int index) {
        return (index >= 0 && index < highlighted.length) ? highlighted[index] : false;
    }
    
    /**
     * Clear all highlighted indices.
     */
    public void clearAllHighlighted() {
        Arrays.fill(highlighted, false);
    }
    
    /**
     * Shuffle the indices array using Fisher-Yates algorithm.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = indices.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Integer temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }
        clearAllHighlighted();
    }
}