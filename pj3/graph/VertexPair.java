/* VertexPair.java */

package graph;

/**
 * The VertexPair represents a pair of objects that act as vertices in a
 * WUGraph (weighted, undirected graph).  The purpose of a VertexPair is to
 * act as a key for Java's hashCode() and equals() functions.  It is designed
 * so that the order of the two objects is immaterial; (u, v) is the same as
 * (v, u).
 */

class VertexPair {
  protected Object object1;
  protected Object object2;

  protected VertexPair(Object o1, Object o2) {
    object1 = o1;
    object2 = o2;
  }

  /**
   * hashCode() returns a hashCode equal to the sum of the hashCodes of each
   * of the two objects of the pair, so that the order of the objects will
   * not affect the hashCode.  Self-edges are treated differently:  we don't
   * add an object's hashCode to itself, since the result would always be even.
   * We add one to the hashCode so that a self-edge will not collide with the
   * object itself if vertices and edges are stored in the same hash table.
   */
  public int hashCode() {
    if (object1.equals(object2)) {
      return object1.hashCode() + 1;
    } else {
      return object1.hashCode() + object2.hashCode();
    }
  }

  /**
   * equals() returns true if this VertexPair represents the same unordered
   * pair of objects as the parameter "o".  The order of the pair does not
   * affect the equality test, so (u, v) is found to be equal to (v, u).
   */
  public boolean equals(Object o) {
    if (o instanceof VertexPair) {
      return ((object1.equals(((VertexPair) o).object1)) &&
              (object2.equals(((VertexPair) o).object2))) ||
             ((object1.equals(((VertexPair) o).object2)) &&
              (object2.equals(((VertexPair) o).object1)));
    } else {
      return false;
    }
  }
}
