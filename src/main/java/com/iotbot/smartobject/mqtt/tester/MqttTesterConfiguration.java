package com.iotbot.smartobject.mqtt.tester;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttTesterConfiguration {

    private String mqttBrokerAddress;

    private int mqttBrokerPort;


    public MqttTesterConfiguration() {
    }

    public String getMqttBrokerAddress() {
        return mqttBrokerAddress;
    }

    public void setMqttBrokerAddress(String mqttBrokerAddress) {
        this.mqttBrokerAddress = mqttBrokerAddress;
    }

    public int getMqttBrokerPort() {
        return mqttBrokerPort;
    }

    public void setMqttBrokerPort(int mqttBrokerPort) {
        this.mqttBrokerPort = mqttBrokerPort;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MqttTesterConfiguration{");
        sb.append("mqttBrokerAddress='").append(mqttBrokerAddress).append('\'');
        sb.append(", mqttBrokerPort=").append(mqttBrokerPort);
        sb.append('}');
        return sb.toString();
    }
}
