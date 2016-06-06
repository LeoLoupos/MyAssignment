package threadservice;

import basics.Connection;
import storage.Database;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SynchronizedThreadCon implements Runnable {
    int threadid;
    boolean isDone;
    public ArrayListContainerCon threadcon;

    public SynchronizedThreadCon(int threadid,ArrayListContainerCon threadcon){
//        this.o=o;
        this.isDone=false;
        this.threadid=threadid;
        this.threadcon=threadcon;
    }
    public void run() {
        if(!Database.isConnected()){
            Database.connect("it21332","dit21332");
        }
        ArrayList<Connection> res = new ArrayList<>();
        res = Database.getAllCons();
        try {
            for (int i=0;i<res.size();i++){
                Connection test = res.get(i);
                threadcon.add(test);
                System.out.println(test.toString());

            }

            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Wake up");
        this.isDone=true;
    }

    public boolean isDone(){
        return isDone;
    }
}