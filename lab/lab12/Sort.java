/* Sort.java */

/**
 *  Contains several sorting routines implemented as static methods.
 *  Arrays are rearranged with smallest item first using compares.
 *  @author Kathy Yelick and Mark Allen Weiss
 **/
public final class Sort {

  /**
   *  Simple insertion sort.
   *  @param a an array of int items.
   **/
  public static void insertionSort(int[] a) {
    for (int p = 1; p < a.length; p++) {
      int tmp = a[p];
      int j = p;

      for(; (j > 0) && (tmp < a[j - 1]); j--) {
        a[j] = a[j - 1];
      }
      a[j] = tmp;
    }
  }

  /**
   *  Shellsort, using a sequence suggested by Gonnet.
   *  @param a an array of int items.
   **/
  public static void shellsort(int[] a) {
    for (int gap = a.length / 2; gap > 0;
         gap = (gap == 2) ? 1 : (int) (gap / 2.2)) {
      for (int i = gap; i < a.length; i++) {
        int tmp = a[i];
        int j = i;

        for (; (j >= gap) && (tmp < a[j - gap]); j -= gap) {
          a[j] = a[j - gap];
        }
        a[j] = tmp;
      }
    }
  }

  /**
   *  Standard heapsort.
   *  @param a an array of int items.
   **/
  public static void heapsort(int[] a) {
    for (int i = a.length / 2; i >= 0; i--) {
      percDown(a, i, a.length);
    }
    for (int i = a.length - 1; i > 0; i--) {
      swapReferences(a, 0, i);
      percDown(a, 0, i);
    }
  }

  /**
   *  Internal method for heapsort.
   *  @param i the index of an item in the heap.
   *  @return the index of the left child.
   **/
  private static int leftChild(int i) {
    return 2 * i + 1;
  }

  /**
   *  Internal method for heapsort, used in deleteMax and buildHeap.
   *  @param a an array of int items.
   *  @index i the position from which to percolate down.
   *  @int n the logical size of the binary heap.
   **/
  private static void percDown(int[] a, int i, int n) {
    int child;
    int tmp;

    for (tmp = a[i]; leftChild(i) < n; i = child) {
      child = leftChild(i);
      if ((child != n - 1) && (a[child] < a[child + 1])) {
        child++;
      }
      if (tmp < a[child]) {
        a[i] = a[child];
      } else {
        break;
      }
    }
    a[i] = tmp;
  }

  /**
   *  Mergesort algorithm.
   *  @param a an array of int items.
   **/
  public static void mergeSort(int[] a) {
    int[] tmpArray = new int[a.length];

    mergeSort(a, tmpArray, 0, a.length - 1);
  }

