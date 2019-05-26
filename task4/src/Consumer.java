import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private LinkedBlockingDeque<myReq> reqQue;
    private AtomicInteger reqNum;
    private Long consumerID;
    public static final int TIMEOUT = 1000;
    public static final int REQ_CYCLE = 100;

    public Consumer(LinkedBlockingDeque<myReq> reqsQue, AtomicInteger reqNum) {
        this.reqQue = reqsQue;
        this.reqNum = reqNum;
    }

    @Override
    public void run() {
        this.consumerID = Thread.currentThread().getId();
        try {
            while (true) {
                if(this.reqQue.offerLast(new myReq(),TIMEOUT, TimeUnit.MILLISECONDS))
                {
                    System.out.println();
                    this.reqNum.incrementAndGet();
                }
                else{
                    System.out.println("Consumer-" + this.consumerID + ": pushed a req.");
                }
                Thread.sleep(REQ_CYCLE);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer-" + this.consumerID + ": quit.");
        } finally {
            System.out.println("Consumer-" + this.consumerID + ": quit.");
        }
    }
}
