package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.RegisterStudentAndTrainerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterStudentAndTrainerProducer {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.registerStudentAndTrainerBindingKey}")
    private String registerStudentAndTrainerBindingKey ;
    @Value("${rabbitmq.authDirectExchange}")
    private String authDirectExchange;
    public void registerStudentAndTrainer(RegisterStudentAndTrainerModel model){
        rabbitTemplate.convertAndSend(authDirectExchange,registerStudentAndTrainerBindingKey,model);
    }
}
