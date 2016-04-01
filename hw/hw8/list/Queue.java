/* Queue.java */

package list;

public interface Queue {

  /** 
   *  size() returns the size of this Queue.
   *  @return the size of this Queue.
   *  Performance:  runs in O(1) time.
   **/
  public int size();

  /**
   *  isEmpty() returns true if this Queue is empty, false otherwise.
   *  @return true if this Queue is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty();

  /**
   *  enqueue() inserts an object at the end of the Queue.
   *  @param item the item to be enqueued.
   **/
  public void enqueue(Object item);

  /**
   *  dequeue() removes and returns the object at the front of the Queue.
   *  @return the item dequeued.
   *  @throws a QueueEmptyException if the Queue is empty.
   **/
  public Object dequeue() throws QueueEmptyException;

  /**
   *  front() returns the object at the front of the Queue.
   *  @return the item at the front of the Queue.
   *  @throws a QueueEmptyException if the Queue is empty.
   **/
  public Object front() throws QueueEmptyException;

}
