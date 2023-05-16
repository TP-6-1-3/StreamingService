package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.template.Template;
import ru.vsu.csf.asashina.musicmanBack.template.TemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;
    private final MailProperties mailProperties;
    private final TemplateEngine templateEngine;

    public void sendTemplate(String toEmail, String subject, Template template, Object model) {
        sendMessage(toEmail, subject, templateEngine.compile(template, model));
    }

    private void sendMessage(String toEmail, String subject, String content) {
        sender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
        });
    }
}
