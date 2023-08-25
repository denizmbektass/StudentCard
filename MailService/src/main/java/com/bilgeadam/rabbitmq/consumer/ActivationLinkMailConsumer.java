package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.ActivationLinkMailModel;
import com.bilgeadam.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationLinkMailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = ("${rabbitmq.queueActivationLink}"))
    public void sendMailActivationLink(ActivationLinkMailModel model){
        mailSenderService.activationLink(model);
    }
}
