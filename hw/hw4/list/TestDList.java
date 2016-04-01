  package list;
  public class TestDList {
  public static void main (String[] args) {
    // Fill in your solution for Part I here.
	  System.out.println("Now we are testing DList.");
	  DList sl1 = new DList();
    sl1.insertBack(new Integer(6));
    sl1.insertBack(new Integer(9));
    sl1.insertBack(new Integer(12));
    System.out.println("Here is a list after insertBack 6, 9, 12: "
		       + sl1.toString());
    System.out.println();
    
    
    sl1.insertFront(new Integer(3));
    sl1.insertBack(new Integer(15));
    System.out.println("Here is the same list after insertBack(15) and insertFront(3): "
		       + sl1.toString());
    newtest(sl1);

    testEmpty();
   testAfterInsertFront();
    testAfterinsertBack();
  }

    
  /**
   *  testEmpty() tests toString(), isEmpty(), length(), insertFront(), and
   *  insertBack() on an empty list.  Prints summary information of the tests
   *  and halts the program if errors are detected.
   **/

  private static void testEmpty() {
    DList lst1 = new DList();
    DList lst2 = new DList();
    System.out.println();
    System.out.println("Here is a list after construction: "
		       + lst1.toString());
    TestHelper.verify(lst1.toString().equals("[  ]"),
		      "toString on newly constructed list failed");
	
    System.out.println("isEmpty() should be true. It is: " +
		       lst1.isEmpty());
    TestHelper.verify(lst1.isEmpty() == true,
		      "isEmpty() on newly constructed list failed");    

    System.out.println("length() should be 0. It is: " +
		       lst1.length());
    TestHelper.verify(lst1.length() == 0, 
		      "length on newly constructed list failed");    
    lst1.insertFront(new Integer(3));
    System.out.println("Here is a list after insertFront(3) to an empty list: "
		       + lst1.toString());
    TestHelper.verify(lst1.toString().equals("[  3  ]"),
		      "InsertFront on empty list failed");
    lst2.insertBack(new Integer(5));
    System.out.println("Here is a list after insertBack(5) on an empty list: "
		       + lst2.toString());
    TestHelper.verify(lst2.toString().equals("[  5  ]"),
		      "insertBack on empty list failed");
  }

  /**
   *  testAfterInsertFront() tests toString(), isEmpty(), length(),
   *  insertFront(), and insertBack() after insertFront().  Prints summary
   *  information of the tests and halts the program if errors are detected.
   **/

  private static void testAfterInsertFront() {
    DList lst1 = new DList();
    lst1.insertFront(new Integer(3));
    lst1.insertFront(new Integer(2));
    lst1.insertFront(new Integer(1));
    System.out.println();
    System.out.println("Here is a list after insertFront 3, 2, 1: "
		       + lst1.toString());
    TestHelper.verify(lst1.toString().equals("[  1  2  3  ]"),
		      "InsertFronts on non-empty list failed");
    System.out.println("isEmpty() should be false. It is: " +
		       lst1.isEmpty());
    TestHelper.verify(lst1.isEmpty() == false,
		      "isEmpty() after insertFront failed");    
    System.out.println("length() should be 3. It is: " +
		       lst1.length());
    TestHelper.verify(lst1.length() == 3, 
		      "length() after insertFront failed");
    lst1.insertBack(new Integer(4));
    System.out.println("Here is the same list after insertBack(4): "
		       + lst1.toString());
    TestHelper.verify(lst1.toString().equals("[  1  2  3  4  ]"),
		      "insertBack on non-empty list failed");
  }
    
  /**
   *  testAfterinsertBack() tests toString(), isEmpty(), length(),
   *  insertFront(), and insertBack() after insertBack().  Prints summary
   *  information of the tests and halts the program if errors are detected.
   **/

  private static void testAfterinsertBack() {
    DList lst1 = new DList();
    lst1.insertBack(new Integer(6));
    lst1.insertBack(new Integer(7));
    System.out.println();
    System.out.println("Here is a list after insertBack 6, 7: "
		       + lst1.toString());
    System.out.println("isEmpty() should be false. It is: " +
		       lst1.isEmpty());
    TestHelper.verify(lst1.isEmpty() == false,
		      "isEmpty() after insertBack failed");    
    System.out.println("length() should be 2. It is: " +
		       lst1.length());
    TestHelper.verify(lst1.length() == 2, 
		      "length() after insertBackfailed");
    lst1.insertFront(new Integer(5));
    System.out.println("Here is the same list after insertFront(5): "
		       + lst1.toString());
    TestHelper.verify(lst1.toString().equals("[  5  6  7  ]"),
		      "insertFront after insertBack failed");
  }
  private static void newtest(DList sl1) {
	  DListNode node = sl1.front();
	  System.out.println();
	  System.out.println("front() should be 3. It is: " +
		       sl1.front().item);
	  System.out.println("front's next should be 6. It is: " +
		       sl1.next(node).item);
	  System.out.println("front's next's prev should be 3. It is: " +
		       sl1.prev(sl1.next(node)).item);
	  sl1.remove(node);
	  System.out.println("After remove the front, the front() should be 6. It is: " +
		       sl1.front().item);
	  node = sl1.front();
	  sl1.insertBefore(new Integer(5),node);
	  sl1.insertAfter(new Integer(8),node);
	  System.out.println("After insertBefore() and insertAfter(), The first 3 nodes should be 5,6,8. The list is: " +
			  sl1.toString());
	  
  }
  }