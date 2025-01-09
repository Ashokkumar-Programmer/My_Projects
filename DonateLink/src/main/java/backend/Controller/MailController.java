package backend.Controller;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailController {

	public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
	
    String mailsent(String mail) {
    	String otp = generateOTP();
        String recipient = mail; 
        String sender = "charitylinkmail@gmail.com"; 
        String host = "smtp.gmail.com";
        int port = 587;
        final String username = "charitylinkmail@gmail.com";
        final String password = "aegt ptyn fash zoce";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("This is Subject");
            
            String text = "<!DOCTYPE html>\r\n"
            		+ "<html lang=\"en\">\r\n"
            		+ "<head>\r\n"
            		+ "    <meta charset=\"UTF-8\">\r\n"
            		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
            		+ "    <style>\r\n"
            		+ "        body {\r\n"
            		+ "            font-family: Arial, sans-serif;\r\n"
            		+ "            margin: 0;\r\n"
            		+ "            padding: 0;\r\n"
            		+ "            background-color: #f4f4f9;\r\n"
            		+ "        }\r\n"
            		+ "        .email-container {\r\n"
            		+ "            width: 100%;\r\n"
            		+ "            max-width: 600px;\r\n"
            		+ "            margin: 20px auto;\r\n"
            		+ "            background-color: #ffffff;\r\n"
            		+ "            border-radius: 8px;\r\n"
            		+ "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);\r\n"
            		+ "            overflow: hidden;\r\n"
            		+ "        }\r\n"
            		+ "        .header {\r\n"
            		+ "            background-color: #007bff;\r\n"
            		+ "            color: #ffffff;\r\n"
            		+ "            text-align: center;\r\n"
            		+ "            padding: 20px 10px;\r\n"
            		+ "        }\r\n"
            		+ "        .header img {\r\n"
            		+ "            max-width: 120px;\r\n"
            		+ "            margin-bottom: 10px;\r\n"
            		+ "        }\r\n"
            		+ "        .content {\r\n"
            		+ "            padding: 20px;\r\n"
            		+ "            text-align: center;\r\n"
            		+ "        }\r\n"
            		+ "        .otp {\r\n"
            		+ "            font-size: 24px;\r\n"
            		+ "            font-weight: bold;\r\n"
            		+ "            color: #007bff;\r\n"
            		+ "            margin: 20px 0;\r\n"
            		+ "        }\r\n"
            		+ "        .footer {\r\n"
            		+ "            background-color: #f4f4f9;\r\n"
            		+ "            color: #777;\r\n"
            		+ "            text-align: center;\r\n"
            		+ "            padding: 10px;\r\n"
            		+ "            font-size: 12px;\r\n"
            		+ "        }\r\n"
            		+ "        .footer a {\r\n"
            		+ "            color: #007bff;\r\n"
            		+ "            text-decoration: none;\r\n"
            		+ "        }\r\n"
            		+ "    </style>\r\n"
            		+ "</head>\r\n"
            		+ "<body>\r\n"
            		+ "    <div class=\"email-container\">\r\n"
            		+ "        <div class=\"header\">\r\n"
            		+ "            <img src=\"https://i.ibb.co/8DzwZZh/website-logo-removebg-preview.png\" alt=\"website-logo-removebg-preview\" border=\"0\">\r\n"
            		+ "            <h1>CharityLink</h1>\r\n"
            		+ "        </div>\r\n"
            		+ "        <div class=\"content\">\r\n"
            		+ "            <p>Hello,</p>\r\n"
            		+ "            <p>Your One-Time Password (OTP) for verification is:</p>\r\n"
            		+ "            <div class=\"otp\">"+otp+"</div>\r\n"
            		+ "            <p>If you did not request this, please ignore this email.</p>\r\n"
            		+ "        </div>\r\n"
            		+ "        <div class=\"footer\">\r\n"
            		+ "            <p>Thank you for using <strong>CharityLink</strong>.</p>\r\n"
            		+ "        </div>\r\n"
            		+ "    </div>\r\n"
            		+ "</body>\r\n"
            		+ "</html>\r\n"
            		+ "";
            message.setContent(text, "text/html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return otp;
    }
}