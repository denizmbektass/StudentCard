package com.bilgeadam.service;


import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import com.bilgeadam.rabbitmq.model.ResetPasswordModel;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendNewPassword(ResetPasswordModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${mailUsername}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Password");
        mailMessage.setText("Your password : " + model.getPassword());
        javaMailSender.send(mailMessage);
    }

    public void sendTrainerAssessmentReminder(ReminderMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${mailUsername}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Eğitmen Görüşü Hatırlatıcısı");
        mailMessage.setText(model.getStudentName() + " isimli öğrenciye " + model.getAralik() + " yapılmamıştır. Lütfen görüşünüzü yapınız.");
        javaMailSender.send(mailMessage);
    }
}
