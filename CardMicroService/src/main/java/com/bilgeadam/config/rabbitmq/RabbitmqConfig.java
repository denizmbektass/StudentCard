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
    private String cardDirectExchange = "card-direct-exchange";
    private String reminderMailBindingKey = "reminder-mail-binding-key";
    private String reminderMailQueue = "reminder-mail-queue";

    @Bean
    DirectExchange cardDirectExchange() {
        return new DirectExchange(cardDirectExchange);
    }

    @Bean
    Queue reminderMailQueue() {
        return new Queue(reminderMailQueue);
    }

    @Bean
    public Binding reminderMailBindingKey(final Queue reminderMailQueue, final DirectExchange cardDirectExchange) {
        return BindingBuilder.bind(reminderMailQueue).to(cardDirectExchange).with(reminderMailBindingKey);
    }

    //Student tan bilgi çekmek için oluşturulan kuyruk
    @Value("${rabbitmq.getStudentBindingKey}")
    private String getStudentBindingKey;
    @Value("${rabbitmq.getStudentQueue}")
    private String getStudentQueue;

    @Bean
    Queue getStudentQueue() {
        return new Queue(getStudentQueue);
    }

    @Bean
    public Binding getStudentBindingKey(final Queue getStudentQueue, final DirectExchange cardDirectExchange) {
        return BindingBuilder.bind(getStudentQueue).to(cardDirectExchange).with(getStudentBindingKey);
    }
}