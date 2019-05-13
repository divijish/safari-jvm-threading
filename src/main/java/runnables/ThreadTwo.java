package runnables;

class MyJobTwo implements Runnable {
  // should be private to allow REAL encapsulation/protection
  public /*volatile*/ long count = 0;

  @Override
  public void run() {
    for (int i = 0; i < 10_000; i++) {
      synchronized (this) { // either "this" or "this.something"
//      count++;
        long newVal = count + 1;
        count = newVal;
      }
    }
    System.out.println(Thread.currentThread().getName() +
        " completed...");
  }
}

public class ThreadTwo {
  public static void main(String[] args) throws Throwable {
    MyJobTwo r = new MyJobTwo();
    new Thread(r).start();
    new Thread(r).start();
    Thread.sleep(1_000);
    System.out.println("total count is " + r.count); // this needs the volatile!!
  }
}
