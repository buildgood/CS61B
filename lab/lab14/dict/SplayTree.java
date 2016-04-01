/* SplayTree.java */

package dict;

/**
 *  SplayTree implements a Dictionary as a binary tree that is kept roughly
 *  balanced with splay tree operations.  Multiple entries with the same key
 *  are permitted.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 *
 *  @author Deepak Warrier and Jonathan Shewchuk
 **/
public class SplayTree implements Dictionary {

  /** 
   *  size is the number of items stored in the dictionary.
   *  root is the BinaryTreeNode that serves as root of the tree.
   *  If there are no items, size is zero and root is null.
   **/
  protected int size;
  protected BinaryTreeNode root;

  /**
   *  Construct an empty splay tree.
   **/
  public SplayTree() {
    makeEmpty();
  }

  /**
   *  makeEmpty() removes all the entries from the dictionary.
   */
  public void makeEmpty() {
    size = 0;
    root = null;
  }

  /** 
   *  size() returns the number of entries stored in the dictionary.
   *
   *  @return the number of entries stored in the dictionary.
   **/
  public int size() {
    return size;
  }

  /** 
   *  isEmpty() tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  rotateRight() rotates a node up through its parent.  It works only if
   *  "node" is a left child of its parent.
   *
   *  See lecture 36 for a description of tree rotations.
   *
   *  @param node the node to rotate up through its parent.
   **/
  private void rotateRight(BinaryTreeNode node) { 
    if (node == null || node.parent == null || node.parent.leftChild != node) {
      System.out.println("Illegal call to rotateRight().  You have bug #1.");
      return;
    }

    BinaryTreeNode exParent = node.parent;
    BinaryTreeNode subtreeParent = exParent.parent;

    // Move "node"'s right subtree to its former parent.
    exParent.leftChild = node.rightChild; 
    if (node.rightChild != null) {
      node.rightChild.parent = exParent;
    }

    // Make exParent become a child of "node".
    node.rightChild = exParent;
    exParent.parent = node;

    // Make "node" become a child of exParent's former parent.
    node.parent = subtreeParent;
    if (subtreeParent == null) {
      root = node;
    } else if (subtreeParent.rightChild == exParent) {
      subtreeParent.rightChild = node;
    } else {
      subtreeParent.leftChild = node;
    }
  }

  /** 
   *  rotateLeft() rotates a node up through its parent.  It works only if
   *  "node" is a right child of its parent.
   *
   *  See lecture 36 for a description of tree rotations.
   *
   *  @param node the node to rotate up through its parent.
   **/
  private void rotateLeft(BinaryTreeNode node) {
    if (node == null || node.parent == null ||
        node.parent.rightChild != node) {
      System.out.println("Illegal call to rotateLeft().  You have bug #2.");
      return;
    } 

    BinaryTreeNode exParent = node.parent;
    BinaryTreeNode subtreeParent = exParent.parent;

    // Move "node"'s left subtree to its former parent.
    exParent.rightChild = node.leftChild; 
    if (node.leftChild != null) {
      node.leftChild.parent = exParent;
    }

    // Make exParent become a child of "node".
    node.leftChild = exParent;
    exParent.parent = node;

    // Make "node" become a child of exParent's former parent.
    node.parent = subtreeParent;
    if (subtreeParent == null) {
      root = node;
    } else if (subtreeParent.rightChild == exParent) {
      subtreeParent.rightChild = node;
    } else {
      subtreeParent.leftChild = node;
    }
  }

  /** 
   *  zig() rotates "node" up through its parent.  (Note that this may entail
   *  either a rotation right or a rotation left.)
   *
   *  @param node the node to splay one step up the tree.
   **/
  private void zig(BinaryTreeNode node) {
    if (node == null || node.parent == null) {
      System.out.println("Illegal call to zig().  You have bug #3.");
      return;  // Cannot rotate.
    } 

    if (node == node.parent.leftChild) {
      rotateRight(node);
    } else if (node == node.parent.rightChild) {
      rotateLeft(node);
    } else {
      System.out.println("Illegal call to zig().  You have bug #4.");
    }
  }

