package com.bilgeadam.service;


import com.bilgeadam.rabbitmq.model.ActivationLinkMailModel;
import com.bilgeadam.rabbitmq.model.RegisterStudentAndTrainerModel;
import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import com.bilgeadam.rabbitmq.model.ResetPasswordModel;
import com.bilgeadam.utility.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    private final JwtTokenProvider jwtTokenProvider;

    public void sendNewPassword(ResetPasswordModel model) {
        preConfiguredMessage.setTo(model.getEmail());
        preConfiguredMessage.setSubject("Password");
        preConfiguredMessage.setText("Your password : " + model.getPassword());
        javaMailSender.send(preConfiguredMessage);
    }

    public void sendTrainerAssessmentReminder(ReminderMailModel model){
        preConfiguredMessage.setTo(model.getEmail());
        preConfiguredMessage.setSubject("Eğitmen Görüşü Hatırlatıcısı");
        preConfiguredMessage.setText(model.getStudentName() + " isimli öğrenciye " + model.getAralik() + " yapılmamıştır. Lütfen görüşünüzü yapınız.");
        javaMailSender.send(preConfiguredMessage);
    }

    public void activationLink(ActivationLinkMailModel model){
        String token = jwtTokenProvider.createTokenForActivationLink(model.getAuthId()).get();
        String linkActivateUserLink = "http://localhost:4040/api/v1/auth/activate-user/"+token;
        preConfiguredMessage.setTo(model.getEmail());
        preConfiguredMessage.setSubject("Activation Link");
        preConfiguredMessage.setText("Dear User, \n"
                + "If you want to activate your profile, please click the link at the below!"
                + "\n" + linkActivateUserLink);
        javaMailSender.send(preConfiguredMessage);
    }

    public void registerStudentAndTrainer(RegisterStudentAndTrainerModel model) {
        preConfiguredMessage.setTo(model.getEmail());
        preConfiguredMessage.setSubject("Password");
        preConfiguredMessage.setText("Your password : " + model.getPassword());
        javaMailSender.send(preConfiguredMessage);
    }

}
