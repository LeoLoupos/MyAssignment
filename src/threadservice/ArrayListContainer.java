package threadservice;

import basics.Location;

import java.util.ArrayList;

public class ArrayListContainer {


    public ArrayList<Location> alllocs;

    public ArrayListContainer() {
        alllocs = new ArrayList<>();
    }

    public synchronized void add(Location h) {
        alllocs.add(h);
    }

    public synchronized  int retSize(){
        return  alllocs.size();
    }



}
