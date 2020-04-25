package com.iotbot.smartobject.mqtt.message;

import java.util.Map;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class CommandResponseMessage {

    private String commandId;
    private boolean isError;
    private String errorMessage;
    private Map<String, Object> responseAttributes;

    public CommandResponseMessage() {
    }

    public CommandResponseMessage(String commandId, boolean isError, String errorMessage, Map<String, Object> responseAttributes) {
        this.commandId = commandId;
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.responseAttributes = responseAttributes;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(Map<String, Object> responseAttributes) {
        this.responseAttributes = responseAttributes;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandResponseMessage{");
        sb.append("commandId='").append(commandId).append('\'');
        sb.append(", isError=").append(isError);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", responseAttributes=").append(responseAttributes);
        sb.append('}');
        return sb.toString();
    }
}
