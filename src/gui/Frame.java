package gui;

import basics.Json;
import basics.Location;
import storage.Database;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Frame extends JFrame {

    private DefaultListModel localModel;
    JTextField nameField;


    public Frame(DefaultListModel dlm) {
        this.localModel = dlm;
        JPanel jp = new JPanel();

        JPanel jloc = new JPanel();
        ConFrame jcon = new ConFrame();
        //Filegui jfile = new Filegui();
        JTabbedPane tabby = new JTabbedPane( );
        tabby.addTab("Location",jloc);
        tabby.addTab("Connection",jcon);
        //tabby.addTab("Save File",jfile);

        this.add(tabby);
        this.setLocation(500,500);
        this.setSize(1200, 200);
        MenuBar mb = new MenuBar();
        mb.add(new Menu("Location"));
        mb.add(new Menu("Connection"));
        this.setMenuBar(mb);
        this.setLayout(new FlowLayout());

        nameField = new JTextField("");
        nameField.setColumns(40);
        jloc.add(nameField);
        JButton b = new JButton("OK");
        jloc.add(b);
        JButton b2 = new JButton("Cancel");
        jloc.add(b2);

        //Connection frame Button;
        JButton Conb = new JButton("Cancel");
        jcon.add(Conb);

        this.getContentPane().add(jp);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        b.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {

                                    try {
                                        addButtonActionPerformed(evt,nameField.getText());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
        );


        b2.addActionListener(new java.awt.event.ActionListener() {
                                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                                     dispose();

                                 }
                             }
        );

        Conb.addActionListener(new java.awt.event.ActionListener() {
                                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                                       dispose();

                                   }
                               }
        );



    }


    private void addButtonActionPerformed(java.awt.event.ActionEvent evt,String name) throws IOException {
        JFrame j = new JFrame();
        if(localModel.contains(name)){
            ArrayList<Location> loc ;
            //ArrayListContainer ar = new ArrayListContainer();
            if(!Database.isConnected()){
                Database.connect("it21332","dit21332");
            }
            loc = Database.readCitiesFromDB();
            for(Location l : loc){
                System.out.println(l.getName());
                if(l.getName().equals(name)){
                    shower(l);
                }
            }

        }else{
            //// Crawl the city
            Location l = Json.getLoc(name);
            if(l.getId()==0){
                //Some Error Cities have id = 0
                JOptionPane.showMessageDialog(j,
                        "Your Location is invalid. Try again.",
                        "Location error",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                shower(l);
            }
        }
    }

    public void shower(Location l){
        JFrame jf =new JFrame();

        jf.setLocation(300,250);
        jf.setSize(400,200);
        String[] columnNames = {"Location Name", "ID", " X ", " Y "};
        Object[][] locs ={
                {l.getName(),l.getId(),l.getX(),l.getY()},
                {}
        };
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