package banyanmails;

import helper.MyTableModel;
import helper.DatePicker;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BanyanMailApp extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5,jbutton;
    private JLabel l1, l2, l3,jlabel;
    private JTextField tf1, tf2,jdate;
    private String choosertitle;
    private SendMailApp sma = new SendMailApp("Banyan Mail Application");
    private SelectClients sc = new SelectClients("Banyan Mail Application");
    private MyTableModel model;
    private JFileChooser chooser = new JFileChooser();
    private ImageIcon attach = new ImageIcon((getClass().getResource("/images/attac.png")));
    private ImageIcon xls = new ImageIcon((getClass().getResource("/images/XLS.png")));
    private ImageIcon dt = new ImageIcon((getClass().getResource("/images/date.png")));
    private String attachFol;
    private String flPath;

    public BanyanMailApp(String msg) {
        super(msg);
        l1 = new JLabel("Attachments Folder Path", attach, JLabel.LEFT);
        l2 = new JLabel(" Select Client File", xls, JLabel.LEFT);
        l3 = new JLabel("Date of Report", JLabel.LEFT);

        tf1 = new JTextField(40);
        tf2 = new JTextField(40);
        b1 = new JButton("Send");
        b2 = new JButton("...");
        b3 = new JButton("...");
        b4 = new JButton("View Selection");
        b5 = new JButton("close");
        
        jlabel=new JLabel("Report Date ");
        jdate=new JTextField(40);
        jbutton=new JButton(dt);
        
        
        l1.setBounds(10, 50, 180, 30);
        l2.setBounds(10, 90, 160, 30);
        tf1.setBounds(190, 55, 300, 22);
        tf2.setBounds(190, 95, 300, 22);
        b1.setBounds(250, 150, 100, 27);
        b2.setBounds(500, 52, 30, 22);
        b3.setBounds(500, 92, 30, 22);
        b4.setBounds(90, 150, 130, 27);
        b5.setBounds(390, 150, 100, 27);
        jlabel.setBounds(10, 220, 100, 27);
        jdate.setBounds(80, 220, 130, 24);
        jbutton.setBounds(210, 217, 30, 27);  
                
        Container cp = getContentPane();
        cp.add(l1);
        cp.add(tf1);
        cp.add(l2);
        cp.add(tf2);
        cp.add(b1);
        cp.add(b2);
        cp.add(b3);
        cp.add(b4);
        cp.add(b5);
        cp.add(jlabel);
        cp.add(jdate);
        cp.add(jbutton);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        jbutton.addActionListener(this);
        setLayout(null);
        b1.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b1) {
            if (tf1.getText().equals("") || tf1.getText() == null || tf2.getText().equals("") || tf2.getText() == null
                    ||jdate.getText().equals("") || jdate.getText() == null) {
                JOptionPane.showMessageDialog(null, "Please select attachments folder path, client file(xlsx or xls) and report date.");
            } else {
                sma.setFilePath(getFlPath() == null ? tf2.getText() : getFlPath());
                sma.setAttchmentFolder(getAttachFol() == null ? tf1.getText() : getAttachFol());
                sma.setModel(model);
                sma.setLocation(350, 150);
                sma.setSize(600, 500);
                sma.setVisible(true);
                sma.setResizable(false);
                this.hide();
            }
        }

        if (e.getSource() == b2) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle(choosertitle);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                tf1.setText(chooser.getSelectedFile().toString());
                setAttachFol(chooser.getSelectedFile().toString());
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            } else {
                System.out.println("No Selection");
            }
        }

        if (e.getSource() == b3) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls");
            FileNameExtensionFilter filter1 = new FileNameExtensionFilter("XLSX files", "xlsx");
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle(choosertitle);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(filter);
            chooser.setFileFilter(filter1);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                tf2.setText(chooser.getSelectedFile().toString());
                setFlPath(chooser.getSelectedFile().toString());
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            } else {
                System.out.println("No Selection");
            }
        }

        if (e.getSource() == b4) {
            if (tf1.getText().equals("") || tf1.getText() == null || tf2.getText().equals("") || tf2.getText() == null) {
                JOptionPane.showMessageDialog(null, "Please select attachments folder path and client file(xlsx or xls).");
            } else {
                sc.setFilePathName(getFlPath() == null ? tf2.getText() : getFlPath());
                sc.setAttachFolder(getAttachFol() == null ? tf1.getText() : getAttachFol());
                sc.init();
                sc.setLocation(350, 200);
                sc.setSize(600, 350);
                sc.setVisible(true);
                sc.setResizable(false);
                this.dispose();
            }
        }

        if (e.getSource() == b5) {
            System.exit(0);
        }
        
        if(e.getSource()==jbutton){
           jdate.setText(new DatePicker(this).setPickedDate());
        }
    }

    public MyTableModel getModel() {
        return model;
    }

    public void setModel(MyTableModel model) {
        this.model = model;
    }

    public void setData(String file, String attach) {
        tf1.setText(attach);
        tf2.setText(file);
        b1.setEnabled(true);
    }

    public void setButtonDisbled() {
        b1.setEnabled(false);
    }

    public String getAttachFol() {
        return attachFol;
    }

    public void setAttachFol(String attachFol) {
        this.attachFol = attachFol;
    }

    public String getFlPath() {
        return flPath;
    }

    public void setFlPath(String flPath) {
        this.flPath = flPath;
    }
}
