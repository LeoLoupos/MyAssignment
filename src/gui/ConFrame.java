package gui;

import basics.Connection;
import basics.Data;
import basics.Json;
import storage.Database;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConFrame extends JPanel {

    public ArrayList<Connection> model;
    JTextField nameField1;
    JTextField nameField2;


    public ConFrame() {
        nameField1 = new JTextField("From");
        nameField1.setColumns(40);
        nameField2 = new JTextField("To");
        nameField2.setColumns(40);
        this.add(nameField1);
        this.add(nameField2);

        JButton b = new JButton("Search");
        this.add(b);


        b.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {

                                    try {
                                        addButtonActionPerformed(evt,nameField1.getText(),nameField2.getText());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
        );
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt,String namef,String namet) throws IOException,SQLException {
        // 3rd jtabbedpane, + file explorer
        if (!Database.isConnected()) {
            Database.connect("it21332", "dit21332");
        }
        Connection Conn = new Connection();
        Conn = Database.getCon(namef, namet);
        if(Conn.getIdf()== 0){
            JOptionPane.showMessageDialog(this,
                    "Your Location is invalid. Try again.",
                    "Location error",
                    JOptionPane.ERROR_MESSAGE);
        }
        ArrayList<Data> data ;
        data = Json.getData(Conn);
        if(data.size()==0){
            //Dialog
        }else {
            shower(data);
        }
    }

    public void shower(ArrayList<Data> dt){
        JFrame jf =new JFrame();

        jf.setLocation(600,500);
        jf.setSize(400,600);
        //String[] columnNames = {"From", "ID", "To", " ID "};
        String[] columnNames = {"Arrival", "Department"};
        Object[][] locs = new Object[2][dt.size()];
        for(int x=0;x<dt.size();x++){
            for(int y=0;y<2;y++){
                if(y==0) {
                    locs[y][x] = dt.get(x).getDepart();
                }else{
                    locs[y][x] = dt.get(y).getArrival();
                }
            }
        }



        TableModel modelt = new DefaultTableModel(locs,columnNames);
        JTable table = new JTable(modelt);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
        jf.add(table, BorderLayout.CENTER);
        jf.setVisible(true);
    }
}

