package com.ZoomCart.OrderAndCart.Component;

import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender emailSender;

    public String sendSimpleMessage(
            String to, String subject, List<String> text) throws SendFailedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shashankhralt@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        StringBuilder builder = new StringBuilder();
        for (String line : text) {
            builder.append(line).append("\n\n"); // Append each line with a newline
        }
        message.setText(builder.toString());
        emailSender.send(message);
        return "Mail sent";
    }
}
