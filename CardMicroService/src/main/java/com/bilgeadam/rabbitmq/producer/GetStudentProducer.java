package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetStudentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetStudentProducer {
    private String cardDirectExchange = "card-direct-exchange";

    @Value("${rabbitmq.getStudentBindingKey}")
    private String getStudentBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public Object GetStudent(GetStudentModel getStudentModel) {
        return rabbitTemplate.convertSendAndReceive(cardDirectExchange, getStudentBindingKey, getStudentModel);
    }
}
