package apps.buildable.sortingalgorithms.sort.algorithm;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SamerGigaByte on 5/19/2017.
 */

public class MergeSort extends SortingAlgorithm {
    @Override
    public List<String> sort(String[] toSort) {
        mergeSort((Comparable[]) toSort);
        return Arrays.asList(toSort);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid;
        for (int k = lo; k < hi; k++) {
            if      (i == mid)                 aux[k] = a[j++];
            else if (j == hi)                  aux[k] = a[i++];
            else if (a[j].compareTo(a[i]) < 0) aux[k] = a[j++];
            else                               aux[k] = a[i++];
        }

        // copy back
        for (int k = lo; k < hi; k++)
            a[k] = aux[k];
    }


    /***************************************************************************
     *  Mergesort the subarray a[lo] .. a[hi-1], using the
     *  auxilliary array aux[] as scratch space.
     ***************************************************************************/
    public static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        // base case
        if (hi - lo <= 1) return;

        // InsertionSort each half, recursively
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid, hi);

        // merge back together
        merge(a, aux, lo, mid, hi);
    }


    /***************************************************************************
     *  Sort the array a using mergesort.
     ***************************************************************************/
    public static void mergeSort(Comparable[] a) {
        final String method=new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(method, " Start "+ String.valueOf(System.nanoTime()));
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        mergeSort(a, aux, 0, n);
        Log.i(method, " End "+ String.valueOf(System.nanoTime()));
    }

    /***************************************************************************
     *  Sort the subarray a[lo..hi] using mergesort.
     ***************************************************************************/
    public static void mergeSort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        Comparable[] aux = new Comparable[n];
        mergeSort(a, aux, lo, hi);
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i].compareTo(a[i-1]) < 0) return false;
        return true;
    }



}