  /**
   *  Internal method that makes recursive calls.
   *  @param a an array of int items.
   *  @param tmpArray an array to place the merged result.
   *  @param left the left-most index of the subarray.
   *  @param right the right-most index of the subarray.
   **/
  private static void mergeSort(int[] a, int[] tmpArray, int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(a, tmpArray, left, center);
      mergeSort(a, tmpArray, center + 1, right);
      merge(a, tmpArray, left, center + 1, right);
    }
  }

  /**
   *  Internal method that merges two sorted halves of a subarray.
   *  @param a an array of int items.
   *  @param tmpArray an array to place the merged result.
   *  @param leftPos the left-most index of the subarray.
   *  @param rightPos the index of the start of the second half.
   *  @param rightEnd the right-most index of the subarray.
   **/
  private static void merge(int[] a, int[] tmpArray, int leftPos, int rightPos,
                            int rightEnd) {
    int leftEnd = rightPos - 1;
    int tmpPos = leftPos;
    int numElements = rightEnd - leftPos + 1;

    // Main loop
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      if (a[leftPos] < a[rightPos]) {
        tmpArray[tmpPos++] = a[leftPos++];
      } else {
        tmpArray[tmpPos++] = a[rightPos++];
      }
    }
    while (leftPos <= leftEnd) {
      tmpArray[tmpPos++] = a[leftPos++];
    }
    while(rightPos <= rightEnd) {
      tmpArray[tmpPos++] = a[rightPos++];
    }
    // Copy TmpArray back
    for (int i = 0; i < numElements; i++, rightEnd--) {
      a[rightEnd] = tmpArray[rightEnd];
    }
  }

  /**
   *  Quicksort algorithm.
   *  @param a an array of int items.
   **/
  public static void quicksort(int[] a) {
    quicksort(a, 0, a.length - 1);
  }

  /**
   *  Method to swap two ints in an array.
   *  @param a an array of ints.
   *  @param index1 the index of the first int to be swapped.
   *  @param index2 the index of the second int to be swapped.
   **/
  public static void swapReferences(int[] a, int index1, int index2) {
    int tmp = a[index1];
    a[index1] = a[index2];
    a[index2] = tmp;
  }

  /**
   *  This is a generic version of C.A.R Hoare's Quick Sort algorithm.  This
   *  will handle arrays that are already sorted, and arrays with duplicate
   *  keys.
   *
   *  If you think of an array as going from the lowest index on the left to
   *  the highest index on the right then the parameters to this function are
   *  lowest index or left and highest index or right.  The first time you call
   *  this function it will be with the parameters 0, a.length - 1.
   *
   *  @param a       an integer array
   *  @param lo0     left boundary of array partition
   *  @param hi0     right boundary of array partition
   **/
   private static void quicksort(int a[], int lo0, int hi0) {
     int lo = lo0;
     int hi = hi0;
     int mid;

     if (hi0 > lo0) {

       // Arbitrarily establishing partition element as the midpoint of
       // the array.
       swapReferences(a, lo0, (lo0 + hi0)/2);
       mid = a[(lo0 + hi0) / 2];

       // loop through the array until indices cross.
       while (lo <= hi) {
         // find the first element that is greater than or equal to 
         // the partition element starting from the left Index.
         while((lo < hi0) && (a[lo] < mid)) {
           lo++;
         }

         // find an element that is smaller than or equal to 
         // the partition element starting from the right Index.
         while((hi > lo0) && (a[hi] > mid)) {
           hi--;
         }
         // if the indices have not crossed, swap them.
         if (lo <= hi) {
           swapReferences(a, lo, hi);
           lo++;
           hi--;
         }
       }

       // If the right index has not reached the left side of array
       // we must now sort the left partition.
       if (lo0 < hi) {
         quicksort(a, lo0, hi);
       }

       // If the left index has not reached the right side of array
       // must now sort the right partition.
       if (lo < hi0) {
         quicksort(a, lo, hi0);
       }
     }
   }

  /**
   *  Internal insertion sort routine.
   *  @param a an array of int items.
   *  @param low the left-most index of the subarray.
   *  @param n the number of items to sort.
   **/
  private static void insertionSort(int[] a, int low, int high) {
    for (int p = low + 1; p <= high; p++) {
      int tmp = a[p];
      int j;

      for (j = p; (j > low) && (tmp < a[j - 1]); j--) {
        a[j] = a[j - 1];
      }
      a[j] = tmp;
    }
  }

  /**
   *  Implementation of the SelectionSort algorithm on integer arrays.
   *  @param a an array of int items.
   **/
  public static void selectionSort(int[] A) {
    int i;
    for (i = A.length-1; i > 0; i--) {
      // outer loop invariant:  A[i..n-1] is sorted.

      // find maximum value in A[0..i]
      int maxIndex = 0;
      int j;
      for (j = 1; j <= i; j++) {
	/* inner loop invariant: for all k < j, A[maxIndex] >= A[k] */

	if (A[maxIndex] < A[j]) {
	  maxIndex = j;
        }
      }

      // swap largest (A[maxIndex]) into A[i] to maintain invariant.
      swapReferences(A, i, maxIndex);
    }
  }

}
