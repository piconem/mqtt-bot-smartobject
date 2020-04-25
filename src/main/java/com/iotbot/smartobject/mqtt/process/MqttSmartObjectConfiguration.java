package com.iotbot.smartobject.mqtt.process;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttSmartObjectConfiguration {

    private String mqttBrokerAddress;

    private int mqttBrokerPort;

    private String deviceNameSpace;

    private int dummyResourcesNumber;

    private int dummyEventUpdateTimeMs;

    private int dummyTelemetryUpdateTimeMs;

    private int dummyRandomPayloadByteSize;

    private int dummyTotalTelemetryMessageLimit = 10000;

    private boolean dummyDeviceTelemetryActive = true;

    private boolean dummyResourceTelemetryActive = true;

    private int dummyDeviceStartDelayMs = 30000;

    private int mqttOutgoingClientQoS = 0;

    private boolean mqttOutgoingClientRetainedMessages = false;

    public MqttSmartObjectConfiguration() {
    }

    public int getDummyEventUpdateTimeMs() {
        return dummyEventUpdateTimeMs;
    }

    public void setDummyEventUpdateTimeMs(int dummyEventUpdateTimeMs) {
        this.dummyEventUpdateTimeMs = dummyEventUpdateTimeMs;
    }

    public int getDummyTelemetryUpdateTimeMs() {
        return dummyTelemetryUpdateTimeMs;
    }

    public void setDummyTelemetryUpdateTimeMs(int dummyTelemetryUpdateTimeMs) {
        this.dummyTelemetryUpdateTimeMs = dummyTelemetryUpdateTimeMs;
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

    public String getDeviceNameSpace() {
        return deviceNameSpace;
    }

    public void setDeviceNameSpace(String deviceNameSpace) {
        this.deviceNameSpace = deviceNameSpace;
    }

    public int getDummyResourcesNumber() {
        return dummyResourcesNumber;
    }

    public void setDummyResourcesNumber(int dummyResourcesNumber) {
        this.dummyResourcesNumber = dummyResourcesNumber;
    }

    public int getDummyRandomPayloadByteSize() {
        return dummyRandomPayloadByteSize;
    }

    public void setDummyRandomPayloadByteSize(int dummyRandomPayloadByteSize) {
        this.dummyRandomPayloadByteSize = dummyRandomPayloadByteSize;
    }

    public int getDummyTotalTelemetryMessageLimit() {
        return dummyTotalTelemetryMessageLimit;
    }

    public void setDummyTotalTelemetryMessageLimit(int dummyTotalTelemetryMessageLimit) {
        this.dummyTotalTelemetryMessageLimit = dummyTotalTelemetryMessageLimit;
    }

    public boolean getDummyDeviceTelemetryActive() {
        return dummyDeviceTelemetryActive;
    }

    public void setDummyDeviceTelemetryActive(boolean dummyDeviceTelemetryActive) {
        this.dummyDeviceTelemetryActive = dummyDeviceTelemetryActive;
    }

    public boolean getDummyResourceTelemetryActive() {
        return dummyResourceTelemetryActive;
    }

    public void setDummyResourceTelemetryActive(boolean dummyResourceTelemetryActive) {
        this.dummyResourceTelemetryActive = dummyResourceTelemetryActive;
    }

    public int getDummyDeviceStartDelayMs() {
        return dummyDeviceStartDelayMs;
    }

    public void setDummyDeviceStartDelayMs(int dummyDeviceStartDelayMs) {
        this.dummyDeviceStartDelayMs = dummyDeviceStartDelayMs;
    }

    public int getMqttOutgoingClientQoS() {
        return mqttOutgoingClientQoS;
    }

    public void setMqttOutgoingClientQoS(int mqttOutgoingClientQoS) {
        this.mqttOutgoingClientQoS = mqttOutgoingClientQoS;
    }

    public boolean getMqttOutgoingClientRetainedMessages() {
        return mqttOutgoingClientRetainedMessages;
    }

    public void setMqttOutgoingClientRetainedMessages(boolean mqttOutgoingClientRetainedMessages) {
        this.mqttOutgoingClientRetainedMessages = mqttOutgoingClientRetainedMessages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MqttSmartObjectConfiguration{");
        sb.append("mqttBrokerAddress='").append(mqttBrokerAddress).append('\'');
        sb.append(", mqttBrokerPort=").append(mqttBrokerPort);
        sb.append(", deviceNameSpace='").append(deviceNameSpace).append('\'');
        sb.append(", dummyResourcesNumber=").append(dummyResourcesNumber);
        sb.append(", dummyEventUpdateTimeMs=").append(dummyEventUpdateTimeMs);
        sb.append(", dummyTelemetryUpdateTimeMs=").append(dummyTelemetryUpdateTimeMs);
        sb.append(", dummyRandomPayloadByteSize=").append(dummyRandomPayloadByteSize);
        sb.append(", dummyTotalTelemetryMessageLimit=").append(dummyTotalTelemetryMessageLimit);
        sb.append(", dummyDeviceTelemetryActive=").append(dummyDeviceTelemetryActive);
        sb.append(", dummyResourceTelemetryActive=").append(dummyResourceTelemetryActive);
        sb.append(", dummyDeviceStartDelayMs=").append(dummyDeviceStartDelayMs);
        sb.append(", mqttOutgoingClientQoS=").append(mqttOutgoingClientQoS);
        sb.append(", mqttOutgoingClientRetainedMessages=").append(mqttOutgoingClientRetainedMessages);
        sb.append('}');
        return sb.toString();
    }
}
