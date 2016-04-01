/* SListNode.java */
package list;

/**
 *  SListNode is a class used internally by the SList class.  An SList object
 *  is a singly-linked list, and an SListNode is a node of a singly-linked
 *  list.  Each SListNode has two references:  one to an object, and one to
 *  the next node in the list.
 *
 *  @author Kathy Yelick and Jonathan Shewchuk
 */

public class SListNode {
  public Object item;
  public SListNode next;


  /**
   *  SListNode() (with two parameters) constructs a list node referencing the
   *  item "obj", whose next list node is to be "next".
   */

  public SListNode(Object obj, SListNode next) {
    item = obj;
    this.next = next;
  }

  /**
   *  SListNode() (with one parameter) constructs a list node referencing the
   *  item "obj".
   */

  public SListNode(Object obj) {
    this(obj, null);
  }
}
