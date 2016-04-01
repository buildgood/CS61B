/* Set.java */

import list.*;

/*\
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
  List setList;
  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
    setList = new DList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return setList.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
    // Your solution here.
    if(setList.isEmpty()){
      setList.insertFront(c);
    }else{
      ListNode current = null;
    try{
      current = setList.front();
      while(current != setList.back() && ((Comparable)(current.item())).compareTo(c) < 0){
        current = current.next();
      }
      if(((Comparable)(current.item())).compareTo(c) > 0){
        current.insertBefore(c);
      }else if(current == setList.back() && ((Comparable)(current.item())).compareTo(c) < 0){
        current.insertAfter(c);
      }
      
    }catch(InvalidNodeException e){
      System.err.println("insert() failed.");
    }
  }
    
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) {
    // Your solution here.
    if(s != null && !s.setList.isEmpty()){
      ListNode curNode = setList.front();
      ListNode sNode = s.setList.front();
      Comparable curItem, sItem; 
      try{

      while(curNode.isValidNode() && sNode.isValidNode()){
        curItem = (Comparable)curNode.item();
        sItem = (Comparable)sNode.item();
        if(curItem.compareTo(sItem) == 0){
          curNode = curNode.next();
          sNode = sNode.next();
        }else if(curItem.compareTo(sItem) < 0){
          curNode = curNode.next();
        }else{
          curNode.insertBefore(sItem);
          sNode = sNode.next();
        }
      }

      while(sNode.isValidNode()){
        sItem = (Comparable)sNode.item();
        setList.insertBack(sItem);
        sNode = sNode.next();
      }

    }catch(InvalidNodeException e){
      System.err.println("union() failed");
    }
  }

  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
    // Your solution here.
    if(s != null && !s.setList.isEmpty()){
      ListNode curNode = setList.front();
      ListNode sNode = s.setList.front();
      Comparable curItem, sItem;
      try{

        while(curNode.isValidNode() && sNode.isValidNode()){
          curItem = (Comparable)curNode.item();
          sItem = (Comparable)sNode.item();
          if(curItem.compareTo(sItem) == 0){
          curNode = curNode.next();
          sNode = sNode.next();
          }else if(curItem.compareTo(sItem) < 0){
              if(curNode != setList.back()){
                curNode = curNode.next();
                curNode.prev().remove();
              }else{
                curNode.remove();
              }
          }else{
          sNode = sNode.next();
          }
        }

        while(curNode.isValidNode()){
          if(curNode != setList.back()){
                curNode = curNode.next();
                curNode.prev().remove();
              }else{
                curNode.remove();
              }
        }

      }catch(InvalidNodeException e){
        System.err.println("intersect() failed.");
      }

    }
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
    String p = "{  ";
    ListNode curNode = setList.front();
    try{
      while(curNode.isValidNode()){
        p = p + curNode.item() + "  ";
        curNode = curNode.next();
      }
    }catch(InvalidNodeException e){
      System.err.println("toString() failed.");
    }
    p = p + "}";
    return p;
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
    Set s4 = new Set();
    s4.insert(new Integer(3));
    s4.insert(new Integer(2));
    s4.insert(new Integer(1));
    System.out.println("Set s4 = " + s4);

    Set s5 = new Set();
    System.out.println("Set s5 = " + s5);

    s4.union(s5);
    System.out.println("After s4.union(s5), s4 = " + s4);

    Set s6 = new Set();
    System.out.println("Set s6 = " + s6);

    Set s7 = new Set();
    s7.insert(new Integer(3));
    s7.insert(new Integer(2));
    s7.insert(new Integer(1));
    System.out.println("Set s7 = " + s7);

    s6.union(s7);
    System.out.println("After s6.union(s7), s6 = " + s6);

    s6.intersect(s4);
    System.out.println("After s6.intersect(s4), s6 = " + s6);

    Set s8 = new Set();
    System.out.println("Set s8 = " + s8);

    Set s9 = new Set();
    s9.insert(new Integer(3));
    s9.insert(new Integer(2));
    s9.insert(new Integer(1));
    System.out.println("Set s9 = " + s9);

    Set s10 = new Set();
    System.out.println("Set s10 = " + s10);

    s8.intersect(s9);
    System.out.println("After s8.intersect(s9), s8 = " + s8);

    s10.intersect(s8);
    System.out.println("After s10.intersect(s8), s10 = " + s10);

    s10.intersect(s9);
    System.out.println("After s10.intersect(s9), s10 = " + s10);

    Set s11 = new Set();
    s11.insert(new Integer(4));
    s11.insert(new Integer(8));
    s11.insert(new Integer(5));
    System.out.println("Set s11 = " + s11);
    Set s14 = s11;

    Set s12 = new Set();
    s12.insert(new Integer(1));
    s12.insert(new Integer(3));
    s12.insert(new Integer(5));
    s12.insert(new Integer(7));
    s12.insert(new Integer(9));
    System.out.println("Set s12 = " + s12);
    Set s13 = s12;

    s11.intersect(s12);
    System.out.println("After s11.intersect(s12), s11 = " + s11);

    System.out.println("Set s13 = " + s13);

    System.out.println("Set s14 = " + s14);

    s13.intersect(s12);
    System.out.println("After s13.intersect(s12), s13 = " + s13);

    s13.intersect(s14);
    System.out.println("After s13.intersect(s14), s13 = " + s13);


    System.out.println("s13.cardinality() = " + s13.cardinality());
  }
}
