package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    private String authDirectExchange = "auth-direct-exchange";
    private String resetPasswordBindingKey = "reset-password-binding-key";
    private String resetPasswordQueue = "reset-password-queue";

    @Bean
    DirectExchange authDirectExchange(){
        return new DirectExchange(authDirectExchange);
    }
    @Bean
    Queue resetPasswordQueue(){
        return new Queue(resetPasswordQueue);
    }
    @Bean
    public Binding resetPasswordBindingKey(final Queue resetPasswordQueue, final DirectExchange authDirectExchange){
        return BindingBuilder.bind(resetPasswordQueue).to(authDirectExchange).with(resetPasswordBindingKey);
    }

    @Value("${rabbitmq.queueActivationLink}")
    private String activationLinkQueue;
    @Value("${rabbitmq.activationLinkBindingKey}")
    private String activationLinkBindingKey;

    @Bean
    Queue activationLinkQueue() {
        return new Queue(activationLinkQueue);
    }
    @Bean
    public Binding activationLinkBindingKey(final Queue activationLinkQueue, final DirectExchange authDirectExchange){
        return BindingBuilder.bind(activationLinkQueue).to(authDirectExchange).with(activationLinkBindingKey);
    }

    @Value("${rabbitmq.registerStudentAndTrainerBindingKey}")
    private String registerStudentAndTrainerBindingKey;
    @Value("${rabbitmq.registerStudentAndTrainerQueue}")
    private String registerStudentAndTrainerQueue;

    @Bean
    Queue registerStudentAndTrainerQueue(){return  new Queue(registerStudentAndTrainerQueue);}

    @Bean
    public Binding registerStudentAndTrainerBindingKey(final Queue registerStudentAndTrainerQueue, final DirectExchange authDirectExchange){
        return BindingBuilder.bind(registerStudentAndTrainerQueue).to(authDirectExchange).with(registerStudentAndTrainerBindingKey);
    }

}
