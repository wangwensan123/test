package task;

/**
 * The {@code TimeStamp} Class used to create a time stamp
 * 
 *
 */
public class TimeStamp {

  /** The start. */
  long start;

  /**
   * Creates the.
   *
   * @return the time stamp
   */
  public static TimeStamp create() {
    return new TimeStamp();
  }

  /**
   * Instantiates a new time stamp.
   */
  public TimeStamp() {
    start = System.nanoTime();
  }

  /**
   * Sets the.
   *
   * @param s
   *          the s
   * @return the time stamp
   */
  public TimeStamp set(long s) {
    start = s;

    return this;
  }

  /**
   * past ms
   *
   * @deprecated
   * @return the long
   */
  public long past() {
    return pastms();
  }

  /**
   * past ms
   * 
   * @return
   */
  public long pastms() {
    return (System.nanoTime() - start) / 1000000;
  }

  /**
   * past us
   * 
   * @return
   */
  public long pastus() {
    return (System.nanoTime() - start) / 1000;
  }

  /**
   * past ns
   * 
   * @return
   */
  public long pastns() {
    return System.nanoTime() - start;
  }

  /**
   * Gets the.
   *
   * @return the long
   */
  public long get() {
    return start;
  }

  /**
   * Reset.
   *
   * @return the long
   */
  public long reset() {
    long r = past();
    start = System.nanoTime();
    return r;
  }

  public static void main(String[] args) {
    TimeStamp t = TimeStamp.create();
    System.out.println(t.past());
    System.out.println(t.pastns());
    System.out.println(t.past());
  }
}
