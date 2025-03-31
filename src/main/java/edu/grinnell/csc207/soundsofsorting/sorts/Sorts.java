package edu.grinnell.csc207.soundsofsorting.sorts;

import java.util.ArrayList;
import java.util.List;
import edu.grinnell.csc207.soundsofsorting.sortevents.*;
import edu.grinnell.csc207.soundsofsorting.sortevents.SortEvent;

/**
 * This is a collection of sorting algorithm implementations that produce a list of events
 * for visualization and sound. Each sorting method returns a list of SortEvent<T>
 * that can be applied to the original array to reproduce the sort.
 * Includes an eventSort method to apply a sequence of events to an array.
 */
public class Sorts {

    /** 
     * Apply a list of sorting events to the given array.
     * This will mutate the array according to the events sequence.
     * 
     * @param arr the array to sort through event applications
     * @param events the list of events to apply
     * @param <T> the element type
     */
    public static <T> void eventSort(T[] arr, List<SortEvent<T>> events) {
        for (SortEvent<T> event : events) {
            event.apply(arr);
        }
    }

    /**
     * Selection sort algorithm 
     * Repeatedly selects the minimum element and swaps it into place.
     * Generates CompareEvents for each comparison and SwapEvents for each swap.
     * 
     * @param arr the array to sort (will not be modified by this method)
     * @param <T> element type (must be Comparable)
     * @return the list of SortEvents recording the sort operations
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> selectionSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        int n = a.length;
        for (int i = 0; i < n; ++i) {
            int minIndex = i;
            for (int j = i + 1; j < n; ++j) {
                // Compare a[j] with a[minIndex]
                events.add(new CompareEvent<T>(j, minIndex));
                if (a[j].compareTo(a[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                // Swap a[i] and a[minIndex]
                events.add(new SwapEvent<T>(i, minIndex));
                T temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp;
            }
        }
        return events;
    }

    /**
     * Insertion sort algorithm (generic).
     * Iteratively inserts each element into the sorted portion at left.
     * Generates CompareEvents for comparisons and CopyEvents for element shifts and placements.
     * 
     * @param arr the array to sort (will not be modified by this method)
     * @param <T> element type (must be Comparable)
     * @return list of SortEvents recording the operations of insertion sort
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> insertionSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        int n = a.length;
        for (int i = 1; i < n; ++i) {
            T toInsert = a[i];
            int j = i;
            // Compare and shift elements to the right to create position for toInsert
            while (j > 0) {
                // Always compare a[j-1] with the value to insert
                events.add(new CompareEvent<T>(j-1, i));
                if (a[j-1].compareTo(toInsert) <= 0) {
                    break;
                }
                // Shift a[j-1] to a[j]
                events.add(new CopyEvent<T>(j, a[j-1]));
                a[j] = a[j-1];
                j--;
            }
            // Place the toInsert value at position j
            events.add(new CopyEvent<T>(j, toInsert));
            a[j] = toInsert;
        }
        return events;
    }

    /**
     * Bubble sort algorithm (generic).
     * Repeatedly sweeps through the array, swapping adjacent out-of-order elements.
     * Generates CompareEvents for comparisons and SwapEvents for swaps.
     * 
     * @param arr the array to sort (will not be modified)
     * @param <T> element type (must be Comparable)
     * @return list of SortEvents recording bubble sort operations
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> bubbleSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        int n = a.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; ++i) {
                events.add(new CompareEvent<T>(i-1, i));
                if (a[i-1].compareTo(a[i]) > 0) {
                    // swap adjacent elements
                    events.add(new SwapEvent<T>(i-1, i));
                    T temp = a[i-1];
                    a[i-1] = a[i];
                    a[i] = temp;
                    swapped = true;
                }
            }
            n--; 
        } while (swapped);
        return events;
    }

    /**
     * Merge sort algorithm (generic, recursive).
     * Divides the array and merge-sorts each part, then merges them.
     * Generates CompareEvents for comparisons and CopyEvents for each write to the array.
     * 
     * @param arr the array to sort (will not be modified by this method)
     * @param <T> element type (must be Comparable)
     * @return list of SortEvents recording the merge sort operations
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> mergeSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        mergeSortHelper(a, 0, a.length - 1, events);
        return events;
    }

    // Helper for mergeSort (recursive)
    private static <T extends Comparable<T>> void mergeSortHelper(T[] a, int lo, int hi, List<SortEvent<T>> events) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        mergeSortHelper(a, lo, mid, events);
        mergeSortHelper(a, mid+1, hi, events);
        // Merge sorted subarrays [lo..mid] and [mid+1..hi]
        T[] aux = a.clone(); // copy entire array (could optimize to copy only needed segment)
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            // Compare the next elements of the two halves
            events.add(new CompareEvent<T>(i, j));
            if (aux[i].compareTo(aux[j]) <= 0) {
                // Take element from left half
                events.add(new CopyEvent<T>(k, aux[i]));
                a[k++] = aux[i++];
            } else {
                // Take element from right half
                events.add(new CopyEvent<T>(k, aux[j]));
                a[k++] = aux[j++];
            }
        }
        // Copy any remaining elements from left half
        while (i <= mid) {
            events.add(new CopyEvent<T>(k, aux[i]));
            a[k++] = aux[i++];
        }
        // Copy any remaining elements from right half
        while (j <= hi) {
            events.add(new CopyEvent<T>(k, aux[j]));
            a[k++] = aux[j++];
        }
    }

    /**
     * Quick sort algorithm (generic, recursive).
     * Picks a pivot and partitions the array around the pivot, then sorts partitions.
     * Generates CompareEvents for comparisons and SwapEvents for swaps.
     * 
     * @param arr the array to sort (will not be modified)
     * @param <T> element type (must be Comparable)
     * @return list of SortEvents recording quicksort operations
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> quickSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        quickSortHelper(a, 0, a.length - 1, events);
        return events;
    }

    // Helper for quickSort
    private static <T extends Comparable<T>> void quickSortHelper(T[] a, int lo, int hi, List<SortEvent<T>> events) {
        if (lo >= hi) return;
        // simple pivot selection: last element
        T pivot = a[hi];
        int i = lo;
        // partition: elements <= pivot to left of i
        for (int j = lo; j < hi; ++j) {
            events.add(new CompareEvent<T>(j, hi));
            if (a[j].compareTo(pivot) <= 0) {
                events.add(new SwapEvent<T>(i, j));
                // swap a[i] and a[j]
                T temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
            }
        }
        // place pivot at index i
        events.add(new SwapEvent<T>(i, hi));
        T temp = a[i];
        a[i] = a[hi];
        a[hi] = temp;
        // recursively sort left and right partitions
        quickSortHelper(a, lo, i-1, events);
        quickSortHelper(a, i+1, hi, events);
    }

    /**
     * Shell sort algorithm (generic).
     * Uses diminishing gap insertion sort (Shell's method).
     * Generates CompareEvents and CopyEvents for shifts (or SwapEvents for swaps).
     * 
     * @param arr the array to sort (will not be modified)
     * @param <T> element type (must be Comparable)
     * @return list of SortEvents recording shell sort operations
     */
    public static <T extends Comparable<T>> List<SortEvent<T>> shellSort(T[] arr) {
        T[] a = arr.clone();
        List<SortEvent<T>> events = new ArrayList<>();
        int n = a.length;
        // Start with gap = n/2 and reduce by half
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // gapped insertion sort for this gap
            for (int i = gap; i < n; ++i) {
                T temp = a[i];
                int j = i;
                // Shift elements of earlier subarray until correct position for temp is found
                while (j >= gap) {
                    events.add(new CompareEvent<T>(j - gap, i));
                    if (a[j - gap].compareTo(temp) <= 0) {
                        break;
                    }
                    // shift a[j-gap] to a[j]
                    events.add(new CopyEvent<T>(j, a[j-gap]));
                    a[j] = a[j-gap];
                    j -= gap;
                }
                // place temp at its correct position
                events.add(new CopyEvent<T>(j, temp));
                a[j] = temp;
            }
        }
        return events;
    }
}