/* Entry.java */

package dict;

/**
 *  A class for dictionary entries, which are (key, value) pairs.
 *
 *  DO NOT CHANGE THIS FILE.  It is part of the interface of the Dictionary
 *  ADT.
 **/

public class Entry {

  protected Object key;
  protected Object value;

  protected Entry() {
  }

  protected Entry(Object k, Object v) {
    key = k;
    value = v;
  }

  public Object key() {
    return key;
  }

  public Object value() {
    return value;
  }
}
