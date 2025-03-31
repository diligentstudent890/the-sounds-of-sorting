package edu.grinnell.csc207.soundsofsorting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * A drawing panel for visualizing the contents of a NoteIndices object.
 */
public class ArrayPanel extends JPanel {
    private NoteIndices notes;
   
    /**
     * Create a new ArrayPanel with the given notes and dimensions.
     * @param notes the note indices 
     * @param width the width of the panel
     * @param height the height of the panel
     */
    public ArrayPanel(NoteIndices notes, int width, int height) {
        this.notes = notes;
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer[] arr = notes.getNotes();
        int n = arr.length;
        if (n == 0) {
            return;
        }
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int barWidth = panelWidth / n;
        for (int i = 0; i < n; i++) {
            int value = arr[i];
            int barHeight = (int) (((double)(value + 1) / n) * panelHeight);
            int x = i * barWidth;
            int y = panelHeight - barHeight;
            // Highlighted bars in red, others in green.
            if (notes.isHighlighted(i)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(x, y, barWidth, barHeight);
        }
    }
}