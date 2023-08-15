package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderMailProducer {
    private final RabbitTemplate rabbitTemplate;
    private String cardDirectExchange = "card-direct-exchange";
    private String reminderMailBindingKey = "reminder-mail-binding-key";

    public void sendReminderMail(ReminderMailModel model){
        rabbitTemplate.convertAndSend(cardDirectExchange,reminderMailBindingKey,model);
    }
}
