import java.io.Serializable;
import java.util.List;


public class BFJob implements Serializable {
    public int[] startIndices;
    public int[] endIndices;
    public char[] charset;
    public byte[] target;
    public String hashName;

    public BFJob(String hash, byte[] target, char[] charset, int[] start, int[] end) {
        this.hashName = hash;
        this.target = target;
        this.charset = charset;
        this.startIndices = start;
        this.endIndices = end;
    }
}
