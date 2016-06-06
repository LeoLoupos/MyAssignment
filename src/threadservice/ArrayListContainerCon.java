package threadservice;


import basics.Connection;

import java.util.ArrayList;

public class ArrayListContainerCon {



    public ArrayList<Connection> allcons;

    public ArrayListContainerCon() {
        allcons = new ArrayList<Connection>();
    }

    public synchronized void add(Connection h) {
        allcons.add(h);
    }
}