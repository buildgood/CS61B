/* Dictionary.java */

package dict;

/**
 *  An interface for dictionary ADTs.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public interface Dictionary {

  /** 
   *  size() returns the number of entries stored in the dictionary.  Entries
   *  with the same key (or even the same key and value) each still count as
   *  a separate entry.
   *
   *  @return the number of entries stored in the dictionary.
   **/
  public int size(); 

  /** 
   *  isEmpty() tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/
  public boolean isEmpty();

  /** 
   *  insert() constructs and inserts a new Entry object, consisting of
   *  a (key, value) pair, into the dictionary, and returns a reference to the
   *  new Entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object associated with the key.
   *  @return an Entry object referencing the key and value.
   **/
  public Entry insert(Object key, Object value);

  /** 
   *  find() searches for an entry with the specified key.  If such an entry is
   *  found, it returns the Entry object; otherwise, it returns null.  If more
   *  than one entry has the key, one of them is chosen arbitrarily and
   *  returned.
   *
   *  @param key the search key.
   *  @return an Entry referencing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/
  public Entry find(Object key);

  /** 
   *  remove() searches for an entry with the specified key.  If such an entry
   *  is found, it removes the Entry object from the Dictionary and returns it;
   *  otherwise, it returns null.  If more than one entry has the key, one of
   *  them is chosen arbitrarily, removed, and returned.
   *
   *  @param key the search key.
   *  @return an Entry referencing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/
  public Entry remove(Object key);

  /**
   *  makeEmpty() removes all the entries from the dictionary.
   */
  public void makeEmpty();

}
