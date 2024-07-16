package com.example.myapplication.Sistem;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {
    private final String username;
    private final String password;

    public MailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String recipientEmail, String password) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Bienvenido a la Aplicación");
        message.setText("Tu contraseña es: " + password);

        Transport.send(message);
    }
}
