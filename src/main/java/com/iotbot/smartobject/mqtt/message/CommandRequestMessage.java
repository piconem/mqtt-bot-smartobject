package com.iotbot.smartobject.mqtt.message;

import java.util.Map;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class CommandRequestMessage {

    private String sourceId;
    private String commandId;
    private String type;
    private Map<String, Object> parameters;

    public CommandRequestMessage() {
    }

    public CommandRequestMessage(String sourceId, String commandId, String type, Map<String, Object> parameters) {
        this.sourceId = sourceId;
        this.commandId = commandId;
        this.type = type;
        this.parameters = parameters;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandRequestMessage{");
        sb.append("sourceId='").append(sourceId).append('\'');
        sb.append(", commandId='").append(commandId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
