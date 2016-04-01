/* GimmeYoNumber.java */

import java.util.Random;
import java.io.*;

class GimmeYoNumber {

  public final static int LISTSIZE = 13;
  public final static int NUMCANDIDATES = 30;
  public final static int CANDIDATERANGE = 100;

  public static void main(String[] args) {
    Random rand = new Random();
    int indexlist[] = new int[LISTSIZE];
    int candidates[] = new int[NUMCANDIDATES];
    int i, j, k;

    /* Create a list of LISTSIZE indexes, each indexing a randomly chosen */
    /*   candidate.  (A candidate may be chosen multiple times.)          */
    for (i = 0; i < LISTSIZE; i++) {
      indexlist[i] = rand.nextInt(NUMCANDIDATES);
    }

    /* Create one list for each of the two opponents. */
    for (k = 1; k <= 2; k++) {

      /* Generate NUMCANDIDATES candidate ints in the range           */
      /*   0...CANDIDATERANGE - 1.  Candidates are (already) sorted.  */
      /*   The number of candidates is smaller than the range because */
      /*   I want a high likelihood of duplicate keys.                */
      j = 0;
      for (i = 0; i < CANDIDATERANGE; i++) {
        if (rand.nextInt(CANDIDATERANGE - i) < NUMCANDIDATES - j) {
          candidates[j] = i;
          j++;
        }
      }

      /* Print the list of chosen candidates. */
      System.out.println("Opponent " + k + ":");
      for (i = 0; i < LISTSIZE; i++) {
        System.out.print(candidates[indexlist[i]] + "  ");
      }
      System.out.println("\n");
    }
  }
}
