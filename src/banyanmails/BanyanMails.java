package banyanmails;

import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class BanyanMails extends WindowAdapter {

    public static void main(String[] args) {
        frmShow();
    }

    public static JFrame frmShow() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        }
        BanyanMailApp frm1 = new BanyanMailApp("Banyan Mail Application");
        frm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm1.setIconImage(new javax.swing.ImageIcon("mail.png").getImage());
        frm1.setResizable(false);
        frm1.setLocation(350, 200);
        frm1.setSize(600, 280);
        frm1.setVisible(true);
        frm1.setButtonDisbled();
        return frm1;
    }

    public static JFrame frmShow(SelectClients sc) {

        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        }
        BanyanMailApp frm1 = new BanyanMailApp("Advance Tax Application- Banyan Tree Advisors");
        frm1.setIconImage(new javax.swing.ImageIcon("mail.png").getImage());
        frm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm1.setResizable(false);
        frm1.setLocation(350, 200);
        frm1.setSize(600, 280);
        frm1.setVisible(true);
        frm1.setButtonDisbled();
        frm1.setData(sc.getFilePathName(), sc.getAttachFolder());
        return frm1;
    }
}
