package banyanmails;

import helper.BanyanAppBean;
import helper.Common;
import helper.MyTableModel;
import helper.SendMailAPI;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import logs.Logger;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class SendMailApp extends JFrame implements ActionListener, Runnable {

    private JLabel lblsub, lblbody, lblsign, lbluname, lblpname, lblstatus;
    private JPasswordField tfpname;
    private JTextField tfsub, tfuname, tasign1;
    private JButton email, close;
    private JTextArea tabody, tasign2;
    private String emailId = "";
    private String name = "";
    private String salutation = "";
    private JScrollPane scrolll;
    private JProgressBar pb;
    private MyTableModel model;
    private Thread load;
    private String attchmentFolder;
    private String filePath;
    Logger log = new Logger();
    String data = "";
    ImageIcon mail = new ImageIcon((getClass().getResource("/images/Mail-icon.png")));

    public SendMailApp() {
    }

    public SendMailApp(String str) {
        super(str);
        lblsub = new JLabel("Subject", JLabel.LEFT);
        lblbody = new JLabel("Body", JLabel.LEFT);
        lblsign = new JLabel("Signature", JLabel.LEFT);
        lbluname = new JLabel("Username", JLabel.LEFT);
        lblpname = new JLabel("Password", JLabel.LEFT);
        lblstatus = new JLabel("Status", JLabel.LEFT);

        tfsub = new JTextField(40);
        tfuname = new JTextField(40);
        tfpname = new JPasswordField(40);
        email = new JButton("Email", mail);
        tabody = new JTextArea(40, 60);
        tasign1 = new JTextField(40);
        tasign2 = new JTextArea(10, 30);

        pb = new JProgressBar();
        pb.setValue(0);
        pb.setStringPainted(true);

        close = new JButton("Exit");

        tasign1.setText("Banyan Tree Advisors");
        tasign2.setText("Bangalore, India.\n M : +91-XXXXXXXXXX");

        scrolll = new JScrollPane(tabody, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrolll.setVisible(true);
        JScrollPane scroll3 = new JScrollPane(tasign2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrolll.setVisible(true);

        lblsub.setBounds(10, 20, 50, 30);
        lblbody.setBounds(10, 60, 160, 30);
        lblsign.setBounds(10, 170, 160, 30);
        lbluname.setBounds(10, 285, 160, 30);
        lblpname.setBounds(10, 330, 160, 30);
        lblstatus.setBounds(90, 396, 400, 30);

        tfsub.setBounds(90, 20, 450, 27);
        tfuname.setBounds(90, 285, 220, 27);
        tfpname.setBounds(90, 330, 220, 27);
        scrolll.setBounds(90, 60, 450, 100);
        tasign1.setBounds(90, 170, 450, 27);
        scroll3.setBounds(90, 210, 450, 60);
        email.setBounds(180, 430, 100, 30);
        close.setBounds(330, 430, 100, 30);
        pb.setBounds(90, 370, 450, 25);

        Container cp = getContentPane();
        cp.add(lblsub);
        cp.add(lblbody);
        cp.add(lblsign);
        cp.add(lbluname);
        cp.add(lblpname);
        cp.add(lblstatus);
        cp.add(tfsub);
        cp.add(tfuname);
        cp.add(tfpname);
        cp.add(scrolll);
        cp.add(tasign1);
        cp.add(scroll3);
        cp.add(email);
        cp.add(close);
        cp.add(pb);
        email.addActionListener(this);
        close.addActionListener(this);
        addWindowListener(new MyWindowAdapter(this));
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == email) {
            if (tfsub.getText().equals("") || tfsub.getText() == null || tfuname.getText().equals("") || tfuname.getText() == null
                    || tfpname.getText().equals("") || tfpname.getText() == null
                    || email.getText().equals("") || email.getText() == null
                    || tabody.getText().equals("") || tabody.getText() == null
                    || tasign1.getText().equals("") || tasign1.getText() == null
                    || tasign2.getText().equals("") || tasign2.getText() == null) {
                JOptionPane.showMessageDialog(null, "All fields are mandatory. Please provide required information!");
            } else {
                load = new Thread(this);
                load.start();
            }
        }

        if (ae.getSource() == close) {
            System.exit(1);
        }
    }

    public String[] getAttachments(String attachmentPath, String name) {
        Calendar cal = Calendar.getInstance();
        File dir = new File(attachmentPath);
        FileFilter fileFilter = new WildcardFileFilter(name + "*.pdf");
        File[] files = dir.listFiles(fileFilter);
        String[] attacFiles = new String[files.length];
        if (files.length == 0) {
            attacFiles = new String[1];
            attacFiles[0] = "";
        } else {
            for (int i = 0; i < files.length; i++) {
                attacFiles[i] = files[i].toString().trim();

            }
        }
        return attacFiles;
    }

    @Override
    public void run() {
        try {
            Common cmn = new Common();
            HashMap hm = cmn.getImportExcelData(getFilePath());
            try {

                ArrayList<BanyanAppBean> ls = (ArrayList) hm.get("list");
                int noRec = ls.size();
                pb.setMinimum(0);
                pb.setMaximum(getDataCheckedLen(getModel()));
                int flag = 0;
                log.clearLogs();
                lblstatus.setText("initializing...");
                for (int i = 0; i < noRec; i++) {
                    BanyanAppBean banApp = (BanyanAppBean) ls.get(i);
                    if (getModel().getValueAt(i, 0).toString().equals(banApp.getId())
                            && getModel().getValueAt(i, 3).toString().equals("true")) {
                        emailId = banApp.getEmail();
                        salutation = banApp.getSalutation();
                        String recipients[] = new String[1];
                        recipients[0] = emailId;
                        name = banApp.getName();
                        lblstatus.setText("Sending mail to " + name);
                        String sign = "<font face='Calibri' size='10px' color='#008000'><b>" + tasign1.getText() + "</b></font>" + "<br><font face='Calibri' size='10px'>Bangalore, India.<br>M : +91-" + banApp.getMobNo() + " </font><br>" + "<a href='www.banyantreeadvisors.com'><font face='Calibri' size='10px' color='#008000'>www.banyantreeadvisors.com</font></a>";
                        String[] attachmentFile = getAttachments(getAttchmentFolder(), name);
                        SendMailAPI mailApi = new SendMailAPI();
                        try {
                            mailApi.postMail(recipients,
                                    tfsub.getText(), tabody.getText(), tfuname.getText(), tfpname.getText(), attachmentFile, sign, banApp.getFieldManager(), salutation);
                            pb.setValue(i + 1);
                            lblstatus.setText("Mail sent to " + name);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(SendMailApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            data = "# [SENT] " + new Date() + "  Id-" + banApp.getId() + "    Name-" + name + "       Email-" + emailId;
                            try {
                                Thread.sleep(15);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(SendMailApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            log.appendToFile(data);

                        } catch (MessagingException messagingException) {
                            flag++;
                            log.errorLogger(messagingException);
                            lblstatus.setText("Mail sending failed to " + name);
                            data = "# [FAILED] " + new Date() + "  Id-" + banApp.getId() + "    Name-" + name + "       Email-" + emailId;
                            log.appendToFile(data);
                        }
                    }
                }
                if (flag > 0) {
                    JOptionPane.showMessageDialog(null, "Authentication failed. Please enter correct username and password.");
                }
                JOptionPane.showMessageDialog(null, "Processing completed.");
                lblstatus.setText("Sucessfully Sent mail to All Users");

                load.suspend();
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Processing completed.");
                log.errorLogger(ex);
            }
        } catch (IOException ex) {
            log.errorLogger(ex);
        }
    }

    public MyTableModel getModel() {
        return model;
    }

    public void setModel(MyTableModel model) {
        this.model = model;
    }

    public String getAttchmentFolder() {
        return attchmentFolder;
    }

    public void setAttchmentFolder(String attchmentFolder) {
        this.attchmentFolder = attchmentFolder;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDataCheckedLen(MyTableModel model) {
        int length = 0;
        int row = model.getRowCount();
        for (int j = 0; j < row; j++) {
            if (model.getValueAt(j, 3).toString().equals("true")) {
                length++;
            }
        }
        return length;
    }
}

class MyWindowAdapter extends WindowAdapter {

    SendMailApp sma = null;

    MyWindowAdapter(SendMailApp sma) {
        this.sma = sma;
    }

    public void windowClosing(WindowEvent e) {
        sma.setVisible(false);
        BanyanMails.frmShow();
    }

}
