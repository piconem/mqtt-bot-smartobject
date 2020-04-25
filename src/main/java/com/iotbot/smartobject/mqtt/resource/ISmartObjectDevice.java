package com.iotbot.smartobject.mqtt.resource;

import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.List;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public interface ISmartObjectDevice {

    public String getDeviceId();

    public void setMqttClient(IMqttClient client);

    public List<String> getResourceList();

    public void start();

    public void init();
}

