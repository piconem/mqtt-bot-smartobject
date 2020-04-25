package com.iotbot.smartobject.mqtt.tester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.iotbot.smartobject.mqtt.exception.MqttSmartObjectConfigurationException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttConsumerTesterProcess {

    private final static Logger logger = LoggerFactory.getLogger(MqttConsumerTesterProcess.class);

    private static final String CONFIGURATION_FILE = "mqtt_tester_conf.yaml";

    private static MqttTesterConfiguration testerConfiguration = null;

    public static void main(String [ ] args) {

        logger.info("MQTT Consumer Tester Started ...");

        try{

            readConfigurationFile();

            String publisherId = UUID.randomUUID().toString();

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient subscriber = new MqttClient(
                    String.format("tcp://%s:%d", testerConfiguration.getMqttBrokerAddress(), testerConfiguration.getMqttBrokerPort()),
                    publisherId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            subscriber.connect(options);

            logger.info("Connected !");

            //Registering forTelemetry Topic
            CountDownLatch receivedSignal = new CountDownLatch(10);
            subscriber.subscribe("#", (topic, msg) -> {
                byte[] payload = msg.getPayload();
                logger.info("Message Received ({}) Message Received: {}", topic, new String(payload));
                //receivedSignal.countDown();
            });

            receivedSignal.await(1, TimeUnit.MINUTES);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void readConfigurationFile() throws MqttSmartObjectConfigurationException {

        try{

            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //File file = new File(classLoader.getResource(WLDT_CONFIGURATION_FILE).getFile());
            File file = new File(CONFIGURATION_FILE);

            ObjectMapper om = new ObjectMapper(new YAMLFactory());

            testerConfiguration = om.readValue(file, MqttTesterConfiguration.class);

            logger.error("{} Configuration Loaded ! Conf: {}", testerConfiguration);

        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = String.format("ERROR LOADING CONFIGURATION FILE ! Error: %s", e.getLocalizedMessage());
            logger.error("{}", errorMessage);
            throw new MqttSmartObjectConfigurationException(errorMessage);
        }
    }

}