  /** 
   *  zigZag() performs a zig-zag operation, thereby splaying "node" two steps
   *  closer to the root of the tree.
   *
   *  @param node the node to splay two steps up the tree.
   **/
  private void zigZag(BinaryTreeNode node) {
    if (node == node.parent.leftChild) {
      rotateRight(node);
      rotateLeft(node); 
    } else {
      rotateLeft(node);
      rotateRight(node); 
    }
  }

  /** 
   *  zigZig() performs a zig-zig operation, thereby splaying "node" two
   *  steps closer to the root of the tree.  Unlike a zig-zag operation,
   *  a zig-zig operation rotates "node"'s parent up through its grandparent
   *  first, then rotates "node" up through its parent.
   *
   *  @param node the node to splay two steps up the tree.
   **/
  private void zigZig(BinaryTreeNode node) {
    // Write your solution to Part I of the lab here.
    if(node == node.parent.leftChild){
      rotateRight(node.parent);
      rotateRight(node);
    }else{
      rotateLeft(node.parent);
      rotateLeft(node);
    }
  }

  /**
   *  splayNode() splays "node" to the root of the tree with a sequence of
   *  zig-zag, zig-zig, and zig operations.
   *
   *  @param node the node to splay to the root.
   **/
  private void splayNode(BinaryTreeNode node) {
    // When you do Part II of the lab, please replace the following faulty code
    // with your solution.
    while (node.parent != null) {
      BinaryTreeNode parent = node.parent;
      BinaryTreeNode grandparent = node.parent.parent;
      if(grandparent == null){
        zig(node);
      }else{
        if(node == parent.rightChild && parent == grandparent.leftChild || node == parent.leftChild && parent == grandparent.rightChild){
        zigZag(node);
      }else if(node == parent.rightChild && parent == grandparent.rightChild || node == parent.leftChild && parent == grandparent.leftChild){
        zigZig(node);
      }
      }
      
    }
    // The following line isn't really necessary, as the rotations update the
    // root correctly if splayNode() successfully splays "node" to the root,
    // but it's a useful reminder.
    root = node;
  }

  /** 
   *  insert() constructs and inserts a new Entry object, consisting of
   *  a (key, value) pair, into the dictionary, and returns a reference to the
   *  new Entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  @param key the key by which the entry can be retrieved.  Must be of
   *  a class that implements java.lang.Comparable.
   *  @param value an arbitrary object associated with the key.
   *  @return an Entry object referencing the key and value.
   **/
  public Entry insert(Object key, Object value) {
    Entry entry = new Entry(key, value);
    if (root == null) {
      root = new BinaryTreeNode(entry);
    } else {
      insertHelper(entry, (Comparable) key, root);
    }

    size++;
    return entry;
  }

  /**
   *  insertHelper() recursively does the work of inserting a new Entry object
   *  into the dictionary, including calling splayNode() on the new node.
   *
   *  @param entry the Entry object to insert into the tree.
   *  @param key the key by which the entry can be retrieved.
   *  @param node the root of a subtree in which the new entry will be
   *         inserted.
   **/
  private void insertHelper(Entry entry, Comparable key, BinaryTreeNode node) {
    if (key.compareTo(node.entry.key()) <= 0) {
      if (node.leftChild == null) {
	node.leftChild = new BinaryTreeNode(entry, node);
        splayNode(node.leftChild);
      } else {
	insertHelper(entry, key, node.leftChild);
      }
    } else {
      if (node.rightChild == null) {
	node.rightChild = new BinaryTreeNode(entry, node);
        splayNode(node.rightChild);
      } else {
	insertHelper(entry, key, node.rightChild);
      }
    }
  }

  /** 
   *  find() searches for an entry with the specified key.  If such an entry is
   *  found, it returns the Entry object; otherwise, it returns null.  If more
   *  than one entry has the key, one of them is chosen arbitrarily and
   *  returned.
   *
   *  @param key the search key.  Must be of a class that implements
   *         java.lang.Comparable.
   *  @return an Entry referencing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/
  public Entry find(Object key) {
    BinaryTreeNode node = findHelper((Comparable) key, root);
    if (node == null) {
      return null;
    } else {
      return node.entry;
    }
  }

  /**
   *  Search for a node with the specified key, starting from "node".  If
   *  a matching key is found (meaning that key1.compareTo(key2) == 0), return
   *  a node containing that key.  Otherwise, return null.
   *
   *  Be sure this method returns null if node == null.
   **/

