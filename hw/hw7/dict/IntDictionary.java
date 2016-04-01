/* IntDictionary.java */

package dict;

/**
 *  An IntDictionary is a mutable ordered dictionary ADT.  Each item is an int
 *  key; there are no additional values or objects associated with the key.
 *
 *  DO NOT CHANGE THIS FILE.
 *
 *  @author Jonathan Shewchuk
 */

public abstract class IntDictionary {

  /**
   *  size is the number of items in the dictionary.
   **/

  protected int size;

  /**
   *  isEmpty() returns true if this IntDictionary is empty, false otherwise.
   *
   *  @return true if this IntDictionary is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  size() returns the size of this IntDictionary.
   *
   *  @return the size of this IntDictionary.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int size() {
    return size;
  }

}
