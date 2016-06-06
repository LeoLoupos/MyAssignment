package threadservice;


import basics.Location;
import storage.Database;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


class SynchronizedThread implements Runnable {
    int threadid;
    boolean isDone;
    public ArrayListContainer threadhuman;

    public SynchronizedThread(int threadid,ArrayListContainer threadhuman){
//        this.o=o;
        this.isDone=false;
        this.threadid=threadid;
        this.threadhuman=threadhuman;
    }
    public void run() {
        if(!Database.isConnected()){
            Database.connect("it21332","dit21332");
        }
        ArrayList<Location> res = new ArrayList<>();
        res = Database.readCitiesFromDB();
        try {
            for (int i=0;i<res.size();i++){
                Location test = res.get(i);
                threadhuman.add(test);
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