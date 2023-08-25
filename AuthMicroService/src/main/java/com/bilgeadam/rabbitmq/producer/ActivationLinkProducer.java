package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.ActivationLinkMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


    @Service
    @RequiredArgsConstructor
    public class ActivationLinkProducer {
        private final RabbitTemplate rabbitTemplate;
        private String authDirectExchange = "auth-direct-exchange";
        private String activationLinkBindingKey = "activation-link-binding-key";

        public void sendActivationLink(ActivationLinkMailModel model){
            rabbitTemplate.convertAndSend(authDirectExchange,activationLinkBindingKey,model);
        }
    }

