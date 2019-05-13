package runnables;

class MyJob implements Runnable {
  private int i = 0;
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " starting...");
    for (; i < 10_000; i++) {
      System.out.println(
          Thread.currentThread().getName() + " i is: " + i);
    }
    System.out.println(Thread.currentThread().getName() + " completed...");
  }
}
public class ThreadOne {
  public static void main(String[] args) {
    Runnable r = new MyJob();
    Thread t1 =  new Thread(r);
    Thread t2 =  new Thread(r);
//    t1.run(); // NONONONO!!! BAD! equivalent to r.run() synchronous
//    t1.setDaemon(true);
    t1.start(); // triggers CONCURRENT execution.
    t2.start();
    System.out.println("Thread kicked off... main exiting");
  }
}
