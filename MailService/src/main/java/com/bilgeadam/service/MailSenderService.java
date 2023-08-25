package com.bilgeadam.service;


import com.bilgeadam.rabbitmq.model.ActivationLinkMailModel;
import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import com.bilgeadam.rabbitmq.model.ResetPasswordModel;
import com.bilgeadam.utility.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    private final JwtTokenProvider jwtTokenProvider;

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
        mailMessage.setText(model.getGroupName() + " grubundaki "+ " öğrencilere görüş bildirimi yapılmamıştır. Lütfen görüşünüzü yapınız.");
        javaMailSender.send(mailMessage);
    }

    public void activationLink(ActivationLinkMailModel model){
        String token = jwtTokenProvider.createTokenForActivationLink(model.getAuthId()).get();
        String linkActivateUserLink = "http://localhost:4040/api/v1/auth/activate-user/"+token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Activation Link");
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setText("Dear User, \n"
                + "If you want to activate your profile, please click the link at the below!"
                + "\n" + linkActivateUserLink);
        javaMailSender.send(mailMessage);
    }

}
