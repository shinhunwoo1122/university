package com.example.university.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

@Configuration
public class MqttPublisherConfig {

    @Bean
    public MessageChannel mqttOutboundChannel(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel= "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory factory){
        //클라이언트 ID 중복 방지를 위해 UUID 사용
        String clientId = "pub-" + UUID.randomUUID().toString();
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, factory);
        messageHandler.setAsync(true);
        return messageHandler;
    }

}
