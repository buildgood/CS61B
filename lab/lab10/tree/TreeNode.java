/* TreeNode.java */

package tree;

/**
 *  A TreeNode object is a mutable node in a tree.  No implementation is
 *  provided.
 *
 *  DO NOT CHANGE THIS FILE.
 *  @author Jonathan Shewchuk
 */

public abstract class TreeNode {

  /**
   *  item references the item stored in this node.
   *  valid is true if and only if this is a valid node in some Tree.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected Object item;
  protected boolean valid;

  /**
   *  isValidNode returns true if this node is valid; false otherwise.
   *
   *  @return true if this node is valid; false otherwise.
   *
   *  Performance:  runs in O(1) time.
   */
  public boolean isValidNode() {
    return valid;
  }

  /**
   *  item() returns this node's item.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the item stored in this node.
   *
   *  Performance:  runs in O(1) time.
   */
  public Object item() throws InvalidNodeException {
    if (isValidNode()) {
      return item;
    } else {
      throw new InvalidNodeException();
    }
  }

  /**
   *  setItem() sets this node's item to "item".  If this node is invalid,
   *  throws an exception.
   *
   *  Performance:  runs in O(1) time.
   */
  public void setItem(Object item) throws InvalidNodeException {
    if (isValidNode()) {
      this.item = item;
    } else {
      throw new InvalidNodeException();
    }
  }

  /**
   *  children() returns the number of children of the node at this position.
   */
  public abstract int children();

  /**
   *  parent() returns the parent TreeNode of this TreeNode.  Throws an
   *  exception if `this' is not a valid node.  Returns an invalid TreeNode if
   *  this node is the root.
   */
  public abstract TreeNode parent() throws InvalidNodeException;

  /**
   *  child() returns the cth child of this TreeNode.  Throws an exception if
   *  `this' is not a valid node.  Returns an invalid TreeNode if there is no
   *  cth child.
   */
  public abstract TreeNode child(int c) throws InvalidNodeException;

  /**
   *  nextSibling() returns the next sibling TreeNode to the right from this
   *  TreeNode.  Throws an exception if `this' is not a valid node.  Returns
   *  an invalid TreeNode if there is no sibling to the right of this node.
   */
  public abstract TreeNode nextSibling() throws InvalidNodeException;

  /**
   *  insertChild() inserts an item as the cth child of this node.  Existing
   *  children numbered c or higher are shifted one place to the right
   *  to accommodate.  If the current node has fewer than c children,
   *  the new item is inserted as the last child.  If c < 1, act as if c is 1.
   */
  public abstract void insertChild(Object item, int c) throws
                                                       InvalidNodeException;

  /**
   *  removeLeaf() removes the node at the current position from the tree if
   *  it is a leaf.  Does nothing if `this' has one or more children.  Throws
   *  an exception if `this' is not a valid node.  If 'this' has siblings to
   *  its right, those siblings are all shifted left by one.
   */
  public abstract void removeLeaf() throws InvalidNodeException;

}
