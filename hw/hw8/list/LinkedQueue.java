/* LinkedQueue.java */

package list;

public class LinkedQueue implements Queue {

  private SListNode head;
  private SListNode tail;
  private int size;

  /**
   *  LinkedQueue() constructs an empty queue.
   **/
  public LinkedQueue() {
    size = 0;
    head = null;
    tail = null;
  }

  /** 
   *  size() returns the size of this Queue.
   *  @return the size of this Queue.
   *  Performance:  runs in O(1) time.
   **/
  public int size() {
    return size;
  }

  /**
   *  isEmpty() returns true if this Queue is empty, false otherwise.
   *  @return true if this Queue is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   *  enqueue() inserts an object at the end of the Queue.
   *  @param item the item to be enqueued.
   **/
  public void enqueue(Object item) {
    if (head == null) {
      head = new SListNode(item);
      tail = head;
    } else {
      tail.next = new SListNode(item);
      tail = tail.next;
    }
    size++;
  }

  /**
   *  dequeue() removes and returns the object at the front of the Queue.
   *  @return the item dequeued.
   *  @throws a QueueEmptyException if the Queue is empty.
   **/
  public Object dequeue() throws QueueEmptyException {
    if (head == null) {
      throw new QueueEmptyException();
    } else {
      Object o = head.item;
      head = head.next;
      size--;
      if (size == 0) {
        tail = null;
      }
      return o;
    }
  }

  /**
   *  front() returns the object at the front of the Queue.
   *  @return the item at the front of the Queue.
   *  @throws a QueueEmptyException if the Queue is empty.
   **/
  public Object front() throws QueueEmptyException {
    if (head == null) {
      throw new QueueEmptyException();
    } else {
      return head.item;
    }
  }

  /**
   *
   *  nth() returns the nth item in this LinkedQueue.
   *    Items in the queue are numbered from 1.
   *  @param n the number of the item to return.
   */
  public Object nth(int n) {
    SListNode node = head;
    for (; n > 1; n--) {
      node = node.next;
    }
    return node.item;
  }

  /**
   *  append() appends the contents of q onto the end of this LinkedQueue.
   *    On completion, q is empty.
   *  @param q the LinkedQueue whose contents should be appended onto this
   *    LinkedQueue.
   **/
  public void append(LinkedQueue q) {
    if (head == null) {
      head = q.head;
    } else {
      tail.next = q.head;
    }
    if (q.head != null) {
      tail = q.tail;
    }
    size = size + q.size;
    q.head = null;
    q.tail = null;
    q.size = 0;
  }

  /**
   *  toString() converts this queue to a String.
   **/
  public String toString() {
    String out = "[ ";
    try {
      for (int i = 0; i < size(); i++) {
	out = out + front() + " ";
	enqueue(dequeue());
      }
    } catch (QueueEmptyException uf) {
      System.err.println("Error:  attempt to dequeue from empty queue.");
    }
    return out + "]";
  }
}
