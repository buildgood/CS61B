/* BadTransactionException.java */

public class BadTransactionException extends Exception {

  public int amountNumber;  // The invalid account number.

  public BadTransactionException(int badAmount) {
    super("Invalid amount: " + badAmount);

    amountNumber = badAmount;
  }
}
