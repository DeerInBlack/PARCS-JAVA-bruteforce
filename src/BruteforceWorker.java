import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import parcs.*;

public class BruteforceWorker implements AM {
    private int[] startIndices;
    private int[] endIndices;
    private int[] currentIndices;
    public char[] charset;
    public byte[] target;
    private char[] message;
    private String hashName;

    private void initWorker(BFJob job) {
        hashName = job.hashName;
        charset = job.charset;
        target = job.target;

        startIndices = job.startIndices.clone();
        endIndices = job.endIndices.clone();
        currentIndices = startIndices.clone();

        message = new char[currentIndices.length];
        for (int i = 0; i < currentIndices.length; i++) {
            message[i] = charset[currentIndices[i]];
        }
    }

    private boolean nextMessage() {
        for (int i = currentIndices.length - 1; i >= 0; i--) {
            if (currentIndices[i] < endIndices[i]) {
                message[i] = charset[++currentIndices[i]];
                return true;
            } else {
                message[i] = charset[currentIndices[i] = startIndices[i]];
            }
        }
        return false;
    }

    private String brute() throws NoSuchAlgorithmException {
        do {
            String m = String.valueOf(message);
            byte[] digest = MessageDigest.getInstance(hashName)
                    .digest(m.getBytes(StandardCharsets.UTF_8));
            if (Arrays.equals(digest, target)) return m;
        } while (nextMessage());
        return null;
    }

    @Override
    public void run(AMInfo info) {
        initWorker((BFJob) info.parent.readObject());
        System.out.printf("!Accepted job:\n\ttarget: %s\n\tcharset: %s\n\tstart: %s\n\tend: %s\n",
                target, Arrays.toString(charset), Arrays.toString(startIndices), Arrays.toString(endIndices));
        String result = null;
        try {
            result = brute();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.printf("Result: %s\n", result == null ? "%not found%" : result);
        info.parent.write(result);
    }
}
