/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue p = new LinkedQueue();
    int length = q.size();
    try{
      for(int i = 0; i < length; i++){
        Object o = q.dequeue();
        LinkedQueue newNode = new LinkedQueue();
        newNode.enqueue(o);
        p.enqueue(newNode);
      }
    }catch(QueueEmptyException e){
      System.err.println();
    }
    return p;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue q = new LinkedQueue();
    Comparable o1, o2;
    try{
      while(q1.size() > 0 && q2.size() > 0){
        o1 = (Comparable)(q1.front());
        o2 = (Comparable)(q2.front());
        if(o1.compareTo(o2) < 0){
          q.enqueue(o1);
          q1.dequeue();
        }else if(o1.compareTo(o2) > 0){
          q.enqueue(o2);
          q2.dequeue();
        }else{
          q.enqueue(o1);
          q.enqueue(o2);
          q1.dequeue();
          q2.dequeue();
        }
      }

      if(q1.size() > 0){
        q.append(q1);
        for(int i = 0; i < q1.size(); i++){
          q1.dequeue();
        }
      }

        if(q2.size() > 0){
        q.append(q2);
        for(int i = 0; i < q2.size(); i++){
          q2.dequeue();
        }
      }

    }catch(QueueEmptyException e){
      System.err.println();
    }
    return q;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try{
      Comparable o;
      while(qIn.size() > 0){
        o = (Comparable)qIn.front();
        if(o.compareTo(pivot) < 0){
          qSmall.enqueue(o);
        }else if(o.compareTo(pivot) == 0){
          qEquals.enqueue(o);
        }else if(o.compareTo(pivot) > 0){
          qLarge.enqueue(o);
        }
        qIn.dequeue();
      }
      
    }catch(QueueEmptyException e){
      System.err.println();
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    LinkedQueue p = makeQueueOfQueues(q);
    try{
      while(p.size() > 1){
        LinkedQueue p1 = (LinkedQueue)p.dequeue();
        LinkedQueue p2 = (LinkedQueue)p.dequeue();
        p.enqueue(mergeSortedQueues(p1, p2));
      }
      if(p.size() == 1){
        q.append((LinkedQueue)p.dequeue());
      }
    }catch(QueueEmptyException e){
      System.err.println();
    }
    
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if(q.size() == 1){
      return;
    }else if(q.size() > 1){
      LinkedQueue qS = new LinkedQueue();
      LinkedQueue qE = new LinkedQueue();
      LinkedQueue qL = new LinkedQueue();
      int position = (int)(q.size()*Math.random()) + 1;
      Comparable pivot = (Comparable)(q.nth(position));
      partition(q, pivot, qS, qE, qL);
      quickSort(qS);
      quickSort(qL);
      q.append(qS);
      q.append(qE);
      q.append(qL);
    }
    

  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    // Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch = new Timer();
    q = makeRandom(10000);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + 10000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(10000);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + 10000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch = new Timer();
    q = makeRandom(100000);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + 100000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(100000);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + 100000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
    stopWatch = new Timer();
    q = makeRandom(1000000);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + 1000000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(1000000);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + 1000000 + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }

}
