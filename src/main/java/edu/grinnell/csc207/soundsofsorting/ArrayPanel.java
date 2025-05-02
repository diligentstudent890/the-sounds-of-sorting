package edu.grinnell.csc207.soundsofsorting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * A drawing panel for visualizing the contents of a NoteIndices object.
 */
public class ArrayPanel extends JPanel {
    private final NoteIndices notes;

    /**
     * @param notes
     * @param width
     * @param height
     */
    public ArrayPanel(NoteIndices notes, int width, int height) {
        this.notes = notes;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer[] arr = notes.getNotes();
        int n = arr.length;
        if (n == 0) {
            return;
        }
        int w = getWidth();
        int h = getHeight();
        int barW = w / n;
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            int barH = (int) (((double) (val + 1) / n) * h);
            int x = i * barW;
            int y = h - barH;
            if (notes.isHighlighted(i)) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.GREEN.darker());
            }
            g.fillRect(x, y, barW, barH);
        }
    }
}