import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Child {
    public static void main(String[] args) throws InterruptedException {
        ProcessHandle current = ProcessHandle.current();
        System.out.println("child[" + current.pid() + "]: I am started. My PID "
                + current.pid() + ". Parent PID " + current.parent().get().pid());

        Thread.sleep(Integer.parseInt(args[0]) * 1000);
        System.out.println("child[" + current.pid() + "]: I am ended. My PID "
                + current.pid() + ". Parent PID " + current.parent().get().pid());
        List<Integer> rand = new ArrayList<>();
        rand.add(0);
        rand.add(1);
        Collections.shuffle(rand);
        if (rand.get(0) == 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }
}