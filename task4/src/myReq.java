import java.io.Serializable;
import java.util.Random;


public class myReq implements Serializable {
    public Integer size;
    public Long timestamp;
    public static final int MAXSIZE = 50;

    public myReq() {
        Random randSize = new Random();
        this.size = randSize.nextInt(MAXSIZE) + 1;
        this.timestamp = System.currentTimeMillis();
    }
}
