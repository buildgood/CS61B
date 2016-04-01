/* SibTree.java */

package tree;

/**
 *  The SibTree class implements general trees using linked nodes.  Each node
 *  has pointers to its parent, its first (leftmost) child, and its sibling to
 *  the immediate right.
 *  @author Jonathan Shewchuk
 */

public class SibTree extends Tree {

  /**
   *  (inherited)  size is the number of items in the tree.
   *  root is the tree's root node.
   **/

  SibTreeNode root;

  /**
   *  ADT implementation invariants:
   *
   *    Definition:  SibTreeNode y is a "sibling of" SibTreeNode x if
   *      1) x.nextSibling == y, or 
   *      2) y is a sibling of x.nextSibling
   *    Definition:  SibTreeNode y is a "descendant of" SibTreeNode x if
   *      1) x == y, or 
   *      2) y is a descendant of x.firstChild, or 
   *      3) y is a descendant of some SibTreeNode z and 
   *             z is a sibling of x.firstChild
   *    Definition:  a SibTreeNode x is said to be "in" SibTree "this" if
   *             x is a descendant of this.root
   *
   *  1) if root != null, then root.valid == true, and root.parent == null.
   *  2) for any SibTreeNode x in SibTree "this", x.valid == true.
   *  3) for any SibTreeNodes x and y in SibTree "this", if
   *             x.firstChild == y or y is a sibling of x.firstChild, then 
   *             y.parent == x.
   *  4) for any SibTreeNodes x and y in SibTree "this", if x.parent == y, then
   *             y.firstChild == x or x is a sibling of y.firstChild.
   *  5) for any SibTreeNode x in SibTree "this", if x.parent == null, then
   *             x == root.
   *  6) "size" is the number of nodes in SibTree "this".
   *  7) for any SibTreeNode x in SibTree "this", x satisfies all the
   *             invariants of SibTreeNode (listed in SibTreeNode.java).
   */

  /**
   * Construct an empty SibTree.
   */
  public SibTree() {
    root = null;
    size = 0;
  }

  /**
   * Construct a one-node SibTree.
   */
  public SibTree(Object item) {
    root = new SibTreeNode(this, item);
    size = 1;
  }

  /**
   *  root() returns the root node, if one exists.  Returns an invalid node if
   *  the tree is empty.
   */
  public TreeNode root() {
    if (root == null) {
      return new SibTreeNode();
    } else {
      return root;
    }
  }

  /**
   *  insertRoot() inserts a node containing "item" at the root of the tree.
   *  If a root already exists, it becomes a child of the new node.
   */
  public void insertRoot(Object item) {
    SibTreeNode newRoot = new SibTreeNode(this, item);
    newRoot.firstChild = root;
    if (root != null) {
      root.parent = newRoot;
    }
    root = newRoot;
    size++;
  }

  /**
   * toString() returns a string representation of the SibTree.
   */
  public String toString() {
    return preorderString(root, 0);
  }

  private String preorderString(SibTreeNode currentNode, int depth) {
    if (currentNode == null) {
      return "";
    }
    String s = "";
    for (int i = 0; i < depth; i++) {
      s = s + "  ";
    }
    return s + currentNode.item + "\n" +
           preorderString(currentNode.firstChild, depth + 1) +
           preorderString(currentNode.nextSibling, depth);
  }

