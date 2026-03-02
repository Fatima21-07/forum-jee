package com.forum.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailUtil {
    
    public static void sendEmail(String to, String subject, String body) {
        new Thread(() -> {
            // Get SMTP configuration from environment variables
            final String host = System.getenv("SMTP_HOST") != null ? System.getenv("SMTP_HOST") : "smtp.gmail.com";
            final String port = System.getenv("SMTP_PORT") != null ? System.getenv("SMTP_PORT") : "587";
            final String username = System.getenv("SMTP_USER");
            final String password = System.getenv("SMTP_PASS");

            if (username == null || password == null) {
                System.err.println("SMTP_USER or SMTP_PASS not set! Email simulation only.");
                System.out.println("To: " + to + "\nSubject: " + subject + "\nBody: " + body);
                return;
            }

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Email sent successfully to " + to);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
