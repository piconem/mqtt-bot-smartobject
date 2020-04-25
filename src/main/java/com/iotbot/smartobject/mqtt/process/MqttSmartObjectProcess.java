package com.iotbot.smartobject.mqtt.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.iotbot.smartobject.mqtt.exception.MqttSmartObjectConfigurationException;
import com.iotbot.smartobject.mqtt.resource.DummyDevice;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttSmartObjectProcess {

    private static final Logger logger = LoggerFactory.getLogger(MqttSmartObjectProcess.class);

    private static final String TAG = "[MQTT-SMARTOBJECT]";

    private static final String MQTT_SMARTOBJECT_CONFIGURATION_FILE = "mqtt_dummy_so_conf.yaml";

    private static MqttSmartObjectConfiguration mqttSmartObjectConfiguration;

    private static DummyDevice device = null;

    public static void main(String[] args) {

        try {

            logger.info("MQTT SmartObject Started !");

            readConfigurationFile();

            device = new DummyDevice(mqttSmartObjectConfiguration);
            device.init();

            startSmartObject();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void startSmartObject(){

        try{

            MqttClientPersistence persistence = new MemoryPersistence();
            IMqttClient publisher = new MqttClient(String.format("tcp://%s:%d",
                    mqttSmartObjectConfiguration.getMqttBrokerAddress(),
                    mqttSmartObjectConfiguration.getMqttBrokerPort()),
                    device.getDeviceId(),
                    persistence);

            //Set the MqttClient to the EngineResource
            device.setMqttClient(publisher);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect(options);

            logger.info("MQTT Client Connected !");

            device.start();

            logger.info("MQTT Smart Object STOPPED");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void readConfigurationFile() throws MqttSmartObjectConfigurationException {

        try{

            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //File file = new File(classLoader.getResource(WLDT_CONFIGURATION_FILE).getFile());
            File file = new File(MQTT_SMARTOBJECT_CONFIGURATION_FILE);

            ObjectMapper om = new ObjectMapper(new YAMLFactory());

            mqttSmartObjectConfiguration = om.readValue(file, MqttSmartObjectConfiguration.class);

            logger.error("{} MQTT Configuration Loaded ! Conf: {}", TAG, mqttSmartObjectConfiguration);

        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = String.format("ERROR LOADING CONFIGURATION FILE ! Error: %s", e.getLocalizedMessage());
            logger.error("{} {}", TAG, errorMessage);
            throw new MqttSmartObjectConfigurationException(errorMessage);
        }
    }

}
