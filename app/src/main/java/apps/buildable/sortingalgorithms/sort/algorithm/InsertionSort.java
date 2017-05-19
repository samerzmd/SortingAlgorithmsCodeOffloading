package apps.buildable.sortingalgorithms.sort.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SamerGigaByte on 5/19/2017.
 */

public class InsertionSort extends SortingAlgorithm {
    @Override
    public List<String> sort(String[] toSort) {
        sort(toSort);
        return Arrays.asList(toSort);
    }
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j-1].compareTo(a[j]) > 0) {
                    exch(a, j-1, j);
                }
                else break;
            }
        }
    }

    // exchange a[i] and a[j]
    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
