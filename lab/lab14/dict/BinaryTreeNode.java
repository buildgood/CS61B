/* BinaryTreeNode.java */

package dict;

/**
 *  BinaryTreeNode represents a node in a binary tree.
 *
 *  DO NOT CHANGE THIS FILE.
 **/
class BinaryTreeNode {

  /**
   *  entry is a (key, value) pair stored in this node.
   *  parent is the parent of this node.
   *  leftChild and rightChild are the children of this node.
   **/
  Entry entry;
  BinaryTreeNode parent;
  BinaryTreeNode leftChild, rightChild;

  /**
   *  Construct a BinaryTreeNode with a specified entry; parent and children
   *  are null.
   **/
  BinaryTreeNode(Entry entry) {
    this(entry, null, null, null);
  }

  /**
   *  Construct a BinaryTreeNode with a specified entry and parent; children
   *  are null.
   **/
  BinaryTreeNode(Entry entry, BinaryTreeNode parent) {
    this(entry, parent, null, null);
  }

  /**
   *  Construct a BinaryTreeNode, specifying all four fields.
   **/
  BinaryTreeNode(Entry entry, BinaryTreeNode parent,
                 BinaryTreeNode left, BinaryTreeNode right) {
    this.entry = entry;
    this.parent = parent;
    leftChild = left;
    rightChild = right;
  }

  /**
   *  Express a BinaryTreeNode as a String.
   *
   *  @return a String representing the BinaryTreeNode.
   **/
  public String toString() {
    String s = "";

    if (leftChild != null) {
      s = "(" + leftChild.toString() + ")";
    }
    s = s + entry.key().toString() + entry.value();
    if (rightChild != null) {
      s = s + "(" + rightChild.toString() + ")";
    }
    return s;
  }
}
