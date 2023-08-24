package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import com.bilgeadam.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderMailConsumer {
    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "reminder-mail-queue")
    public void sendReminderMail(ReminderMailModel model){
        mailSenderService.sendTrainerAssessmentReminder(model);
    }
}