  private BinaryTreeNode findHelper(Comparable key, BinaryTreeNode node) {
    if (node == null) {
      return null;
    }

    if (key.compareTo(node.entry.key()) < 0) {  // "key" < "node"'s key
      if (node.leftChild == null) {
        splayNode(node);  // Splay the last node visited to the root.
        return null;
      }
      return findHelper(key, node.leftChild);
    } else if (key.compareTo(node.entry.key()) > 0) { // "key" > "node"'s key
      if (node.rightChild == null) {
        splayNode(node);  // Splay the last node visited to the root.
        return null;
      }
      return findHelper(key, node.rightChild);
    } else { // "key" == "node"'s key
      splayNode(node);  // Splay the found node to the root.
      return node;
    }
  }

  /** 
   *  remove() searches for an entry with the specified key.  If such an entry
   *  is found, it removes the Entry object from the Dictionary and returns it;
   *  otherwise, it returns null.  If more than one entry has the key, one of
   *  them is chosen arbitrarily, removed, and returned.
   *
   *  @param key the search key.  Must be of a class that implements
   *         java.lang.Comparable.
   *  @return an Entry referencing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/
  public Entry remove(Object key) {
    //  Not implemented.
    return null;
  }

  /**
   *  Convert the tree into a string.
   **/

  public String toString() {
    if (root == null) {
      return "";
    } else {
      return root.toString();
    }
  }

  private static void makeUnbalancedTree(SplayTree tree) {
    tree.insert(new Integer(10), "A"); 
    tree.insert(new Integer(9), "B"); 
    tree.insert(new Integer(8), "C"); 
    tree.insert(new Integer(7), "D"); 
    tree.insert(new Integer(6), "E"); 
    tree.insert(new Integer(5), "F"); 
    tree.insert(new Integer(4), "G"); 
    tree.insert(new Integer(3), "H"); 
    tree.insert(new Integer(2), "I"); 
    tree.insert(new Integer(1), "J"); 
  }

