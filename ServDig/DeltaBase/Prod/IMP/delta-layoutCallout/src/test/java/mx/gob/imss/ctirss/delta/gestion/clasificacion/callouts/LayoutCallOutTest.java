package mx.gob.imss.ctirss.delta.gestion.clasificacion.callouts;

import org.junit.Test;
import org.junit.Before;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.callouts.LayoutCallOut.generateLayout;

public class LayoutCallOutTest {

    private Stack stack;

    @Before
    public void setUp() throws IOException {
        try {
            FileUtils.forceDelete(new File("/home/r/Delta/Out/appendToThis"));
        }
        catch(FileNotFoundException fileNotFound) {
        }
    }

    @Test
    public void testGenerateLayout() throws IOException, InterruptedException {
        final Stack stack = new Stack() {{
            for (int i = 1;i<=20000;i++){ push(i); }
        }};

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50 ; i++) {
            service.execute(new RunnableLayoutHelper(stack));
        }
        service.shutdown();
        while(!service.isTerminated()) {
            Thread.sleep(250);
        }
    }

}

class RunnableLayoutHelper implements Runnable {


    private Stack stack;

    RunnableLayoutHelper(Stack stack) {
        this.stack = stack;
    }

    public void run() {
        for(int i = 0; i < 1200; i++) {
            if (stack.empty()) {
                break;
            }
            try {
                generateLayout("/home/r/Delta/OUT/appendToThis", (stack.pop().toString()
                        + "\tLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
                        + "\tLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.").getBytes());
            }
            catch(IOException e) {
                throw new RuntimeException("exception appending in file", e);
            }
        }
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

}

