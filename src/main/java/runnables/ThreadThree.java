package runnables;

class Stopper implements Runnable {
  public volatile boolean stop = false;

  @Override
  public void run() {
    System.out.println("Starting 'stopper' job...");
    while (!stop)
      ;
    System.out.println("'stopper' job finished...");
  }
}
public class ThreadThree {
  public static void main(String[] args) throws Throwable {
    Stopper s = new Stopper();
    new Thread(s).start();
    System.out.println("stopper kicked off...");
    Thread.sleep(1_000);
    s.stop = true;
    Thread.sleep(1_000);
    System.out.println("set stop to true... main exiting.");
  }
}
