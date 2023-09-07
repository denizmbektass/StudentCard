package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.RegisterStudentAndTrainerModel;
import com.bilgeadam.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterStudentAndTrainerConsumer {
    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.registerStudentAndTrainerQueue}")
    public void registerStudentAndTrainer(RegisterStudentAndTrainerModel model){
        mailSenderService.registerStudentAndTrainer(model);
    }

}
