package edu.grinnell.csc207.soundsofsorting;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import edu.grinnell.csc207.soundsofsorting.sortevents.SortEvent;
import edu.grinnell.csc207.soundsofsorting.sorts.Sorts;

/**
 * Control panel for the sorting visualizer.
 */
public class ControlPanel extends JPanel {
    private static final int FPS = 20;
    public static final int[] bMinorPentatonicValues =
        {46, 49, 51, 53, 56, 58, 61, 63, 65, 68, 70, 73, 75, 78, 82, 85, 87};
    public static final int[] chromaticValues =
        {40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
         60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79};

    private final NoteIndices notes;
    private final ArrayPanel panel;
    private Scale scale;
    private boolean isSorting;

    /**
     * @param notes
     * @param panel
     */
    public ControlPanel(NoteIndices notes, ArrayPanel panel) {
        this.notes = notes;
        this.panel = panel;
        this.scale = new Scale(bMinorPentatonicValues);
        notes.initializeAndShuffle(scale.size());
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JComboBox<String> sortBox = new JComboBox<>(
            new String[]{"Selection", "Insertion", "Bubble", "Merge", "Quick"});
        JComboBox<String> scaleBox = new JComboBox<>(
            new String[]{"Pentatonic", "Chromatic"});
        JButton makeScale = new JButton("Make Scale");
        JButton play = new JButton("Play");

        // Change scale and shuffle
        makeScale.addActionListener(e -> {
            if (!isSorting) {
                scale = generateScale((String) scaleBox.getSelectedItem());
                notes.initializeAndShuffle(scale.size());
                panel.repaint();
            }
        });

        // Play sorting animation
        play.addActionListener(e -> {
            if (isSorting) {
                return;
            }
            isSorting = true;

            Integer[] copy = notes.getNotes().clone();
            List<SortEvent<Integer>> events = generateEvents(
                (String) sortBox.getSelectedItem(), copy);
            notes.clearAllHighlighted();

            // Use Swing Timer (javax.swing.Timer) for animation
            final Timer timer = new Timer(1000 / FPS, null);
            timer.addActionListener(new ActionListener() {
                int idx = 0;
                @Override
                public void actionPerformed(ActionEvent ev) {
                    notes.clearAllHighlighted();
                    if (idx < events.size()) {
                        SortEvent<Integer> evt = events.get(idx++);
                        for (int i : evt.getAffectedIndices()) {
                            notes.highlightNote(i);
                            scale.playNote(notes.getNotes()[i], evt.isEmphasized());
                        }
                        evt.apply(notes.getNotes());
                        panel.repaint();
                    } else {
                        timer.stop();
                        isSorting = false;
                        panel.repaint();
                    }
                }
            });
            timer.start();
        });

        add(sortBox);
        add(scaleBox);
        add(makeScale);
        add(play);
    }

    private static List<SortEvent<Integer>> generateEvents(
            String sort, Integer[] arr) {
        switch (sort) {
            case "Selection": return Sorts.selectionSort(arr);
            case "Insertion": return Sorts.insertionSort(arr);
            case "Bubble": return Sorts.bubbleSort(arr);
            case "Merge": return Sorts.mergeSort(arr);
            case "Quick": return Sorts.quickSort(arr);
            default: throw new IllegalArgumentException();
        }
    }

    private static Scale generateScale(String name) {
        switch (name) {
            case "Pentatonic":
                return new Scale(bMinorPentatonicValues);
            case "Chromatic":
                return new Scale(chromaticValues);
            default:
                throw new IllegalArgumentException();
        }
    }
}