import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{
    private Long producerID;
    private AtomicInteger tmpReq;
    private AtomicInteger servedNum;
    private AtomicInteger failedNum;
    private LinkedBlockingDeque<myReq> servings;
    public static final int SERV_RANGE = 50;
    public static final int TIMEOUT = 1000;
    public static final int REST = 1000;
    public static final int SWITCH_FIFO = 40;

    public Producer(
            AtomicInteger tmpReq,
            AtomicInteger servedNum,
            AtomicInteger failedNum,
            LinkedBlockingDeque<myReq> reqQue)
    {
        this.tmpReq = tmpReq;
        this.servedNum = servedNum;
        this.failedNum = failedNum;
        this.servings = reqQue;
    }

    @Override
    public void run() {
        this.producerID = Thread.currentThread().getId();
        try {
            while (true)
            {
                Random rand = new Random();
                Integer count = this.tmpReq.addAndGet(rand.nextInt(SERV_RANGE) + 1);
                System.out.println("Producer-"+ this.producerID + ": serving size - " +  this.servings.size());
                if(this.servings.size() > SWITCH_FIFO) {
                    try{
                        myReq request = this.servings.getLast();
                        if(request.size <= this.tmpReq.get())
                        {
                            this.tmpReq.updateAndGet(x -> (x - request.size));
                            this.servings.remove(request);
                            this.servedNum.incrementAndGet();
                            System.out.println("Producer-"+ this.producerID + ": served size - " +  this.servings.size() + " (FILO)");
                        }
                        else{
                            break;
                        }
                    }
                    catch (NoSuchElementException e)
                    {
                        break;
                    }
                } else {
                    while(true)
                    {
                        try{
                            myReq request = this.servings.getFirst();
                            if(request.size <= this.tmpReq.get())
                            {
                                this.tmpReq.updateAndGet(x -> (x - request.size));
                                this.servings.remove(request);
                                this.servedNum.incrementAndGet();
                                System.out.println("Producer-"+ this.producerID + ": served size - " +  this.servings.size() + " (FIFO)");
                            }
                            else{
                                break;
                            }
                        }
                        catch (NoSuchElementException e)
                        {
                            break;
                        }
                    }
                }
                while(true)
                {
                    long now = System.currentTimeMillis();
                    try{
                        myReq request = this.servings.getFirst();
                        if(now - request.timestamp > TIMEOUT)
                        {
                            System.out.println("Producer-"+ this.producerID + ": dropped size - " +  this.servings.size());
                            this.servings.takeFirst();
                            this.failedNum.incrementAndGet();
                        }
                        else{
                            break;
                        }
                    }catch (NoSuchElementException ignored)
                    {
                        break;
                    }
                }
                Thread.sleep(REST);
            }
        } catch (Exception e) {
            System.out.println("Producer-"+ this.producerID + ": quit.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Producer-"+ this.producerID + ": quit.");
        }

    }
}
