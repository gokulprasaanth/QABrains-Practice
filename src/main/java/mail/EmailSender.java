package mail;

import utils.Log;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) { //Will be executed separately outside TestNG

        final String senderEmailID = "qualiashtester@gmail.com";
        final String appPassword = "etxeqjggynixvkon";
        final String recipientEmailID = "qualiashtester@gmail.com";

        //Set SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailID, appPassword);
            }
        });
        session.setDebug(true);

        //Compose Email & Send
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmailID));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmailID));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(recipientEmailID));
            message.setSubject("Test Report for QA Practice Site Login Test(s)");
            //message.setText("Hello,\nHere is the test report from automation test run.\n\nBest,\nQA Team");

            //Email body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hello,\nHere is the test report from automation test run.\n\nBest,\nQA Team");

            //Attachment part
            MimeBodyPart attachmentReport = new MimeBodyPart();
            String filePath =  System.getProperty("user.dir") + "/reports/ExtentReport_20261030_163427.html";
            attachmentReport.attachFile(new File(filePath));

            //Combine body and attachment parts
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentReport);
            message.setContent(multipart);

            //Send
            Transport.send(message);
            System.out.println("Sent email successfully...");
            Log.info("Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add attachment (test report)

    }

}
