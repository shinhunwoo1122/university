package com.example.university.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttMessageListener {

    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMessage(Message<?> message){
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = (String) message.getPayload();

        log.info("==========================================");
        log.info("메시지 수신 성공!");
        log.info("수신 토픽: {}", topic);
        log.info("메시지 내용: {}", payload);
        log.info("==========================================");
    }

}
