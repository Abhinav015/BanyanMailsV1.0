/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banyanmails;

import helper.BanyanAppBean;
import helper.Common;
import helper.MyTableModel;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectClients extends JFrame implements ActionListener {

    private JTable table;
    private JButton b1, b2, b3, b4;
    private String filePathName;
    private String attachFolder;
    MyTableModel model;
    Common cmn = new Common();

    public SelectClients(String str) {
        super(str);
    }

    public void init() {

        try {
            String[] colHeads = {"ID", "Name", "EMail", "Checkbox"};
            HashMap hm;
            try {
                hm = cmn.getImportExcelData(getFilePathName());
                int cols = 4;
                ArrayList<BanyanAppBean> ls = (ArrayList) hm.get("list");
                int noRec = ls.size();
                List data = (ArrayList) hm.get("data");
                Iterator itr = data.iterator();
                Object[][] tableData = new Object[noRec][cols];
                int i = 0;
                while (itr.hasNext()) {
                    for (int j = 1; j <= cols; j++) {
                        tableData[i][j - 1] = itr.next();
                    }
                    i++;
                }
                b1 = new JButton("Select All");
                b2 = new JButton("Clear All");
                b3 = new JButton("Ok");
                b4 = new JButton("Cancel");

                b1.setBounds(40, 270, 100, 27);
                b2.setBounds(180, 270, 100, 27);
                b3.setBounds(320, 270, 100, 27);
                b4.setBounds(460, 270, 100, 27);

                model = new MyTableModel(tableData, colHeads);
                table = new JTable(model);
                JScrollPane tableScroller = new JScrollPane(table);
                tableScroller.setBounds(2, 10, 593, 250);
                Container cp = getContentPane();
                cp.add(tableScroller);
                cp.add(b1);
                cp.add(b2);
                cp.add(b3);
                cp.add(b4);

                b1.addActionListener(this);
                b2.addActionListener(this);
                b3.addActionListener(this);
                b4.addActionListener(this);
                addWindowListener(new MyWindowAdapter1(this));
                setLayout(null);

                table.setRowHeight(22);

            } catch (IOException ex) {
                Logger.getLogger(SelectClients.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception in select clients");
            }
        } catch (Exception ex) {
            System.out.println("Exception in select clients");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Select All")) {
            int rows = model.getRowCount();
            int column = model.getColumnCount();
            for (int i = 0; i < rows; i++) {
                model.setValueAt(Boolean.TRUE, i, column - 1);
            }
        }

        if (e.getActionCommand().equals("Clear All")) {
            int rows = model.getRowCount();
            int column = model.getColumnCount();
            for (int i = 0; i < rows; i++) {
                model.setValueAt(Boolean.FALSE, i, column - 1);
            }
        }

        if (e.getActionCommand().equals("Ok")) {
            BanyanMailApp frm1 = new BanyanMailApp("Banyan Mail Application");
            frm1.setModel(model);
            frm1.setData(filePathName, attachFolder);
            frm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frm1.setResizable(false);
            frm1.setLocation(350, 200);
            frm1.setSize(600, 280);
            frm1.setVisible(true);
            this.dispose();
        }

        if (e.getActionCommand().equals("Cancel")) {
                 this.setVisible(false);
                 BanyanMails.frmShow(this);
        }
    }

    public String getFilePathName() {
        return filePathName;
    }

    public void setFilePathName(String filePathName) {
        this.filePathName = filePathName;
    }

    public String getAttachFolder() {
        return attachFolder;
    }

    public void setAttachFolder(String attachFolder) {
        this.attachFolder = attachFolder;
    }
    
    class MyWindowAdapter1 extends WindowAdapter {

        SelectClients sc = null;

        MyWindowAdapter1(SelectClients sc) {
            this.sc = sc;
        }

        public void windowClosing(WindowEvent e) {
            sc.setVisible(false);
            BanyanMails.frmShow(sc);
        }
    }
   
}
