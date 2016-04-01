/* WUGTest.java */

/**
 * The WUGTest class tests the WUGraph class.
 */

import graph.*;

public class WUGTest {

  private static int vertexTest(Object[] vertArray) {
    int result;
    int countDeduction = 0;
    int getDeduction = 0;
    int isVDeduction = 0;
    WUGraph g;
    Object[] wuVertArray;

    System.out.println("Running vertex test.");
    System.out.println("Creating empty graph.");
    g = new WUGraph();
    result = g.vertexCount();
    if (result != 0) {
      System.out.println("vertexCount() on empty graph returns " + result +
                         " but should return 0.");
      countDeduction = 1;
    }
    result = g.edgeCount();
    if (result != 0) {
      System.out.println("edgeCount() on empty graph returns " + result +
                         " but should return 0.");
      countDeduction = 1;
    }
    wuVertArray = g.getVertices();
    if (wuVertArray == null) {
      System.out.println("getVertices() returns null but shouldn't.");
      getDeduction = 1;
    } else if (wuVertArray.length != 0) {
      System.out.println("getVertices() returns array of length " +
                         wuVertArray.length + "; should have length 0.");
      getDeduction = 1;
    }
    if (g.isVertex(vertArray[0])) {
      System.out.println("isVertex() on vertex 0 should return false" +
                         " but returns true.");
      isVDeduction = 1;
    }

    System.out.println("Adding vertex 0.");
    g.addVertex(vertArray[0]);
    result = g.vertexCount();
    if (result != 1) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 1.");
      countDeduction = 1;
    }
    if (!g.isVertex(vertArray[0])) {
      System.out.println("isVertex() on vertex 0 should return true" +
                         " but returns false.");
      isVDeduction = 1;
    }
    if (g.isVertex(vertArray[1])) {
      System.out.println("isVertex() on vertex 1 should return false" +
                         " but returns true.");
      isVDeduction = 1;
    }
    wuVertArray = g.getVertices();
    if (wuVertArray == null) {
      System.out.println("getVertices() returns null but shouldn't.");
      getDeduction = 1;
    } else if (wuVertArray.length != 1) {
      System.out.println("getVertices() returns array of length " +
                         wuVertArray.length + "; should have length 1.");
      getDeduction = 1;
    } else if (wuVertArray[0] != vertArray[0]) {
      System.out.println("getVertices() returns array containing" +
                         " the wrong object.");
      getDeduction = 1;
    }

