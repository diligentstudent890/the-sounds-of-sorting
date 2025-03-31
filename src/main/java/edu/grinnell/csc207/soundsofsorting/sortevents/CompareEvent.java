package edu.grinnell.csc207.soundsofsorting.sortevents;

import java.util.Arrays;
import java.util.List;

/**
 * An event representing a comparison between two elements in the array.
 * This event does not modify the array when applied.
 * 
 * @param <T> the array element type
 */
public class CompareEvent<T> implements SortEvent<T> {
    private final int index1;
    private final int index2;

    public CompareEvent(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public void apply(T[] arr) {
        // Compare events do not change array elements
        return;
    }

    @Override
    public List<Integer> getAffectedIndices() {
        // The two indices involved in the comparison
        return Arrays.asList(index1, index2);
    }

    @Override
    public boolean isEmphasized() {
        // Comparisons are not emphasized
        return false;
    }
}