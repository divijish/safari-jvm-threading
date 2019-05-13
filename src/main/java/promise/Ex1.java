package promise;

import java.util.concurrent.CompletableFuture;

public class Ex1 {
  public static CompletableFuture<String> readFile(String filename) {
    CompletableFuture<String> result = new CompletableFuture<>();
    new Thread(() -> {
      System.out.println("Reading file...");
      try {
        Thread.sleep(1000 + (int)(Math.random()* 2000));
      } catch (InterruptedException ie) {}
      String fileContents = "The contents of the file " + filename +
            " are 'it is a fact universally acknowledged...'";
      result.complete(fileContents);
    }).start();
    return result;
  }
  public static void main(String[] args) {
    CompletableFuture<Void> cfv = CompletableFuture
        .supplyAsync(() -> { return "filename.txt";})
//        .thenApplyAsync((x) -> "The contents of the file " + x +
//            " are 'it is a fact universally acknowledged...'")
        .thenCompose((x) -> readFile(x))
        .thenAcceptAsync((x) -> System.out.println("Result is: " + x));
    System.out.println("Pipeline assembly completed...");
    cfv.join();
    System.out.println("Pipeline has completed execution...");
}
}
