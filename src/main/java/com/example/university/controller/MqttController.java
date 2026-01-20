package com.example.university.controller;

import com.example.university.mqtt.MqttGateWay;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MqttController {

    private final MqttGateWay mqttGateWay;

    @GetMapping("/mqtt/send")
    public String sendMqttMessage(@RequestParam("topic") String topic,
                                  @RequestParam("message") String message){

        // gateway를 통해 MQTT 브로커로 메세지 전송
        mqttGateWay.sendToMqtt(message, topic);

        return "MQTT 메시지 전송 성공! <br> 토픽: " + topic + "<br> 메시지: " + message;
    }
}
