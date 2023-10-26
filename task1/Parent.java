import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutionException;

public class Parent{
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
        AtomicInteger atomicInteger = new AtomicInteger();
		ProcessHandle current = ProcessHandle.current();
		System.out.println(current.pid());
		int n = sc.nextInt();
		for (int i = 0; i  < n; i++) {
			generateProcess(current, atomicInteger);
			// Process process = Runtime.getRuntime().exec("java Child " + random.nextInt(5, 10));
			// System.out.println("Parent[" + current.pid() + "]: I ran children process with PID " + process.pid());
			// process.onExit().thenAccept(x -> {
			// 	if (x.exitValue() == 1) {
			// 		generateProcess();
            // 	} else {
			// 		atomicInteger.incrementAndGet();
            // 	}
          	// 	System.out.println("Parent[" + current.pid() + "]: Child with PID " + process.pid() + " terminated. Exit Status " + process.exitValue());
        	// });	   

		}
		while (true) {
			if (atomicInteger.get() == n) {
				System.out.println("WIn");
				break;
			}
		}
	}	
	public static void generateProcess(ProcessHandle current, AtomicInteger atomicInteger) throws IOException {
		Random random = new Random();
		Process process = Runtime.getRuntime().exec("java Child " + random.nextInt(5, 10));
		System.out.println("Parent[" + current.pid() + "]: I ran children process with PID " + process.pid());
		process.onExit().thenAccept(x -> {
			if (x.exitValue() == 1) {
				try {
                    generateProcess(current, atomicInteger);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
           	} else {
				atomicInteger.incrementAndGet();
           	}
       		System.out.println("Parent[" + current.pid() + "]: Child with PID " + process.pid() + " terminated. Exit Status " + process.exitValue());
		});	 

	}
}