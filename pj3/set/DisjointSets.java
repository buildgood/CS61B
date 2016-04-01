/* DisjointSets.java */

package set;

/**
 *  A disjoint sets ADT.  Performs union-by-size and path compression.
 *  Implemented using arrays.  There is no error checking whatsoever.
 *  By adding your own error-checking, you might save yourself a lot of time
 *  finding bugs in your application code for Project 3 and Homework 9.
 *  Without error-checking, expect bad things to happen if you try to unite
 *  two elements that are not roots of their respective sets, or are not
 *  distinct.
 *
 *  Elements are represented by ints, numbered from zero.
 **/

public class DisjointSets {

  private int[] array;

  /**
   *  Construct a disjoint sets object.
   *
   *  @param numElements the initial number of elements--also the initial
   *  number of disjoint sets, since every element is initially in its own set.
   **/
  public DisjointSets(int numElements) {
    array = new int [numElements];
    for (int i = 0; i < array.length; i++) {
      array[i] = -1;
    }
  }

  /**
   *  union() unites two disjoint sets into a single set.  A union-by-size
   *  heuristic is used to choose the new root.  This method will corrupt
   *  the data structure if root1 and root2 are not roots of their respective
   *  sets, or if they're identical.
   *
   *  @param root1 the root of the first set.
   *  @param root2 the root of the other set.
   **/
  public void union(int root1, int root2) {
    if (array[root2] < array[root1]) {                 // root2 has larger tree
      array[root2] += array[root1];        // update # of items in root2's tree
      array[root1] = root2;                              // make root2 new root
    } else {                                  // root1 has equal or larger tree
      array[root1] += array[root2];        // update # of items in root1's tree
      array[root2] = root1;                              // make root1 new root
    }
  }

  /**
   *  find() finds the (int) name of the set containing a given element.
   *  Performs path compression along the way.
   *
   *  @param x the element sought.
   *  @return the set containing x.
   **/
  public int find(int x) {
    if (array[x] < 0) {
      return x;                         // x is the root of the tree; return it
    } else {
      // Find out who the root is; compress path by making the root x's parent.
      array[x] = find(array[x]);
      return array[x];                                       // Return the root
    }
  }

  /**
   *  main() is test code.  All the find()s on the same output line should be
   *  identical.
   **/
  public static void main(String[] args) {
    int NumElements = 128;
    int NumInSameSet = 16;

    DisjointSets s = new DisjointSets(NumElements);
    int set1, set2;

    for (int k = 1; k < NumInSameSet; k *= 2) {
      for (int j = 0; j + k < NumElements; j += 2 * k) {
        set1 = s.find(j);
        set2 = s.find(j + k);
        s.union(set1, set2);
      }
    }

    for (int i = 0; i < NumElements; i++) {
      System.out.print(s.find(i) + "*");
      if (i % NumInSameSet == NumInSameSet - 1) {
        System.out.println();
      }
    }
    System.out.println();
  }
}
