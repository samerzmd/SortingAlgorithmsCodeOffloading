/**
 * Provides an implementation of the quick sort algorithm.
 *
 * @author  Middleware Services
 */

package apps.buildable.sortingalgorithms.sort.algorithm;

import java.util.Arrays;
import java.util.List;

public class QuickSort extends SortingAlgorithm {

    public QuickSort() {

    }

    public void sorting(String array[], int start, int end) {
        int i = start;
        int k = end;
        if (end - start >= 1) {
            String pivot = array[start];
            while (k > i) {
                while (array[i].compareTo(pivot) <= 0 && i <= end && k > i)
                    i++;
                while (array[k].compareTo(pivot) > 0 && k >= start && k >= i)
                    k--;
                if (k > i)
                    swap(array, i, k);
            }
            swap(array, start, k);
            sorting(array, start, k - 1);
            sorting(array, k + 1, end);
        } else { return; }
    }
    private void swap(String array[], int index1, int index2) {
        String temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    @Override
    public List<String> sort(String[] toSort) {

        sorting(toSort,0,toSort.length-1);
        return  Arrays.asList(toSort);
    }
}