
package edu.grinnell.csc207.soundsofsorting.sortevents;

import java.util.Arrays;
import java.util.List;
/**
 * A swapevent logs a swap between two indices of the array.
 */
public class SwapEvent<T> implements SortEvent<T> {
    private final int index1;
    private final int index2;

    public SwapEvent(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public void apply(T[] arr) {
        // Swap the elements at index1 and index2
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    @Override
    public List<Integer> getAffectedIndices() {
        // The two indices that were swapped
        return Arrays.asList(index1, index2);
    }

    @Override
    public boolean isEmphasized() {
        // Swaps should be emphasized (they are actual data movements)
        return true;
    }
}