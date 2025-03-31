package edu.grinnell.csc207.soundsofsorting.sortevents;
import java.util.List;

/**
 * SortEvents are events generated during sorting that allow for playback
 * of the logged sorting algorithm.
 */
public interface SortEvent<T> {
    /**
     * Applies this event to the array.
     * @param arr the array to modify
     */
    public void apply(T[] arr);

    /**
     * @return a list of the indices affected by this event
     */
    public List<Integer> getAffectedIndices();

    /**
     * @return true if this event is emphasized
     */
    public boolean isEmphasized();
}
