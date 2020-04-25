package com.iotbot.smartobject.mqtt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttTopics {

    private static final Logger logger = LoggerFactory.getLogger(MqttTopics.class);

    public static final String DEVICE_TELEMETRY_TOPIC = "telemetry/{{device_id}}";
    public static final String DEVICE_TELEMETRY_RESOURCE_TOPIC = "telemetry/{{device_id}}/resource/{{resource_id}}";
    public static final String DEVICE_EVENT_TOPIC = "events/{{device_id}}";
    public static final String DEVICE_COMMAND_REQUEST_TOPIC = "commands/{{device_id}}/request";
    public static final String DEVICE_COMMAND_RESPONSE_TOPIC = "commands/{{device_id}}/response";

    public static String getResourceTelemetryTopic(String deviceId, String resourceId){
        try {
            return TopicTemplateManager.getTopicForDeviceResource(DEVICE_TELEMETRY_RESOURCE_TOPIC, deviceId, resourceId);
        } catch (IOException e) {
            logger.error("Error composing topic ! Exception: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String getDeviceTelemetryTopic(String deviceId){
        try {
            return TopicTemplateManager.getTopicForDevice(DEVICE_TELEMETRY_TOPIC, deviceId);
        } catch (IOException e) {
            logger.error("Error composing topic ! Exception: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String getDeviceEventTopic(String deviceId){
        try {
            return TopicTemplateManager.getTopicForDevice(DEVICE_EVENT_TOPIC, deviceId);
        } catch (IOException e) {
            logger.error("Error composing topic ! Exception: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String getDeviceCommandRequestTopic(String deviceId){
        try {
            return TopicTemplateManager.getTopicForDevice(DEVICE_COMMAND_REQUEST_TOPIC, deviceId);
        } catch (IOException e) {
            logger.error("Error composing topic ! Exception: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String getDeviceCommandResponseTopic(String deviceId){
        try {
            return TopicTemplateManager.getTopicForDevice(DEVICE_COMMAND_RESPONSE_TOPIC, deviceId);
        } catch (IOException e) {
            logger.error("Error composing topic ! Exception: {}", e.getLocalizedMessage());
            return null;
        }
    }

}
