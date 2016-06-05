package gui;

import javax.swing.*;


public class Filegui extends JPanel {

    public Filegui(){
        JFileChooser fc = new JFileChooser();
        JButton b = new JButton("Save");
        this.add(b);
        int returnVal = fc.showOpenDialog(b);

        //if (returnVal == JFileChooser.APPROVE_OPTION) {
        //    File file = fc.getSelectedFile(); //το αρχείο που επέλεξε
        //    System.out.println(file.getName());
        // }
        // else {
        // }
    }
}
