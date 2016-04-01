/* DebugMe.java */

/**
 *  DebugMe is an application for summing a geometric series.
 **/
class DebugMe {

  /**
   *  main() prints the value of a geometric series.
   *  @param args is ignored.
   **/
  public static void main(String[] args) {

    int N = 3;
    ListNode geomSeries = createGeomSeries((double)1/2, N);
    double sum = listSum(geomSeries);

    System.out.println("for N = " + N + ",\n");
    System.out.println("       N  | 1 |^i");
    System.out.println("      sum |---|   = " + sum);
    System.out.println("      i=0 | 2 |\n");
  }

  /**
   *  createGeomSeries() creates a list built from ListNodes in which each item
   *  is a Double containing one term in the geometric series
   * 
   *               N
   *              sum (r^i).
   *              i=0
   *
   *  @param r is the base of the geometric series.
   *  @param N is the maximum exponent of any term in the geometric series.
   *           N is assumed to be non-negative.
   */
  public static ListNode createGeomSeries(double r, int N) {
    ListNode newTerm = new ListNode(null, null);
    if (N == 0) {
      newTerm.item = new Double(1.0);
      newTerm.next = null;
    } else {
      newTerm.next = createGeomSeries(r, N - 1);
      newTerm.item = new Double(((Double) newTerm.next.item).doubleValue() *
				r);
    }

    return newTerm;
  }


  /**
   *  listSum() computes the sum of terms in a list.  
   *  @param l is linked list represented by ListNodes.  Each item in the list
   *           is assumed to be a Double.
   */
  public static double listSum(ListNode l) {
    if (l == null) {
      return 0.0;
    }

    return ((Double) l.item).doubleValue() + listSum(l.next);
  }
}
