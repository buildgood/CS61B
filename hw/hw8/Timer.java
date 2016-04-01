/* Timer.java */

/**
 *  Implements a simple stopwatch/timer class based on wall-clock time.
 **/

/**
 *  RUNNING() == true  <==>  start() called with no corresponding
 *                           call to stop()
 *
 *  All times are given in units of msec.
 **/
public class Timer {

  private boolean running;
  private long tStart;
  private long tFinish;
  private long tAccum;

  /**
   *  Initializes Timer to 0 msec
   **/
  public Timer() {
    reset();
  }

  /**
   *  Starts the timer.  Accumulates time across multiple calls to start.
   **/
  public void start() {
    running = true;
    tStart = System.currentTimeMillis();
    tFinish = tStart;
  }

  /**
   *  Stops the timer.  returns the time elapsed since the last matching call
   *  to start(), or zero if no such matching call was made.
   **/
  public long stop() {
    tFinish = System.currentTimeMillis();
    if (running) {
      running = false;

      long diff = tFinish - tStart;
      tAccum += diff;
      return diff;
    }
    return 0;
  }

  /**
   *  if RUNNING()   ==>  returns the time since last call to start()
   *  if !RUNNING()  ==>  returns total elapsed time
   **/
  public long elapsed() {
    if (running) {
      return System.currentTimeMillis() - tStart;
    }

    return tAccum;
  }

  /**
   *  Stops timing, if currently RUNNING(); resets
   *          accumulated time to 0.
   */
  public void reset() {
    running = false;
    tStart = 0;
    tFinish = 0;
    tAccum = 0;
  }

}
