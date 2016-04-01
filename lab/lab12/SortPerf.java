/* SortPerf.java */

import java.util.Random;
import java.io.*;

class SortPerf {

  private static final long INIT_SEED = 1234567;

  /**
   *  Times a sorting algorithm on data for arrays of size incN, 2 * incN, ...,
   *  maxN and writes the timing data to "outFileName".
   *
   *  For an array of each size, sorts the random data numTrials times to
   *  produce an total running time.
   **/
  public static void main(String[] argv) throws IOException {

    String outFileName = "";
    int incN = 0;
    int maxN = 0;
    int numTrials = 0;
    String sortAlg = "";
    if (argv.length != 5) {
      printUsage();
      return;
    } else try {
      sortAlg = argv[0];
      incN = Integer.parseInt(argv[1]);
      maxN = Integer.parseInt(argv[2]);
      numTrials = Integer.parseInt(argv[3]);
      outFileName = argv[4];
    } catch (Exception e) { 
      printUsage(); 
      return;
    }
    PrintWriter timings = new PrintWriter(new FileOutputStream(outFileName));
    timings.println("# Results for " + numTrials + " trials");
    timings.println("# n   time (msec)");
    timings.println("# ---------------");

    timeSort(timings, incN, maxN, numTrials, sortAlg);
    timings.close();
    System.out.println("done!  results in `" + outFileName + "'");
  }

  /** 
   *  Times a sorting algorithm on data for arrays of size incN, 2 * incN, ...,
   *  maxN.  Performs numTrials trials and computes the total running time.
   */
  private static void timeSort(PrintWriter timings, int incN, int maxN, 
			       int numTrials, String sortAlg) {
    Timer stopWatch = new Timer();
    for (int n = incN; n <= maxN; n += incN) {
      System.out.println("timing n == " + n + " ... ");
      stopWatch.reset();
      int[][] data = new int[numTrials + 1][];
      for (int t = 0; t < numTrials + 1; t++) {
	data[t] = randomData(n);
      }
      if (sortAlg.equals("insert")) {
	Sort.insertionSort(data[numTrials]);     // sort once without counting 
	stopWatch.start();
	for (int t = 0; t < numTrials; t++) {
	  Sort.insertionSort(data[t]);
	}
	stopWatch.stop();
      } else if (sortAlg.equals("select")) {
	Sort.selectionSort(data[numTrials]);     // sort once without counting 
	stopWatch.start();
	for(int t = 0; t < numTrials; t++) {
	  Sort.selectionSort(data[t]);
	}
	stopWatch.stop();
      } else if (sortAlg.equals("merge")) {
	Sort.mergeSort(data[numTrials]);         // sort once without counting 
	stopWatch.start();
	for (int t = 0; t < numTrials; t++) {
	  Sort.mergeSort(data[t]);
	}
	stopWatch.stop();
      } else if (sortAlg.equals("quick")) {
	Sort.quicksort(data[numTrials]);         // sort once without counting 
	stopWatch.start();
	for(int t = 0; t < numTrials; t++) {
	  Sort.quicksort(data[t]);
	}
	stopWatch.stop();
      } else if (sortAlg.equals("best")) {
	YourSort.sort(data[numTrials]);          // sort once without counting 
	stopWatch.start();
	for(int t = 0; t < numTrials; t++) {
	  YourSort.sort(data[t]);
	}
	stopWatch.stop();
      } else {
	printUsage();
	return;
      }
      long totalTime = stopWatch.elapsed();
      timings.println(n + "  " + totalTime);
    }
  }

  // Prints the contents of A.
  private static void printData(int[] A) {
    for (int i = 0; i < A.length - 1; i++) {
      System.out.print(A[i] + ", ");
    }
    if (A.length - 1 >= 0) {
      System.out.println(A[A.length - 1]);
    }
  }

  /**
   *  Assumes n > 0
   *  Returns an array of `n' randomly selected integers.
   **/
  private static int[] randomData(int n) {

    // choose same sequence of random numbers so that
    // we can fairly compare our sorting algorithms

    Random randGen = new Random(INIT_SEED);

    int[] newData = new int[n];
    for (int i = 0; i < n; i++) {
      newData[i] = randGen.nextInt();
    }

    return newData;
  }

  /** Print a message saying how the main method should be called. */
  private static void printUsage() {
    System.out.println("Usage:");
    System.out.println(" java SortPerf <sort> <incr> <max> <runs> <outfile>");
    System.out.println("    sort - one of insert, merge, quick, or best");
    System.out.println("    incr - the initial array size and increment");
    System.out.println("    max - the maximum array size");
    System.out.println("    runs - the number of runs for each size");
    System.out.println("    outfile - is the name of the timing output file");
  }
}