  /**
   * main() contains mounds and mounds of icky test code.
   */
  public static void main(String[] args) {
    TreeNode r, r1, r2, r3, r31, r32;

    // Create two-node tree.
    Tree t = new SibTree(new Integer(11));
    t.insertRoot(new Integer(1));
    System.out.println("Creating 2-node tree.");

    // Reference the nodes.
    r = t.root();
    r1 = null;
    try {
      r1 = r.child(1);
    } catch (InvalidNodeException e) {
    }

    // Does parent() work?
    System.out.println("Testing parent().");
    try {
      if (r != r1.parent()) {
        System.out.println("  ERROR:  parent of root node's child is" +
                           " not the root, but should be.");
      }
      if (r.parent() == null) {
        System.out.println("  ERROR:  parent() returned null.");
      } else if (r.parent().isValidNode()) {
        System.out.println("  ERROR:  parent() of root is valid," +
                           " but shouldn't be.");
      }
    } catch (Exception e) {
      System.out.println("  ERROR:  parent() threw interrupt on valid node.");
    }
    try {
      TreeNode stp = r.child(2).parent();
      System.out.println("  ERROR:  parent() failed to throw exception on" +
                         " invalid node.");
    } catch (InvalidNodeException e) {
    }

    // Add more nodes to tree.
    System.out.println("\nTesting insertChild()." +
                       "  Adding two more nodes to the 2-node tree.");
    r2 = null;
    r3 = null;
    r31 = null;
    r32 = null;
    try {
      r.insertChild(new Integer(13), 1000);
      r.insertChild(new Integer(12), 2);
      r2 = r.child(2);
      r3 = r.child(3);
      if (((Integer) r2.item()).intValue() != 12) {
        System.out.println("  ERROR:  Second child of root does not contain" +
                           " the correct key, 12.");
      }
      if (((Integer) r3.item()).intValue() != 13) {
        System.out.println("  ERROR:  Third child of root does not contain" +
                           " the correct key, 13.");
      }
      if (r2.parent() != r) {
        System.out.println("  ERROR:  Second child of root does not think" +
                           " the root is its parent.");
      }
      if (r3.parent() != r) {
        System.out.println("  ERROR:  Third child of root does not think" +
                           " the root is its parent.");
      }
      if (r2.nextSibling() != r3) {
        System.out.println("  ERROR:  Second child of root does not think" +
                           " the root's third child is its next sibling.");
      }
      if (r3.nextSibling().isValidNode()) {
        System.out.println("  ERROR:  Third child of root thinks it has" +
                           " a next sibling.");
      }

      System.out.println("Adding two more nodes to the 4-node tree.");
      r3.insertChild(new Integer(132), 1);
      r3.insertChild(new Integer(131), 1);
      r31 = r3.child(1);
      r32 = r3.child(2);
      if (((Integer) r31.item()).intValue() != 131) {
        System.out.println("  ERROR:  Node r31 does not contain" +
                           " the correct key, 131.");
      }
      if (((Integer) r32.item()).intValue() != 132) {
        System.out.println("  ERROR:  Node r32 does not contain" +
                           " the correct key, 132.");
      }
      if (r31.parent() != r3) {
        System.out.println("  ERROR:  Node r31 does not think" +
                           " Node r3 is its parent.");
      }
      if (r32.parent() != r3) {
        System.out.println("  ERROR:  Node r32 does not think" +
                           " Node r3 is its parent.");
      }
      if (r31.nextSibling() != r32) {
        System.out.println("  ERROR:  Node r31 does not think" +
                           " Node r32 is its next sibling.");
      }
      if (r32.nextSibling().isValidNode()) {
        System.out.println("  ERROR:  Node r32 thinks it has a next sibling.");
      }
    } catch (Exception e) {
      System.out.println("  ERROR:  unexpected exception while adding and" +
                         " testing nodes.");
      //      System.exit(1);
    }
    try {
      if (r.parent() == null) {
        System.out.println("  ERROR:  parent() returned null.");
      } else {
        r.parent().insertChild(new Integer(0), 1);
        System.out.println("  ERROR:  insertChild() failed to throw" +
                           " exception on invalid node.");
      }
    } catch (InvalidNodeException e) {
    }
    if (t.size() != 6) {
      System.out.println("  ERROR:  tree size is " + t.size() +
                         " but should be 6.");
    }


    System.out.println("The tree looks like this:");
    System.out.print(t.toString());
    System.out.println("[The above sequence should be" +
                       " 1, 11, 12, 13, 131, 132.]");


    // Remove nodes from tree.
    System.out.println("\nTesting removeLeaf()." +
                       "  Removing one node from 6-node tree.");
    try {
      r1.removeLeaf();
    } catch (Exception e) {
      System.out.println("  ERROR:  unexpected exception while removing.");
    }
    if (r1.isValidNode()) {
      System.out.println("  ERROR:  the removed node should be invalid.");
    }
    if (t.size() != 5) {
      System.out.println("  ERROR:  tree size is " + t.size() +
                         " but should be 5.");
    }
    try {
      r1 = r.child(1);
    } catch (Exception e) {
    }
    if (r1 != r2) {
      System.out.println("  ERROR:  after deleting Node r1, Node r2 has" +
                         " not become the first child of the root.");
    }


    System.out.println("Removing another node from 5-node tree.");
    try {
      r32.removeLeaf();
    } catch (Exception e) {
      System.out.println("  ERROR:  unexpected exception while removing.");
    }
    if (t.size() != 4) {
      System.out.println("  ERROR:  tree size is " + t.size() +
                         " but should be 4.");
    }
    try {
      r32 = r3.child(2);
    } catch (Exception e) {
    }
    if (r32.isValidNode()) {
      System.out.println("  ERROR:  Node r3 still has a second child.");
    }
    try {
      r32 = r31.nextSibling();
    } catch (Exception e) {
    }
    if (r32.isValidNode()) {
      System.out.println("  ERROR:  Node r31 still has a next sibling.");
    }


    System.out.println("Attempting to remove non-leaf node from 4-node tree.");
    System.out.println("  Operation should have no effect.");
    try {
      r3.removeLeaf();
      if (!r3.isValidNode()) {
        System.out.println("  ERROR:  removed non-leaf is invalid.");
      }
      if (r.child(2) != r3) {
        System.out.println("  ERROR:  removed non-leaf is no longer" +
                           " the root's second child.");
      }
    } catch (Exception e) {
      System.out.println("  ERROR:  removeLeaf() threw exception.");
    }

    System.out.println("Attempting to remove invalid node from 4-node tree.");
    try {
      r32.removeLeaf();
      System.out.println("  ERROR:  removeLeaf() failed to throw exception.");
    } catch (InvalidNodeException e) {
    }


    System.out.println("The tree looks like this:");
    System.out.print(t.toString());
    System.out.println("[The above sequence should be" +
                       " 1, 12, 13, 131.]");


    System.out.println("Removing remaining nodes from 4-node tree.");
    try {
      r31.removeLeaf();
      r2.removeLeaf();
      r3.removeLeaf();
      r.removeLeaf();
    } catch (Exception e) {
      System.out.println("  ERROR:  unexpected exception while removing.");
    }
    if (t.size() != 0) {
      System.out.println("  ERROR:  tree size is " + t.size() +
                         " but should be zero.");
    }
    r = t.root();
    if (r.isValidNode()) {
      System.out.println("  ERROR:  the root should be invalid.");
    }
  }

}
