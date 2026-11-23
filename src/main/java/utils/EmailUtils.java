package utils;
import utils.Log;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailUtils {

    public static void sendEmail(String reportPath) {

        // Option 1: Using a config.properties File (Best for Local QA Frameworks)
//        Properties config = loadConfig();
//        final String senderEmailID = config.getProperty("email.sender");
//        final String appPassword = config.getProperty("email.password");
//        final String recipientEmailID = config.getProperty("email.recipient");

        // Option 2: Using Environment Variables (Best for CI/CD Pipelines)
        final String senderEmailID = System.getenv("QA_EMAIL_SENDER");
        final String appPassword = System.getenv("QA_EMAIL_PASSWORD");
        final String recipientEmailID = System.getenv("QA_EMAIL_RECIPIENT");

        if (senderEmailID == null || appPassword == null || recipientEmailID == null) {
            throw new IllegalStateException("Email credentials are missing from environment variables.");
        }

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
        //SMTP connection is working fine, so set value to false
        session.setDebug(false);

        //Compose Email & Send
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmailID));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmailID));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(recipientEmailID));
            message.setSubject("Test Report for QA Practice Site Login Test(s)");

            //Email body part
            MimeMultipart multipart = getMimeMultipart(reportPath);
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

    private static @NonNull MimeMultipart getMimeMultipart(String reportPath) throws MessagingException, IOException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hello,\nHere is the test report from automation test run.\n\nBest,\nQA Team");

        //Attachment part
        MimeBodyPart attachmentReport = new MimeBodyPart();
        attachmentReport.attachFile(new File(reportPath));

        //Combine body and attachment parts
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentReport);
        return multipart;
    }

//    private static Properties loadConfig() {
//        Properties config = new Properties();
//        File configFile = new File("src/main/resources/config.properties");
//
//        if (!configFile.exists()) {
//            throw new RuntimeException("File does not exist at: " + configFile.getAbsolutePath());
//        }
//
//        try (InputStream input = new FileInputStream(configFile)) {
//            config.load(input);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read config.properties", e);
//        }
//        return config;
//    }
}
