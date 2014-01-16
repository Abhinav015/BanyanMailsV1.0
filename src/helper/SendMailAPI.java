package helper;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailAPI {

    public SendMailAPI() {
    }
    private String HOST_NAME = "smtp.mail.yahoo.com";
    String messageBody;

    public void postMail(String recipients[], String subject, String message,
            String from, String emailPassword, String[] files, String sign, String manager, String salutation) throws MessagingException {

        boolean debug = false;
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator authenticator = new SMTPAuthenticator(from, emailPassword);
        Session session = Session.getDefaultInstance(props, authenticator);

        session.setDebug(debug);

        Message msg = new MimeMessage(session);

        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Setting the Subject and Content Type
        message = "<font face='Calibri' size='10px'>" + salutation + ", <br><br>" + message + "<br><br>Regards<br>" + manager + "<br><br></font>";
        String details = sign;
        message = message + details;
        msg.setSubject(subject);
        msg.setContent(message, "text/html");

        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(message, "text/html");
        multipart.addBodyPart(htmlPart);

        addAtachments(files, multipart);
        //Put all message parts in the message
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("Sucessfully Sent mail to All Users");

    }

    protected void addAtachments(String[] attachments, Multipart multipart)
            throws MessagingException, AddressException {
        try {
            if (!attachments[0].equals("")) {
                for (int i = 0; i < attachments.length; i++) {
                    String filename = attachments[i];
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(filename);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
                    attachmentBodyPart.setFileName(filename);
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception raised while attaching file: No Attachments Found");
            ex.printStackTrace();
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        String username;
        String password;

        private SMTPAuthenticator(String authenticationUser, String authenticationPassword) {
            username = authenticationUser;
            password = authenticationPassword;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(username, password);
        }
    }
}