    System.out.println("Adding vertex 1.");
    g.addVertex(vertArray[1]);
    result = g.vertexCount();
    if (result != 2) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 2.");
      countDeduction = 1;
    }
    if (!g.isVertex(vertArray[1])) {
      System.out.println("isVertex() on vertex 1 should return true" +
                         " but returns false.");
      isVDeduction = 1;
    }

    System.out.println("Adding vertex 0.");
    g.addVertex(vertArray[0]);
    result = g.vertexCount();
    if (result != 2) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 2.");
      countDeduction = 1;
    }
    if (!g.isVertex(vertArray[0])) {
      System.out.println("isVertex() on vertex 0 should return true " +
                         " but returns false.");
      isVDeduction = 1;
    }
    wuVertArray = g.getVertices();
    if (wuVertArray == null) {
      System.out.println("getVertices() returns null but shouldn't.");
      getDeduction = 1;
    } else if (wuVertArray.length != 2) {
      System.out.println("getVertices() returns array of length " +
                         wuVertArray.length + "; should have length 2.");
      getDeduction = 1;
    } else if (((wuVertArray[0] != vertArray[0]) ||
                (wuVertArray[1] != vertArray[1])) &&
               ((wuVertArray[0] != vertArray[1]) ||
                (wuVertArray[1] != vertArray[0]))) {
      System.out.println("getVertices() returns array containing" +
                         " wrong objects.");
      getDeduction = 1;
    }

    System.out.println("Adding vertex 2.");
    g.addVertex(vertArray[2]);
    System.out.println("Adding vertex 3.");
    g.addVertex(vertArray[3]);
    System.out.println("Adding vertex 4.");
    g.addVertex(vertArray[4]);
    result = g.vertexCount();
    if (result != 5) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 5.");
      countDeduction = 1;
    }
    wuVertArray = g.getVertices();
    if (wuVertArray == null) {
      System.out.println("getVertices() returns null but shouldn't.");
      getDeduction = 1;
    } else if (wuVertArray.length != 5) {
      System.out.println("getVertices() returns array of length " +
                         wuVertArray.length + "; should have length 5.");
      getDeduction = 1;
    }

    System.out.println("Removing vertex 0.");
    g.removeVertex(vertArray[0]);
    System.out.println("Removing vertex 3.");
    g.removeVertex(vertArray[3]);
    result = g.vertexCount();
    if (result != 3) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 3.");
      countDeduction = 1;
    }
    System.out.println("Removing vertex 3.");
    g.removeVertex(vertArray[3]);
    result = g.vertexCount();
    if (result != 3) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 3.");
      countDeduction = 1;
    }
    result = g.edgeCount();
    if (result != 0) {
      System.out.println("edgeCount() on empty graph returns " + result +
                         " but should return 0.");
      countDeduction = 1;
    }
    if (g.isVertex(vertArray[0])) {
      System.out.println("isVertex() on vertex 0 should return false" +
                         " but returns true.");
      isVDeduction = 1;
    }
    if (g.isVertex(vertArray[3])) {
      System.out.println("isVertex() on vertex 3 should return false" +
                         " but returns true.");
      isVDeduction = 1;
    }
    if (!g.isVertex(vertArray[4])) {
      System.out.println("isVertex() on vertex 4 should return true" +
                         " but returns false.");
      isVDeduction = 1;
    }
    wuVertArray = g.getVertices();
    if (wuVertArray == null) {
      System.out.println("getVertices() returns null but shouldn't.");
      getDeduction = 1;
    } else if (wuVertArray.length != 3) {
      System.out.println("getVertices() returns array of length " +
                         wuVertArray.length + "; should have length 3.");
      getDeduction = 1;
    } else if (((wuVertArray[0] != vertArray[1]) &&
                (wuVertArray[0] != vertArray[2]) &&
                (wuVertArray[0] != vertArray[4])) ||
               ((wuVertArray[1] != vertArray[1]) &&
                (wuVertArray[1] != vertArray[2]) &&
                (wuVertArray[1] != vertArray[4])) ||
               ((wuVertArray[2] != vertArray[1]) &&
                (wuVertArray[2] != vertArray[2]) &&
                (wuVertArray[2] != vertArray[4]))) {
      System.out.println("getVertices() returns array containing " +
                         "wrong objects.");
      getDeduction = 1;
    } else if ((wuVertArray[0] == wuVertArray[1]) ||
               (wuVertArray[1] == wuVertArray[2]) ||
               (wuVertArray[2] == wuVertArray[0])) {
      System.out.println("getVertices() returns array containing " +
                         "duplicate objects.");
      getDeduction = 1;
    }

    System.out.println();
    return countDeduction + 2 * getDeduction + isVDeduction;
  }

  private static int edgeTest(Object[] vertArray) {
    int result;
    int countDeduction = 0;
    int degreeDeduction = 0;
    int getDeduction = 0;
    int isDeduction = 0;
    int weightDeduction = 0;
    int remEDeduction = 0;
    int remVDeduction = 0;
    WUGraph g;
    Neighbors neigh;

    System.out.println("Running edge test.");
    System.out.println("Creating empty graph.");
    g = new WUGraph();
    System.out.println("Adding vertex 0.");
    g.addVertex(vertArray[0]);
    System.out.println("Adding vertex 1.");
    g.addVertex(vertArray[1]);
    System.out.println("Adding vertex 2.");
    g.addVertex(vertArray[2]);
    System.out.println("Adding vertex 3.");
    g.addVertex(vertArray[3]);
    System.out.println("Adding vertex 4.");
    g.addVertex(vertArray[4]);
    System.out.println("Adding vertex 5.");
    g.addVertex(vertArray[5]);
    System.out.println("Adding vertex 6.");
    g.addVertex(vertArray[6]);
    System.out.println("Adding vertex 7.");
    g.addVertex(vertArray[7]);
    System.out.println("Adding vertex 8.");
    g.addVertex(vertArray[8]);
    System.out.println("Adding vertex 9.");
    g.addVertex(vertArray[9]);

    result = g.degree(vertArray[3]);
    if (result != 0) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 0.");
      degreeDeduction = 1;
    }
    if (g.getNeighbors(vertArray[3]) != null) {
      System.out.println("getNeighbors(vertex 3) should return null " +
                         " but doesn't.");
      getDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[7])) {
      System.out.println("isEdge(vertex 3, vertex 7) should return false " +
                         " but returns true.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[3], vertArray[7]);
    if (result != 0) {
      System.out.println("weight(vertex 3, vertex 7) returns " + result +
                         " but should return 0.");
      weightDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[3])) {
      System.out.println("isEdge(vertex 3, vertex 3) should return false " +
                         " but returns true.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[3], vertArray[3]);
    if (result != 0) {
      System.out.println("weight(vertex 3, vertex 3) returns " + result +
                         " but should return 0.");
      weightDeduction = 1;
    }

    System.out.println("Adding edge (3, 7) with weight 4.");
    g.addEdge(vertArray[3], vertArray[7], 4);
    result = g.vertexCount();
    if (result != 10) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 10.");
      countDeduction = 1;
    }
    result = g.edgeCount();
    if (result != 1) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 1.");
      countDeduction = 1;
    }
    if (!g.isEdge(vertArray[3], vertArray[7])) {
      System.out.println("isEdge(vertex 3, vertex 7) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[3], vertArray[7]);
    if (result != 4) {
      System.out.println("weight(vertex 3, vertex 7) returns " + result +
                         " but should return 4.");
      weightDeduction = 1;
    }
    if (!g.isEdge(vertArray[7], vertArray[3])) {
      System.out.println("isEdge(vertex 7, vertex 3) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[7], vertArray[3]);
    if (result != 4) {
      System.out.println("weight(vertex 7, vertex 3) returns " + result +
                         " but should return 4.");
      weightDeduction = 1;
    }
    result = g.degree(vertArray[3]);
    if (result != 1) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 1.");
      degreeDeduction = 1;
    }
    result = g.degree(vertArray[7]);
    if (result != 1) {
      System.out.println("degree(vertex 7) returns " + result +
                         " but should return 1.");
      degreeDeduction = 1;
    }
    result = g.degree(vertArray[0]);
    if (result != 0) {
      System.out.println("degree(vertex 0) returns " + result +
                         " but should return 0.");
      degreeDeduction = 1;
    }

    System.out.println("Adding edge (3, 3) with weight 7.");
    g.addEdge(vertArray[3], vertArray[3], 7);
    result = g.edgeCount();
    if (result != 2) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 2.");
      countDeduction = 1;
    }
    if (!g.isEdge(vertArray[3], vertArray[3])) {
      System.out.println("isEdge(vertex 3, vertex 3) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[3], vertArray[3]);
    if (result != 7) {
      System.out.println("weight(vertex 3, vertex 3) returns " + result +
                         " but should return 7.");
      weightDeduction = 1;
    }
    result = g.degree(vertArray[3]);
    if (result != 2) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 2.");
      degreeDeduction = 1;
    }
    result = g.degree(vertArray[7]);
    if (result != 1) {
      System.out.println("degree(vertex 7) returns " + result +
                         " but should return 1.");
      degreeDeduction = 1;
    }

    System.out.println("Adding edge (7, 3) with weight 9.");
    g.addEdge(vertArray[7], vertArray[3], 9);
    result = g.edgeCount();
    if (result != 2) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 2.");
      countDeduction = 1;
    }
    if (!g.isEdge(vertArray[3], vertArray[7])) {
      System.out.println("isEdge(vertex 3, vertex 7) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[3], vertArray[7]);
    if (result != 9) {
      System.out.println("weight(vertex 3, vertex 7) returns " + result +
                         " but should return 9.");
      weightDeduction = 1;
    }
    if (!g.isEdge(vertArray[7], vertArray[3])) {
      System.out.println("isEdge(vertex 7, vertex 3) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[7], vertArray[3]);
    if (result != 9) {
      System.out.println("weight(vertex 7, vertex 3) returns " + result +
                         " but should return 9.");
      weightDeduction = 1;
    }
    result = g.degree(vertArray[3]);
    if (result != 2) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 2.");
      degreeDeduction = 1;
    }
    result = g.degree(vertArray[7]);
    if (result != 1) {
      System.out.println("degree(vertex 7) returns " + result +
                         " but should return 1.");
      degreeDeduction = 1;
    }
    neigh = g.getNeighbors(vertArray[3]);
    if (neigh == null) {
      System.out.println("getNeighbors(vertex 3) improperly returns null.");
      getDeduction = 1;
    } else if (neigh.neighborList == null) {
      System.out.println("getNeighbors(vertex 3) improperly returns null" +
                         " neighborList.");
      getDeduction = 1;
    } else if (neigh.weightList == null) {
      System.out.println("getNeighbors(vertex 3) improperly returns null" +
                         " weightList.");
      getDeduction = 1;
    } else if (neigh.neighborList.length != 2) {
      System.out.println("getNeighbors(vertex 3) returns neighborList of" +
                         " length " + neigh.neighborList.length +
                         "; should have length 2.");
      getDeduction = 1;
    } else if (neigh.weightList.length != 2) {
      System.out.println("getNeighbors(vertex 3) returns weightList of" +
                         " length " + neigh.weightList.length +
                         "; should have length 2.");
      getDeduction = 1;
    } else if (((neigh.neighborList[0] != vertArray[3]) ||
                (neigh.neighborList[1] != vertArray[7])) &&
               ((neigh.neighborList[0] != vertArray[7]) ||
                (neigh.neighborList[1] != vertArray[3]))) {
      System.out.println("getNeighbors(vertex 3) returns array containing" +
                         " wrong objects.");
      getDeduction = 1;
    } else if (((neigh.weightList[0] != 7) || (neigh.weightList[1] != 9)) &&
               ((neigh.weightList[0] != 9) || (neigh.weightList[1] != 7))) {
      System.out.println("getNeighbors(vertex 3) returns array containing" +
                         " wrong weights.");
      getDeduction = 1;
    }

    System.out.println("Adding edge (9, 0) with weight -2.");
    g.addEdge(vertArray[9], vertArray[0], -2);
    System.out.println("Adding edge (9, 3) with weight 2.");
    g.addEdge(vertArray[9], vertArray[3], 2);
    System.out.println("Adding edge (1, 6) with weight 8.");
    g.addEdge(vertArray[1], vertArray[6], 8);
    System.out.println("Adding edge (9, 7) with weight 5.");
    g.addEdge(vertArray[9], vertArray[7], 5);
    System.out.println("Adding edge (3, 1) with weight 1.");
    g.addEdge(vertArray[3], vertArray[1], 1);
    System.out.println("Adding edge (1, 1) with weight 3.");
    g.addEdge(vertArray[1], vertArray[1], 3);
    result = g.edgeCount();
    if (result != 8) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 8.");
      countDeduction = 1;
    }
    if (!g.isEdge(vertArray[1], vertArray[3])) {
      System.out.println("isEdge(vertex 1, vertex 3) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[1], vertArray[3]);
    if (result != 1) {
      System.out.println("weight(vertex 1, vertex 3) returns " + result +
                         " but should return 1.");
      weightDeduction = 1;
    }
    if (!g.isEdge(vertArray[1], vertArray[1])) {
      System.out.println("isEdge(vertex 1, vertex 1) should return true " +
                         " but returns false.");
      isDeduction = 1;
    }
    result = g.weight(vertArray[1], vertArray[1]);
    if (result != 3) {
      System.out.println("weight(vertex 1, vertex 1) returns " + result +
                         " but should return 3.");
      weightDeduction = 1;
    }
    result = g.degree(vertArray[3]);
    if (result != 4) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 4.");
      degreeDeduction = 1;
    }
    result = g.degree(vertArray[1]);
    if (result != 3) {
      System.out.println("degree(vertex 1) returns " + result +
                         " but should return 3.");
      degreeDeduction = 1;
    }

    System.out.println("Removing edge (1, 6).");
    g.removeEdge(vertArray[1], vertArray[6]);
    result = g.edgeCount();
    if (result != 7) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 7.");
      remEDeduction = 1;
    }
    if (g.isEdge(vertArray[1], vertArray[6])) {
      System.out.println("isEdge(vertex 1, vertex 6) should return false " +
                         " but returns true.");
      remEDeduction = 1;
    }
    if (g.isEdge(vertArray[6], vertArray[1])) {
      System.out.println("isEdge(vertex 6, vertex 1) should return false " +
                         " but returns true.");
      remEDeduction = 1;
    }
    result = g.degree(vertArray[6]);
    if (result != 0) {
      System.out.println("degree(vertex 6) returns " + result +
                         " but should return 0.");
      remEDeduction = 1;
    }
    result = g.degree(vertArray[1]);
    if (result != 2) {
      System.out.println("degree(vertex 1) returns " + result +
                         " but should return 2.");
      remEDeduction = 1;
    }

    System.out.println("Removing vertex 3.");
    g.removeVertex(vertArray[3]);
    result = g.vertexCount();
    if (result != 9) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 9.");
      remVDeduction = 1;
    }
    result = g.edgeCount();
    if (result != 3) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 3.");
      remVDeduction = 1;
    }
    if (g.isVertex(vertArray[3])) {
      System.out.println("isVertex() on vertex 3 should return false" +
                         " but returns true.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[3]);
    if (result != 0) {
      System.out.println("degree(vertex 3) returns " + result +
                         " but should return 0.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[3])) {
      System.out.println("isEdge(vertex 3, vertex 3) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[7])) {
      System.out.println("isEdge(vertex 3, vertex 7) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[7], vertArray[3])) {
      System.out.println("isEdge(vertex 7, vertex 3) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[9])) {
      System.out.println("isEdge(vertex 3, vertex 9) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[9], vertArray[3])) {
      System.out.println("isEdge(vertex 9, vertex 3) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[3], vertArray[1])) {
      System.out.println("isEdge(vertex 3, vertex 1) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[1], vertArray[3])) {
      System.out.println("isEdge(vertex 1, vertex 3) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[1]);
    if (result != 1) {
      System.out.println("degree(vertex 1) returns " + result +
                         " but should return 1.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[7]);
    if (result != 1) {
      System.out.println("degree(vertex 7) returns " + result +
                         " but should return 1.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[9]);
    if (result != 2) {
      System.out.println("degree(vertex 9) returns " + result +
                         " but should return 2.");
      remVDeduction = 1;
    }
    neigh = g.getNeighbors(vertArray[7]);
    if (neigh == null) {
      System.out.println("getNeighbors(vertex 7) improperly returns null.");
      getDeduction = 1;
    } else if (neigh.neighborList == null) {
      System.out.println("getNeighbors(vertex 7) improperly returns null" +
                         " neighborList.");
      getDeduction = 1;
    } else if (neigh.weightList == null) {
      System.out.println("getNeighbors(vertex 7) improperly returns null" +
                         " weightList.");
      getDeduction = 1;
    } else if (neigh.neighborList.length != 1) {
      System.out.println("getNeighbors(vertex 7) returns neighborList of" +
                         " length " + neigh.neighborList.length +
                         "; should have length 1.");
      getDeduction = 1;
    } else if (neigh.weightList.length != 1) {
      System.out.println("getNeighbors(vertex 7) returns weightList of" +
                         " length " + neigh.weightList.length +
                         "; should have length 1.");
      getDeduction = 1;
    } else if (neigh.neighborList[0] != vertArray[9]) {
      System.out.println("getNeighbors(vertex 7) returns array containing" +
                         " wrong object.");
      getDeduction = 1;
    } else if (neigh.weightList[0] != 5) {
      System.out.println("getNeighbors(vertex 7) returns array containing" +
                         " wrong weight.");
      getDeduction = 1;
    }

    System.out.println("Removing edge (1, 1).");
    g.removeEdge(vertArray[1], vertArray[1]);
    result = g.edgeCount();
    if (result != 2) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 2.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[1], vertArray[1])) {
      System.out.println("isEdge(vertex 1, vertex 1) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[1]);
    if (result != 0) {
      System.out.println("degree(vertex 1) returns " + result +
                         " but should return 0.");
      remVDeduction = 1;
    }

    System.out.println("Removing edge (7, 6).");
    g.removeEdge(vertArray[7], vertArray[6]);
    System.out.println("Removing edge (3, 1).");
    g.removeEdge(vertArray[3], vertArray[1]);
    System.out.println("Removing edge (1, 1).");
    g.removeEdge(vertArray[1], vertArray[1]);
    result = g.edgeCount();
    if (result != 2) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 2.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[1], vertArray[1])) {
      System.out.println("isEdge(vertex 1, vertex 1) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }

    System.out.println("Removing edge (9, 7).");
    g.removeEdge(vertArray[9], vertArray[7]);
    result = g.edgeCount();
    if (result != 1) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 1.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[9], vertArray[7])) {
      System.out.println("isEdge(vertex 9, vertex 7) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[7], vertArray[9])) {
      System.out.println("isEdge(vertex 7, vertex 9) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }

    System.out.println("Removing vertex 9.");
    g.removeVertex(vertArray[9]);
    result = g.vertexCount();
    if (result != 8) {
      System.out.println("vertexCount() returns " + result +
                         " but should return 8.");
      remVDeduction = 1;
    }
    result = g.edgeCount();
    if (result != 0) {
      System.out.println("edgeCount() returns " + result +
                         " but should return 0.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[9], vertArray[0])) {
      System.out.println("isEdge(vertex 9, vertex 0) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    if (g.isEdge(vertArray[0], vertArray[9])) {
      System.out.println("isEdge(vertex 0, vertex 9) should return false " +
                         " but returns true.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[9]);
    if (result != 0) {
      System.out.println("degree(vertex 9) returns " + result +
                         " but should return 0.");
      remVDeduction = 1;
    }
    result = g.degree(vertArray[0]);
    if (result != 0) {
      System.out.println("degree(vertex 0) returns " + result +
                         " but should return 0.");
      remVDeduction = 1;
    }

    System.out.println();
    return countDeduction + degreeDeduction + 2 * getDeduction + isDeduction +
           weightDeduction + 2 * remEDeduction + 2 * remVDeduction;

  }

  public static final int VERTICES = 20;

  public static void main(String[] args) {
    int i;

    Object vertArray[] = new Object[VERTICES];
    for (i = 0; i < VERTICES; i++) {
      vertArray[i] = new Nothing();
    }

    int score = 14 - vertexTest(vertArray) - edgeTest(vertArray);
    if (score < 0) {
      score = 0;
    }

    System.out.println("Your WUGraph test score is " + (0.5 * (double) score) +
                       " out of 7.0.");
    System.out.println("  (Be sure also to run KruskalTest.java.)");
  }
}

class Nothing {
}
