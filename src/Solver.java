import parcs.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Solver {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        task curtask = new task();
        curtask.addJarFile("Worker.jar");
//        Node n = fromFile(curtask.findFile("input"));

        byte[] target = MessageDigest.getInstance("SHA-256").digest("rabbit".getBytes(StandardCharsets.UTF_8));
        char[] charset = {'a', 'b', 'i', 'r', 't'};
        int[] start = {0, 0, 0, 0, 0, 0};
        int last = charset.length - 1;
        int[] end = {last, last, last, last, last, last};
        BFJob job = new BFJob("SHA-256", target, charset, start, end);

        AMInfo info = new AMInfo(curtask, null);
        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("BruteforceWorker");
        c.write(job);

        System.out.println("Waiting for result...");
        System.out.println("Result: " + (String) c.readObject());
        curtask.end();
    }
}
