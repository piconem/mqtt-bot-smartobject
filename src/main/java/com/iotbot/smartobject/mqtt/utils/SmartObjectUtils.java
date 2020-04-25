package com.iotbot.smartobject.mqtt.utils;

import java.util.UUID;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class SmartObjectUtils {

    public static String getRandomDeviceId(String deviceNamespace, String deviceType){
        return buildDeviceId(deviceNamespace, deviceType, UUID.randomUUID().toString());
    }

    public static String getRandomResourceId(String resourceType){
        return String.format("%s:%s", resourceType, UUID.randomUUID().toString());
    }

    public static String buildDeviceId(String deviceNamespace, String deviceType, String deviceId){
        return String.format("%s:%s:%s", deviceNamespace, deviceType, deviceId);
    }

}
