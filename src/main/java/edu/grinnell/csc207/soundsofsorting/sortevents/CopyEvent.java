package edu.grinnell.csc207.soundsofsorting.sortevents;

import java.util.Arrays;
import java.util.List;

/**
 * An event representing copying a value into a specific array index.
 * Applying this event sets the target index in the array to the stored value.
 * 
 * @param <T> the array element type
 */
public class CopyEvent<T> implements SortEvent<T> {
    private final int index;
    private final T value;

    public CopyEvent(int index, T value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void apply(T[] arr) {
        // Copy the recorded value into the array at the target index
        arr[index] = value;
    }

    @Override
    public List<Integer> getAffectedIndices() {
        // Only the target index is affected by this copy
        return Arrays.asList(index);
    }

    @Override
    public boolean isEmphasized() {
        // Copying a value (writing to array) is emphasized
        return true;
    }
}