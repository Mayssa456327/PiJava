package org.example.models;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    public static void sendEmailWithAttachment(String recipientEmail, String subject, String body, String attachmentFilePath) {
        // Configurez les propriétés du serveur SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Remplacez smtp.example.com par le serveur SMTP de votre fournisseur de messagerie
        props.put("mail.smtp.port", "587"); // Utilisez le port SMTP de votre fournisseur de messagerie

        // Configurez l'authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Akrimihachem1@gmail.com", "getl qyha lfsj omfp"); // Remplacez your-email@example.com et your-password par votre adresse e-mail et votre mot de passe
            }
        });

        try {
            // Créez un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("directdoc31@gmail.com")); // Remplacez your-email@example.com par votre adresse e-mail
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Créez le contenu du message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Créez la pièce jointe
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentFilePath);

            // Ajoutez la pièce jointe au message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Envoyez le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