  /**
   *  main() tests the splay tree.
   *
   * DO NOT CHANGE THE FOLLOWING TEST CODE!
   **/
  public static void main(String[] args) {
    SplayTree tree = new SplayTree();

    System.out.println("\nPART I:  Testing zigZig()\n");
    String shouldBe = null;

    System.out.println("Inserting 1G, 3O, 2O, 5J, 4D, 7B, 6O into Tree 1.");
    tree.insert(new Integer(1), "G");
    tree.insert(new Integer(3), "O");
    Entry testEntry = tree.insert(new Integer(2), "O");
    BinaryTreeNode testNode =
      tree.findHelper((Comparable) testEntry.key, tree.root);
    tree.insert(new Integer(5), "J");
    tree.insert(new Integer(4), "D");
    tree.insert(new Integer(7), "B");
    tree.insert(new Integer(6), "O");
    System.out.println("Tree 1 is:  " + tree);

    if (tree.toString().equals("(((((1G)2O)3O)4D)5J)6O(7B)")) {
      System.out.println("Skipping the rest of Part I.");
    } else if (tree.toString().equals("(((1G)2O(3O))4D(5J))6O(7B)")) {
      System.out.println("\nPerforming zig-zig on node with key 2.");
      tree.zigZig(testNode);
      System.out.println("Tree 1 is now:  " + tree);
      shouldBe = "(1G)2O((3O)4D((5J)6O(7B)))";
      if (tree.toString().equals(shouldBe)) {
	System.out.println("  Zig-zig successful.");
      } else {
	System.out.println("  ERROR:  SHOULD BE " + shouldBe);
      }
      System.out.println("\nAttempting to balance an unbalanced tree using only zig():");
      SplayTree unbalanced = new SplayTree();
      makeUnbalancedTree(unbalanced);
      System.out.println("\nInserting 10A, 9B, 8C, 7D, 6E, 5F, 4G, 3H, 2I, 1J");
      System.out.println("tree is:  " + unbalanced);
      shouldBe = "1J(2I(3H(4G(5F(6E(7D(8C(9B(10A)))))))))";
      if (!unbalanced.toString().equals(shouldBe)) {
	System.out.println("  ERROR:  SHOULD BE " + shouldBe);
      }
      unbalanced.testFind(10, "A");
      System.out.println("Tree 1 is now:  " + unbalanced);
      shouldBe = "(1J(2I(3H(4G(5F(6E(7D(8C(9B)))))))))10A";
      if (!unbalanced.toString().equals(shouldBe)) {
	System.out.println("  ERROR:  SHOULD BE " + shouldBe);
      } else {
        System.out.println("As you can see, the tree is still unbalanced.\n" +
                           "If there are no errors, go on to Part II.");
      }
    } else {
      System.out.println("ERROR:  splayNode() is returning incorrect results.");
    }

    System.out.println("\nPART II:  Testing splayNode()");

    System.out.println("\nCalling splayNode() on the unbalanced tree:\n");
    System.out.println("Inserting 10A, 9B, 8C, 7D, 6E, 5F, 4G, 3H, 2I, 1J");
    SplayTree splayTest = new SplayTree();
    makeUnbalancedTree(splayTest);
    System.out.println("tree is:  " + splayTest);
    splayTest.testFind(10, "A");
    System.out.println("The tree should be better balanced now: " + splayTest);
    shouldBe = "(1J((2I)3H((4G)5F((6E)7D((8C)9B)))))10A";
    if (!splayTest.toString().equals(shouldBe)) {
      System.out.println("  ERROR:  SHOULD BE " + shouldBe);
    }

    System.out.println("\nSome find() operations on a new tree to test splayNode():\n");
    System.out.println("Inserting 3A, 7B, 4C, 2D, 9E, 1F");
    SplayTree tree2 = new SplayTree();
    tree2.insert(new Integer(3), "A");
    tree2.insert(new Integer(7), "B");
    tree2.insert(new Integer(4), "C");
    tree2.insert(new Integer(2), "D");
    tree2.insert(new Integer(9), "E");
    tree2.insert(new Integer(1), "F");
    System.out.println("Tree 2 is:  " + tree2);
    shouldBe = "1F((2D(3A((4C)7B)))9E)";
    if (!tree2.toString().equals(shouldBe)) {
      System.out.println("  ERROR:  SHOULD BE " + shouldBe);
    }
    // tree2.testRemove(2, "(1F(3A((4C)7B)))9E");
    // tree2.testRemove(4, "((1F)3A)7B(9E)");
    // tree2.testFind(1, "F");
    // tree2.testFind(9, "E");
    // shouldBe = "(1F((3A)7B))9E";
    // if (!tree2.toString().equals(shouldBe)) {
    //   System.out.println("  ERROR:  SHOULD BE " + shouldBe);
    // }
    tree2.testFind(7, "B");
    System.out.println("Tree 2 is:  " + tree2);
    shouldBe = "(1F((2D)3A(4C)))7B(9E)";
    if (!tree2.toString().equals(shouldBe)) {
      System.out.println("  ERROR:  SHOULD BE " + shouldBe);
    }
    tree2.testFind(4, "C");
    System.out.println("Tree 2 is:  " + tree2);
    shouldBe = "((1F(2D))3A)4C(7B(9E))";
    if (!tree2.toString().equals(shouldBe)) {
      System.out.println("  ERROR:  SHOULD BE " + shouldBe);
    }
  }

  private void testRemove(int n, String shouldBe) {
    Integer key = new Integer(n);
    System.out.print("After remove(" + n + "):  ");
    remove(key);
    System.out.println(this);
    if (!toString().equals(shouldBe)) {
      System.out.println("  SHOULD BE " + shouldBe);
    }
  }

  private void testFind(int n, Object truth) {
    Integer key = new Integer(n);
    Entry entry = find(key);
    System.out.println("Calling find(" + n + ")");
    if (entry == null) {
      System.out.println("  returned null.");
      if (truth != null) {
        System.out.println("  SHOULD BE " + truth + ".");
      }
    } else {
      System.out.println("  returned " + entry.value() + ".");
      if (!entry.value().equals(truth)) {
        if (truth == null) {
          System.out.println("  SHOULD BE null.");
        } else {
          System.out.println("  SHOULD BE " + truth + ".");
        }
      }
    }
  }
}
