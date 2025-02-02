package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.getStudentQueue}")
    private String getStudentQueue;

    @Bean
    Queue getStudentQueue(){return  new Queue(getStudentQueue);}

}