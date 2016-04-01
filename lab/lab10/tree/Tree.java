/* Tree.java */

package tree;

/**
 *  A Tree is a mutable ADT for general trees (wherein a node can have any
 *  number of children).
 *  @author Jonathan Shewchuk
 */

public abstract class Tree {

  /**
   *  size is the number of items in the tree.
   **/

  protected int size;

  /**
   *  isEmpty() returns true if this Tree is empty, false otherwise.
   *
   *  @return true if this Tree is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  size() returns the size of this Tree.
   *
   *  @return the size of this Tree.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int size() {
    return size;
  }

  /**
   *  root() returns the root node, if one exists.  Returns an invalid node if
   *  the tree is empty.
   */
  public abstract TreeNode root();

  /**
   *  insertRoot() inserts a node containing "item" at the root of the tree.
   *  If a root already exists, it becomes a child of the new node.
   */
  public abstract void insertRoot(Object item);

}
