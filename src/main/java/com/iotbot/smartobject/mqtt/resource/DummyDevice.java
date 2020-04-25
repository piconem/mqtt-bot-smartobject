package com.iotbot.smartobject.mqtt.resource;

import com.google.gson.Gson;
import com.iotbot.smartobject.mqtt.exception.MqttSmartObjectConfigurationException;
import com.iotbot.smartobject.mqtt.message.EventMessage;
import com.iotbot.smartobject.mqtt.process.MqttSmartObjectConfiguration;
import com.iotbot.smartobject.mqtt.utils.MqttTopics;
import com.iotbot.smartobject.mqtt.utils.SenML;
import com.iotbot.smartobject.mqtt.utils.SmartObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class DummyDevice implements Callable<Void>, ISmartObjectDevice {

    private static final Logger logger = LoggerFactory.getLogger(DummyDevice.class);

    private static final String DUMMY_EVENT_TYPE = "DUMMY_EVENT_TYPE";

    public static final String DEVICE_TYPE = "dummy_device";

    public static final String DUMMY_RESOURCE_TYPE = "dummy_sensor";

    public static final double DEVICE_VERSION = 0.1;

    private static final String DUMMY_RESOURCE_UNIT = "RAND";

    private int dummyDeviceStartDelayMs = 10000;

    private boolean isDummyDeviceTelemetryActive;

    private boolean isDummyResourceTelemetryActive;

    private int dummyTotalTelemetryMessageLimit = 0;

    private MqttSmartObjectConfiguration mqttSmartObjectConfiguration = null;

    private int randomPayloadByteSize = 0;

    private IMqttClient mqttClient;

    private String deviceId;

    private Gson gson;

    private Random rnd;

    private Semaphore semaphore;

    private int resourceNumber = 1;

    private Map<String, SenML> resourceMap;

    private int eventUpdateTimeMs = 60000;

    private int telemetryUpdateTimeMs = 10000;

    private int publishedTelemetryDataCount = 0;

    private int mqttOutgoingClientQoS = 0;

    private boolean mqttOutgoingClientRetainedMessages = false;

    private DummyDevice(){}

    public DummyDevice(MqttSmartObjectConfiguration mqttSmartObjectConfiguration) throws MqttSmartObjectConfigurationException {

        if(mqttSmartObjectConfiguration == null)
            throw new MqttSmartObjectConfigurationException("Provided Configuration Object = null !");

        this.deviceId = SmartObjectUtils.getRandomDeviceId(mqttSmartObjectConfiguration.getDeviceNameSpace(), DummyDevice.DEVICE_TYPE);
        this.mqttSmartObjectConfiguration = mqttSmartObjectConfiguration;
        this.resourceNumber = this.mqttSmartObjectConfiguration.getDummyResourcesNumber();
        this.randomPayloadByteSize = this.mqttSmartObjectConfiguration.getDummyRandomPayloadByteSize();
        this.eventUpdateTimeMs = this.mqttSmartObjectConfiguration.getDummyEventUpdateTimeMs();
        this.telemetryUpdateTimeMs = this.mqttSmartObjectConfiguration.getDummyTelemetryUpdateTimeMs();
        this.dummyTotalTelemetryMessageLimit = this.mqttSmartObjectConfiguration.getDummyTotalTelemetryMessageLimit();
        this.isDummyDeviceTelemetryActive = this.mqttSmartObjectConfiguration.getDummyDeviceTelemetryActive();
        this.isDummyResourceTelemetryActive = this.mqttSmartObjectConfiguration.getDummyResourceTelemetryActive();
        this.dummyDeviceStartDelayMs = this.mqttSmartObjectConfiguration.getDummyDeviceStartDelayMs();
        this.mqttOutgoingClientQoS = this.mqttSmartObjectConfiguration.getMqttOutgoingClientQoS();
        this.mqttOutgoingClientRetainedMessages = this.mqttSmartObjectConfiguration.getMqttOutgoingClientRetainedMessages();
    }

    @Override
    public void init(){

        this.rnd = new Random(System.currentTimeMillis());
        this.gson = new Gson();

        semaphore = new Semaphore(0);

        this.resourceMap = new HashMap<>();

        //Init n Dummy Resources for the Device
        for(int i = 0; i < this.resourceNumber; i++)
            this.resourceMap.put(String.format("%s-%d", SmartObjectUtils.getRandomResourceId(DUMMY_RESOURCE_TYPE), i), new SenML());

        logger.debug("Device with id {} and {} resources correctly created !", this.deviceId, this.resourceMap.entrySet().size());
    }

    @Override
    public List<String> getResourceList(){
        return new ArrayList<String>(this.resourceMap.keySet());
    }

    private class TelemetryTask extends TimerTask {
        @Override
        public void run() {
            try {
                    generateResourcesRandomValues();

                    //Publish both data to the device telemetry topic
                    if(isDummyDeviceTelemetryActive && !isTelemetryLimitReached()) {
                        publishData(MqttTopics.getDeviceTelemetryTopic(deviceId), getSenmlDeviceData());
                        publishedTelemetryDataCount++;
                    }

                    //Publish each resource data to the specific resource topic
                    if(isDummyResourceTelemetryActive && !isTelemetryLimitReached())
                        resourceMap.forEach((dummyResourceId, dummyResourceValue)->{
                            try {
                                String payload = gson.toJson(dummyResourceValue);
                                publishData(MqttTopics.getResourceTelemetryTopic(deviceId, dummyResourceId), payload);
                                publishedTelemetryDataCount++;
                            } catch (MqttException e) {
                                logger.error("Error Publishing Data ({}) for ResourceId: {}", dummyResourceValue, dummyResourceId);
                            }
                        });

                    logger.info("Published Telemetry Message: {}", publishedTelemetryDataCount);
            } catch (Exception e) {
                e.printStackTrace();
                cancel();
                semaphore.release();
            }
        }
    }

    private class EventTask extends TimerTask {
        @Override
        public void run() {
            try {
                publishData(MqttTopics.getDeviceEventTopic(deviceId), generateDummyEventData());
            } catch (MqttException e) {
                e.printStackTrace();
                cancel();
                semaphore.release();
            }
        }
    }

    @Override
    public void start(){
        this.call();
    }

    private boolean isTelemetryLimitReached(){
        if(dummyTotalTelemetryMessageLimit > 0 && publishedTelemetryDataCount >= dummyTotalTelemetryMessageLimit)
            return true;
        else
            return false;
    }

    public Void call() {

        try {

            logger.info("Waiting {} ms before starting ...", dummyDeviceStartDelayMs) ;
            Thread.sleep(dummyDeviceStartDelayMs);

            Timer smartObjectEventTimer = new Timer();
            smartObjectEventTimer.schedule(new EventTask(), 0, eventUpdateTimeMs);

            Timer telemetryTimer = new Timer();
            telemetryTimer.schedule(new TelemetryTask(), 5000, telemetryUpdateTimeMs);

            semaphore.acquire(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getSenmlDeviceData(){

        try{

            ArrayList<SenML> senmlList = new ArrayList<>();

            resourceMap.forEach((resourceId, resourceValue)->{
                senmlList.add(resourceValue);
            });

            return this.gson.toJson(senmlList);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private SenML generateRandomSenmlPyaload(String resourceId){

        SenML senmlData = new SenML();

        senmlData.setBaseName(resourceId);
        senmlData.setName(DUMMY_RESOURCE_TYPE);
        senmlData.setUnit(DUMMY_RESOURCE_UNIT);
        senmlData.setVersion(DEVICE_VERSION);
        senmlData.setUpdateTime(System.currentTimeMillis());

        if(this.randomPayloadByteSize > 0)
            senmlData.setDataValue(RandomStringUtils.random(this.randomPayloadByteSize, true, true));
        else
            senmlData.setValue(10 + rnd.nextDouble() * 20.0);

        return senmlData;
    }

    private void publishData(String topic, String msgString) throws MqttException {

        logger.debug("Publishing to Topic: {} Data: {}", topic, msgString);

        if (mqttClient.isConnected() && msgString != null && topic != null) {
            MqttMessage msg = new MqttMessage(msgString.getBytes());
            msg.setQos(this.mqttOutgoingClientQoS);
            msg.setRetained(this.mqttOutgoingClientRetainedMessages);
            mqttClient.publish(topic,msg);
            logger.debug("Data Correctly Published !");
        }
        else{
            logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
        }

    }

    private void generateResourcesRandomValues() {
        this.resourceMap.forEach((key, value) -> {
            resourceMap.put(key, generateRandomSenmlPyaload(key));
        });
    }

    private String generateDummyEventData(){

        EventMessage eventMessage = new EventMessage();
        eventMessage.setTimestamp(System.currentTimeMillis());
        eventMessage.setType(DUMMY_EVENT_TYPE);

        return this.gson.toJson(eventMessage);
    }

    public void setMqttClient(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public IMqttClient getMqttClient() {
        return mqttClient;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getEventUpdateTimeMs() {
        return eventUpdateTimeMs;
    }

    public void setEventUpdateTimeMs(int eventUpdateTimeMs) {
        this.eventUpdateTimeMs = eventUpdateTimeMs;
    }

    public int getTelemetryUpdateTimeMs() {
        return telemetryUpdateTimeMs;
    }

    public void setTelemetryUpdateTimeMs(int telemetryUpdateTimeMs) {
        this.telemetryUpdateTimeMs = telemetryUpdateTimeMs;
    }
}