import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingDeque<myReq> myContainer = new LinkedBlockingDeque<>(100);

        AtomicInteger totalReq = new AtomicInteger();
        AtomicInteger tmpReq = new AtomicInteger();
        AtomicInteger servedReq = new AtomicInteger();
        AtomicInteger failedReq = new AtomicInteger();

        Consumer c1 = new Consumer(myContainer, totalReq);
        Consumer c2 = new Consumer(myContainer, totalReq);
        Consumer c3 = new Consumer(myContainer, totalReq);
        Producer p1 = new Producer(tmpReq, servedReq, failedReq, myContainer);
        Producer p2 = new Producer(tmpReq, servedReq, failedReq, myContainer);
        Producer p3 = new Producer(tmpReq, servedReq, failedReq, myContainer);

        ExecutorService multiThreadServices = Executors.newCachedThreadPool();
        multiThreadServices.execute(p1);
        multiThreadServices.execute(p2);
        multiThreadServices.execute(p3);
        multiThreadServices.execute(c1);
        multiThreadServices.execute(c2);
        multiThreadServices.execute(c3);
        Integer running_sec = 5;
        Thread.sleep(running_sec*1000);
        multiThreadServices.shutdownNow();
        Thread.sleep(500);
        System.out.println("--------Report--------");
        System.out.println("Total req:         " + totalReq);
        System.out.println("Served req:        " + servedReq);
        System.out.println("Dropped req:       " + failedReq);
    }
}
