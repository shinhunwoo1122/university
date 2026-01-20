package com.example.university.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import java.util.UUID;

@Configuration
public class MqttInboundConfig {

    @Bean
    public MessageChannel mqttInboundChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(MqttPahoClientFactory factory){
        String clientId = "sub-" + UUID.randomUUID().toString();

        /**
         * 공유 구독 형식: $share/{그룹명}/{토픽}
         * 이렇게 하면 app1, app2, app3가 떠 있을 때 메시지가 Round-Robin 방식으로 한 곳에만 전달됩니다.
         */
        String sharedTopic = "$share/my-app-group/university/notice";

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, factory, sharedTopic);

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }


}
