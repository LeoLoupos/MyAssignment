package basics;

import java.io.Serializable;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Location implements Comparator<Location>, Comparable<Location>, Serializable {

    public Location(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Location() {
        this.name = "";
        this.id = 0;
    }

    public Location(String line){
        StringTokenizer st=new StringTokenizer(line, ",= ");
        st.nextToken();
        this.name=st.nextToken();
        st.nextToken();
        this.id=Integer.parseInt(st.nextToken());

    }
    public String name;
    public int id;
    public String x;
    public String y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", id=" + id +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' ;
    }

    @Override
    public int compare(Location o1, Location o2) {
        if (o1.id == o2.id) {
            return 0;
        } else if (o1.id < o2.id) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int compareTo(Location o) {
        if (this.id == o.id) {
            return 0;
        } else if (this.id < o.id) {
            return -1;
        } else {
            return 1;
        }    }
}
