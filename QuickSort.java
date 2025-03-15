import java.util.*;
import java.lang.*;

/**
 * Class for QuickSort Investigation, exploring how changing
 * the switch to insertion sort affects runtime. Note that
 * there are many private methods here for how quicksort works.
 * (You won't need to consider all of them, especially fillAndShuffle().)
 * You'll modify main, and you're welcome to add any additional
 * methods you'd like. You will likely need to modify the way in which
 * the insertionSort threshold (currently set with MIN_SIZE) is
 * specified.
 *
 * @author Anna Rafferty
 * @author Layla Oesper
 * @author Eric Alexander
 * @author Titus Klinge
 * @author Sneha Narayan
 * @author [YOUR NAMES HERE]
 */
public class QuickSort {
    private static final int MIN_SIZE = 10;

    /**
     * Sorts an array in increasing order using quicksort.
     *
     * @param arr the array to be sorted
     */
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper method to quicksort.
     *
     * @param arr the array to be sorted
     * @param firstI the first index to be considered
     * @param lastI the last index to be considered
     */
    private static void quickSort(int[] arr, int firstI, int lastI) {
        if (lastI - firstI <= MIN_SIZE) {
            insertionSort(arr, firstI, lastI);
        } else {
            int pivotI = partition(arr, firstI, lastI);
            quickSort(arr, firstI, pivotI - 1);
            quickSort(arr, pivotI + 1, lastI);
        }
    }

    /**
     * Partitions slice of given array between two given indices around a pivot.
     *
     * @param arr the array to be partitioned
     * @param firstI the first index to be considered in partitioning
     * @param lastI the last index to be considered in partitioning
     */
    private static int partition(int[] arr, int firstI, int lastI) {
        // Select middle item as pivot
        int pivotI = (lastI + firstI) / 2;
        int pivotV = arr[pivotI];
        swap(arr, firstI, pivotI);

        // March up and down closer and closer until they cross,
        // swapping values that are out of place
        int up = firstI + 1;
        int down = lastI;
        boolean done = false;
        while (!done) {
            // March up to find value greater than pivotV
            while (up < lastI && arr[up] < pivotV) {
                up++;
            }
            // March down to find value less than pivotV
            while (down > firstI && arr[down] > pivotV) {
                down--;
            }

            // If up and down passed, we're done.
            // Else, swap their values and keep going.
            if (up < down) {
                swap(arr, up, down);
                up++;
                down--;
            } else {
                done = true;
                swap(arr, firstI, down);
            }
        }

        // down will be pointing to the index of the pivot
        return down;
    }
    // Investigation 1: create a new method with a version of the algorithm that implements the median-of-three strategy of choosing the pivot, run the sort again on arrays of different sizes, and compare what you see in both cases. 
    public static void quickSortMedian(int[] arr) {
        quickSortMedian(arr, 0, arr.length - 1);
    }
    
    private static void quickSortMedian(int[] arr, int firstI, int lastI) {
        if (lastI - firstI <= MIN_SIZE) {
            insertionSort(arr, firstI, lastI);
        } else {
            int pivotI = partitionMedian(arr, firstI, lastI);
            quickSortMedian(arr, firstI, pivotI - 1);
            quickSortMedian(arr, pivotI + 1, lastI);
        }
    }   
    /**
 * Partitions the array using the median-of-three pivot strategy.
 */
private static int partitionMedian(int[] arr, int firstI, int lastI) {
    int pivotI = (firstI + lastI) / 2;
    // Find the median of the three elements by comparing and swapping first, last and middle elements
    if (arr[firstI] > arr[pivotI])
        swap(arr, firstI, pivotI);
    if (arr[firstI] > arr[lastI])
        swap(arr, firstI, lastI);
    if (arr[pivotI] > arr[lastI])
        swap(arr, pivotI, lastI);

    // arr[pivotI] now holds the median value.
    swap(arr, firstI, pivotI);
    int pivotV = arr[firstI];

    int up = firstI + 1;
    int down = lastI;
    boolean done = false;
    while (!done) {
        while (up < lastI && arr[up] < pivotV) {
            up++;
        }
        while (down > firstI && arr[down] > pivotV) {
            down--;
        }
        if (up < down) {
            swap(arr, up, down);
            up++;
            down--;
        } else {
            done = true;
            swap(arr, firstI, down);
        }
    }
    return down;
}

/**
 * Sorts the array using quicksort with a given insertion sort cutoff threshold.
 * @param arr the array to be sorted
 * @param threshold the cutoff value at which to switch to insertion sort
 */
    public static void quickSortThreshold(int[] arr, int threshold) {
        quickSortThreshold(arr, 0, arr.length - 1, threshold);
    }

