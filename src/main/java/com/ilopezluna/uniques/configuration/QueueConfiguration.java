package com.ilopezluna.uniques.configuration;

import com.ilopezluna.uniques.receiver.HitReceiver;
import com.ilopezluna.uniques.service.UniquesService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by ignasi on 13/6/15.
 */
@Configuration
public class QueueConfiguration {

    public final static String queueName = "uniques";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("uniques-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    HitReceiver receiver(UniquesService uniquesService) {
        return new HitReceiver(uniquesService);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(HitReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
