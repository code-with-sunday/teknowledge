package dev.teknowledge.service.serviceImpl;

import dev.teknowledge.DTO.request.EmailDTO;
import dev.teknowledge.model.EmailNotification;
import dev.teknowledge.repository.EmailNotificationRepository;
import dev.teknowledge.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final EmailNotificationRepository emailNotificationRepository;

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sendMail;

    @Override
    public void sendEmailAlert(EmailDTO mailDTO) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(sendMail);
            simpleMailMessage.setTo(mailDTO.getTo());
            simpleMailMessage.setSubject(mailDTO.getSubject());
            simpleMailMessage.setText(mailDTO.getMessage());

            EmailNotification emailNotification = new EmailNotification();
            emailNotification.setSubject(mailDTO.getSubject());
            emailNotification.setTo(mailDTO.getTo());
            emailNotification.setMessage(mailDTO.getMessage());
            log.info("Email notification saved: {}", emailNotification);

            emailNotificationRepository.save(emailNotification);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent successfully", simpleMailMessage.getTo());
        } catch (MailException e) {
            throw new RuntimeException(e);

        }
    }
}
