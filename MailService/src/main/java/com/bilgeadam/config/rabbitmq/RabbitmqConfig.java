package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    private String resetPasswordQueue = "reset-password-queue";
    private String reminderMailQueue = "reminder-mail-queue";

    @Bean
    Queue resetPasswordQueue(){
        return new Queue(resetPasswordQueue);
    }

    @Bean
    Queue reminderMailQueue() {
        return new Queue(reminderMailQueue);
    }
}