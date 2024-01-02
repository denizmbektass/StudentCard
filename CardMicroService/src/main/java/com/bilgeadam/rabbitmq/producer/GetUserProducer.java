package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserProducer {
    private String cardDirectExchange = "card-direct-exchange";

    @Value("${rabbitmq.getUserBindingKey}")
    private String getUserBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public Object GetUser(GetUserModel getUserModel){
        return rabbitTemplate.convertSendAndReceive(cardDirectExchange, getUserBindingKey, getUserModel);
    }
}
