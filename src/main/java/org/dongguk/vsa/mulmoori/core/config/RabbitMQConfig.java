package org.dongguk.vsa.mulmoori.core.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.connection-port}")
    private Integer connectionPort;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.MULMOORI_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue dialogueQueue() {
        return new Queue(Constants.MULMOORI_DIALOGUE_QUEUE_NAME, true);
    }

    @Bean
    public Binding dialogueBinding(Queue dialogueQueue, TopicExchange exchange) {
        return BindingBuilder.bind(dialogueQueue).to(exchange).with(Constants.NAROOTEO_DIALOGUE_ROUTING_KEY);
    }

    @Bean
    public Binding userDialogueBinding(Queue dialogueQueue, TopicExchange exchange) {
        return BindingBuilder.bind(dialogueQueue).to(exchange).with(Constants.NAROOTEO_USER_DIALOGUE_ROUTING_KEY);
    }

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(connectionPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Module dateTimeModule() {
        return new JavaTimeModule();
    }
}
