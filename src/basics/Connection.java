package basics;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by user on 5/2/2016.
 */
public class Connection implements Comparator<Connection>, Comparable<Connection>, Serializable {

    public int id;
    public String fromname;
    public String toname;
    public int idf;
    public int idt;
    public boolean value;


    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "fromname='" + fromname + '\'' +
                ", toname='" + toname + '\'' +
                ", idf='" + idf + '\'' +
                ", idt='" + idt + '\'' +
                " value=" + value + '\'';
    }

    @Override
    public int compareTo(Connection o) {
        if (this.idf == o.idf) {
            return 0;
        } else if (this.idf < o.idf) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int compare(Connection o1, Connection o2) {
        if (o1.idf == o2.idf) {
            return 0;
        } else if (o1.idf < o2.idf) {
            return -1;
        } else {
            return 1;
        }
    }
}