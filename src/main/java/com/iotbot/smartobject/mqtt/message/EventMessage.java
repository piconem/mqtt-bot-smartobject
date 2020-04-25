package com.iotbot.smartobject.mqtt.message;

import java.util.Map;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class EventMessage {

    private String type;
    private long timestamp;
    private Map<String, Object> metadata;

    public EventMessage() {
    }

    public EventMessage(String type, long timestamp, Map<String, Object> metadata) {
        this.type = type;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventMessage{");
        sb.append("type='").append(type).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", metadata=").append(metadata);
        sb.append('}');
        return sb.toString();
    }
}
