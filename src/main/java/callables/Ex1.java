package callables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class MyCallable implements Callable<String> {
  private static int nextJobId = 0;
  private String jobName = "Job " + ++nextJobId;

  @Override
  public String call() throws Exception {
    System.out.println(jobName + " starting");
    if (Math.random() > 0.6) throw new SQLException("DB broke");
    try {
      Thread.sleep((int) (Math.random() * 2000) + 1000);
    } catch (InterruptedException ie) {
      System.out.println("Shutting down cleanly...");
      return jobName + "Shutdown prematurely...";
    }
    System.out.println(jobName + " finished");
    return "finished: " + jobName;
  }
}

public class Ex1 {
  public static void main(String[] args) throws Throwable {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Future<String>> handles = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      handles.add(es.submit(new MyCallable()));
    }
    System.out.println("All jobs submitted");
    Thread.sleep(1_000);
    handles.get(1).cancel(true);
    es.shutdown();
    for (Future<String> h : handles) {
      try {
        String result = h.get(); // need to check h.isCancelled()
        System.out.println("Job produced: " + result);
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
        System.out.println("Job blew up, with " + e.getCause().getMessage());
      }
    }
    System.out.println("All jobs finished...");
  }
}
