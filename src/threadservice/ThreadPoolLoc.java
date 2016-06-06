package threadservice;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolLoc {

    public void exec() throws InterruptedException {
        ArrayListContainer all = new ArrayListContainer();

//        Thread starter;
        ExecutorService e=Executors.newFixedThreadPool(15);
        SynchronizedThread s = new SynchronizedThread(1,all);
        e.execute(s);
//            starter = new Thread(s);
//            starter.start();
//            System.out.println(s.isDone());
        ArrayListContainerCon cons = new ArrayListContainerCon();
        ExecutorService ez=Executors.newFixedThreadPool(15);
        SynchronizedThreadCon sz = new SynchronizedThreadCon(2,cons);
        e.execute(sz);




        e.shutdown();
        while(!e.isTerminated()){
            Thread.sleep(500);
        }
    }
}