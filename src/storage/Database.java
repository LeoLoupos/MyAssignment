package storage;

import basics.Data;
import basics.Location;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public static Connection conn;

    public Database(String user, String pass){
        connect(user,pass);
    }

    //checker
    public static boolean isConnected() {
        try {
            if (conn != null && !conn.isClosed()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //connect to DB
    public static void connect(String username,String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:oracle:thin:@//10.100.51.123:1521/orcl";
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conn established");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //disc for DB
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/////  ALL METHODS LOCATIONS(INSERT,ALL,DELETE,SELECT)

    public static void storeLoc(Location loc) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO LOCATION VALUES(?,?,?,?)");
            pst.setString(1,loc.getName());
            pst.setInt(2,loc.getId());
            pst.setString(3,loc.getX());
            pst.setString(4,loc.getY());
            pst.addBatch();
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static ArrayList<Location> readCitiesFromDB() {
        ArrayList<Location> all=new ArrayList<Location>();
        try {
            Statement st = conn.createStatement();
            String sql="SELECT * FROM LOCATION";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                Location loc=new Location();
                loc.setName(rs.getString(1));
                loc.setId(rs.getInt(2));
                loc.setX(rs.getString(3));
                loc.setY(rs.getString(4));
                all.add(loc);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    public static void clearLocs() {
        try {
            String sql="DELETE FROM LOCATION";
            Statement st = conn.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /*
    public static void storeAllLocs(ArrayList<Location> locs) {
        try {


            PreparedStatement pst = conn.prepareStatement("INSERT INTO LOCATION VALUES(?,?,?,?)");

            for (Location loc : locs) {
                pst.setString(1,loc.getName());
                pst.setInt(2,loc.getId());
                pst.setString(3,loc.getX());
                pst.setString(4,loc.getY());
                pst.addBatch();
            }
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    public static void writeCitiesToDB(ArrayList<Location> cities) throws DBHasDataException {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO LOCATION VALUES(?,?,?,?)");
            ResultSet check = pst.executeQuery("SELECT * FROM LOCATION ");
            if (check.next() == true) {//and if its true throws the DBexception
                throw new DBHasDataException("Db is already containing data.");
            }
            for (Location loc : cities) {
                pst.setString(1, loc.getName());
                pst.setInt(2, loc.getId());
                pst.setString(3, loc.getX());
                pst.setString(4, loc.getY());
                pst.addBatch();
            }
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DBHasDataException e) {
            e.printStackTrace();
        }
    }


    //ALL METHODS CONNECTIONS
    public static void storeCon(basics.Connection con) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO CONNECTION VALUES(?,?,?,?,?)");
            pst.setString(1,con.getFromname());
            pst.setString(2,con.getToname());
            pst.setInt(3,con.getIdf());
            pst.setInt(4,con.getIdt());
            pst.setInt(5,con.getId());
            pst.addBatch();
            pst.executeBatch();
            pst.close();
            System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void storeAllCons(ArrayList<basics.Connection> cons) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO CONNECTION VALUES(?,?,?,?,?)");
            for (basics.Connection con : cons) {
                pst.setString(1,con.getFromname());
                pst.setString(2,con.getToname());
                pst.setInt(3,con.getIdf());
                pst.setInt(4,con.getIdt());
                pst.setInt(5,con.getId());
                pst.addBatch();
            }
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void clearCons() {
        try {
            Statement st = conn.createStatement();
            String sqls="DELETE FROM CONNECTION";
            st.execute(sqls);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<basics.Connection> getAllCons() {
        ArrayList<basics.Connection> all=new ArrayList<basics.Connection>();
        try {
            Statement st = conn.createStatement();
            String sql="SELECT * FROM CONNECTION ";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                basics.Connection con = new basics.Connection();
                con.setFromname(rs.getString(1));
                con.setToname(rs.getString(2));
                con.setIdf(rs.getInt(3));
                con.setIdt(rs.getInt(4));
                con.setId(rs.getInt(5));

                all.add(con);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }


    ///method for GUI
    public static basics.Connection getCon(String namef,String namet){
        basics.Connection con = new basics.Connection();

        try {
            Statement st = conn.createStatement();
            String sql="SELECT * FROM CONNECTION WHERE NAMEFR='"+namef + "' AND NAMETO='"+ namet + "'";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()) {
                con.setFromname(rs.getString(1));
                con.setToname(rs.getString(2));
                con.setIdf(rs.getInt(3));
                con.setIdt(rs.getInt(4));
                con.setId(rs.getInt(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;

    }

    //ALL METHODS CONNECTIONS
    public static void storeData(Data data) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO DATATAB VALUES(?,?,?,?)");
            pst.setString(1,data.getDepart());
            pst.setString(2,data.getArrival());
            pst.setInt(3,data.getId());
            pst.setInt(4,data.getRealid());
            pst.addBatch();
            pst.executeBatch();
            pst.close();
            System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Methods for Data(arrival,Departure) conns.
    public static void storeAllData(ArrayList<Data> data) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO DATATAB VALUES(?,?,?)");
            for (Data dat : data) {
                pst.setInt(1,dat.getId());
                pst.setString(1,dat.getArrival());
                pst.setString(2,dat.getDepart());

                pst.addBatch();
            }
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Data> getAllData() {
        ArrayList<Data> all=new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            String sql="SELECT * FROM DATATAB ";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                Data dat = new Data();
                dat.setId(rs.getInt(1));
                dat.setArrival(rs.getString(2));
                dat.setDepart(rs.getString(3));

                all.add(dat);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    public static void storeAllPaths(ArrayList<basics.Connection> cons) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO ALLPATHS VALUES(?,?,?,?,?,?)");
            for (basics.Connection con : cons) {
                pst.setString(1,con.getFromname());
                pst.setString(2,con.getToname());
                pst.setInt(3,con.getIdf());
                pst.setInt(4,con.getIdt());
                pst.setInt(5,con.getId());
                int myInt = (con.isValue()) ? 1 : 0;
                pst.setInt(6,myInt);
                pst.addBatch();
            }
            pst.executeBatch();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
