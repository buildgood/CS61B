/* KruskalTest.java */

/**
 * The KruskalTest class tests the Kruskal class.
 */

import graph.*;
import graphalg.*;
import java.util.*;

public class KruskalTest {

  private static final int VERTICES = 10;
  private static final int MAXINT = 100;

  private static boolean tree = true;
  private static boolean minTree = true;

  public static void addRandomEdges(WUGraph g, Object[] vertArray) {
    int i, j;

    System.out.println("Adding random edges to graph.");

    Random random = new Random(3);      // Create a "Random" object with seed 0

    for (i = 0; i < vertArray.length; i++) {
      for (j = i; j < vertArray.length; j++) {
        int r = random.nextInt() % MAXINT;                // Between -99 and 99
        if (r >= 0) {
          g.addEdge(vertArray[i], vertArray[j], r);
        }
      }
    }
  }

  public static void DFS(WUGraph t, DFSVertex current, DFSVertex prev,
                         int[] maxOnPath, int maxEdge) {
    Neighbors neigh;
    int i;

    current.visited = true;
    maxOnPath[current.number] = maxEdge;
    neigh = t.getNeighbors(current);
    if (neigh != null) {
      for (i = 0; i < neigh.neighborList.length; i++) {
        DFSVertex next = (DFSVertex) neigh.neighborList[i];
        if (next.visited) {
          if ((next != current) && (next != prev)) {
            tree = false;
            return;
          }
        } else if (neigh.weightList[i] > maxEdge) {
          DFS(t, next, current, maxOnPath, neigh.weightList[i]);
        } else {
          DFS(t, next, current, maxOnPath, maxEdge);
        }
        if (!tree) {
          return;
        }
      }
    }
  }

  public static void DFSTest(WUGraph g, WUGraph t, DFSVertex[] vertArray) {
    int[][] maxOnPath;
    Neighbors neigh;
    int i, j;

    System.out.println("Testing the tree.");

    maxOnPath = new int[VERTICES][VERTICES];
    for (i = 0; i < VERTICES; i++) {
      for (j = 0; j < VERTICES; j++) {
        vertArray[j].visited = false;
      }
      DFS(t, vertArray[i], null, maxOnPath[i], -MAXINT);
      for (j = 0; j < VERTICES; j++) {
        if (!vertArray[j].visited) {
          tree = false;
        }
      }
      if (!tree) {
        return;
      }
    }

//  for (i = 0; i < vertArray.length; i++) {
//    for (j = 0; j < vertArray.length; j++) {
//      System.out.print(" " + maxOnPath[i][j]);
//    }
//    System.out.println();
//  }

    for (i = 0; i < VERTICES; i++) {
      neigh = g.getNeighbors(vertArray[i]);
      if (neigh != null) {
        for (j = 0; j < neigh.neighborList.length; j++) {
          int v = ((DFSVertex) neigh.neighborList[j]).number;
          if (neigh.weightList[j] < maxOnPath[i][v]) {
            minTree = false;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int i, j;
    int score;
    WUGraph g, t;
    DFSVertex[] vertArray;

    System.out.println("Running minimum spanning tree test.");
    System.out.println("Creating empty graph.");
    g = new WUGraph();

    System.out.println("Adding " + VERTICES + " vertices.");
    vertArray = new DFSVertex[VERTICES];
    for (i = 0; i < VERTICES; i++) {
      vertArray[i] = new DFSVertex();
      vertArray[i].number = i;
      g.addVertex(vertArray[i]);
    }

    addRandomEdges(g, vertArray);

//  for (i = 0; i < vertArray.length; i++) {
//    for (j = 0; j < vertArray.length; j++) {
//      if (g.isEdge(vertArray[i], vertArray[j])) {
//        System.out.print(" " + g.weight(vertArray[i], vertArray[j]));
//      } else {
//        System.out.print(" *");
//      }
//    }
//    System.out.println();
//  }

    System.out.println("Finding the minimum spanning tree.");
    t = Kruskal.minSpanTree(g);

//  for (i = 0; i < vertArray.length; i++) {
//    for (j = 0; j < vertArray.length; j++) {
//      if (t.isEdge(vertArray[i], vertArray[j])) {
//        System.out.print(" " + t.weight(vertArray[i], vertArray[j]));
//      } else {
//        System.out.print(" *");
//      }
//    }
//    System.out.println();
//  }

    DFSTest(g, t, vertArray);

    if (tree) {
      System.out.println("One point for creating a tree.");
      if (minTree) {
        System.out.println("Two points for creating a minimum spanning tree.");
        score = 3;
      } else {
        System.out.println("Not a minimum spanning tree.");
        score = 1;
      }
    } else {
      System.out.println("Not a tree.");
      score = 0;
    }

    System.out.println("Your Kruskal test score is " + score + " out of 3.");
    System.out.println("  (Be sure also to run WUGTest.java.)");
  }
}

class DFSVertex {
  boolean visited;
  int number;
}