    private static void quickSortThreshold(int[] arr, int firstI, int lastI, int threshold) {
        if (lastI - firstI <= threshold) {
            insertionSort(arr, firstI, lastI);
        } else {
            int pivotI = partition(arr, firstI, lastI);
            quickSortThreshold(arr, firstI, pivotI - 1, threshold);
            quickSortThreshold(arr, pivotI + 1, lastI, threshold);
        }
    }   

    /**
     * Sorts the specified array between given indices.
     *
     * @param arr the array to be sorted
     * @param firstI the first index to be considered
     * @param lastI the last index to be considered
     */
    private static void insertionSort(int[] arr, int firstI, int lastI) {
        for (int i = firstI + 1; i <= lastI; i++) {
            int numToInsert = arr[i];
            int j = i;
            while (j > firstI && arr[j-1] > numToInsert) {
                arr[j] = arr[j-1];
                j--;
            }
            arr[j] = numToInsert;
        }
    }

    /**
     * Helper method that swaps two values in a given array.
     *
     * @param arr the array containing the values
     * @param i the index of the first value
     * @param j the index of the second value
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Helper function you may decide to use to print out a given array to the console.
     *
     * @param arr the array to print
     */
    private static void printArr(int[] arr) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < arr.length; i++) {
            sj.add(Integer.toString(arr[i]));
        }
        System.out.println(sj.toString());
    }

    /**
     * Generates a pseudo-random permutation of the integers from 0 to a.length - 1.
     * See p. 139 of "Seminumerical Algorithms, 2nd edition," by Donald Knuth.
     */
    public static void fillAndShuffle(int[] a) {
        // Fill the array with the integers from 0 to a.length - 1.
        int k;
        for (k = 0; k < a.length; k++) {
            a[k] = k;
        }

        // Shuffle.
        for (k = a.length - 1; k > 0; k--) {
            int swapIndex = (int)Math.floor(Math.random() * (k + 1));
            int temp = a[k];
            a[k] = a[swapIndex];
            a[swapIndex] = temp;
        }
    }

    /**
     * You'll put your experiments for the investigation here.
     * The current contents are just to give you an example.
     */
    public static void main(String[] args) {
        System.err.println("Investigation 1：");

        //This is just an example of how you might do timing - you can erase
        // it and write your own investigation.
        /** 
        int[] quicksortArray = new int[1000000];
        fillAndShuffle(quicksortArray);
        long startTime = System.currentTimeMillis();
        quickSort(quicksortArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Array length: " + quicksortArray.length + "; time to sort (ms): " + (endTime-startTime));
        */
        int[] sizes = {100000, 200000, 400000, 800000, 1600000, 3200000, 6400000 ,12800000, 25600000};

        for (int size : sizes) {
            int[] arrMiddle = new int[size];
            int[] arrMedian = new int[size];
            fillAndShuffle(arrMiddle);
            System.arraycopy(arrMiddle, 0, arrMedian, 0, size);
    
            // Time the original quickSort (middle element pivot)
            long startTime = System.currentTimeMillis();
            quickSort(arrMiddle);
            long middleTime = System.currentTimeMillis() - startTime;
    
            // Time the quickSortMedian (median-of-three pivot)
            startTime = System.currentTimeMillis();
            quickSortMedian(arrMedian);
            long medianTime = System.currentTimeMillis() - startTime;
    
           
            System.out.println(size + ", " + middleTime + ", " + medianTime);
        }
    }
        /*
        System.err.println("Investigation 2：");
        int arraySizeBroad = 2000000; // example array size (adjust as needed)
        int[] broadThresholds = {10, 30, 50, 70, 90};
    
        for (int threshold : broadThresholds) {
            int[] arr = new int[arraySizeBroad];
            fillAndShuffle(arr);
            long startTime = System.currentTimeMillis();
            quickSortThreshold(arr, threshold);
            long endTime = System.currentTimeMillis();
            System.out.println("Array Size: " + arraySizeBroad + ", Threshold: " + threshold + ", Time: " + (endTime - startTime) + " ms");
        }
    
            // Fine-grained experiment: using the same (or different) array size and a narrower range of threshold values.
            System.out.println("Fine-Grained");
            int arraySizeFine = 2000000; // example array size (adjust as needed)
            // Fine-grained sweep from threshold 10 to 20 (you can adjust the range and increments)
            for (int threshold = 50 ;threshold <= 100; threshold++) {
            int[] arr = new int[arraySizeFine];
            fillAndShuffle(arr);
            long startTime = System.currentTimeMillis();
            quickSortThreshold(arr, threshold);
            long endTime = System.currentTimeMillis();
            System.out.println("Array Size: " + arraySizeFine + ", Threshold: " + threshold + ", Time: " + (endTime - startTime) + " ms");
                }
        }*/
    }
