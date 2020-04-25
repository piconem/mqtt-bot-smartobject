#!/bin/bash

#docker run -it -p 1883:1883 -p 9001:9001 -v mosquitto.conf:/mosquitto/config/mosquitto.conf eclipse-mosquitto

#docker run -it -p 1883:1883 -p 9001:9001 -v mosquitto.conf:/mosquitto/config/mosquitto.conf -v /mosquitto/data -v /mosquitto/log eclipse-mosquitto


docker run --name=mosquitto-broker \
    -p 1883:1883 \
    -p 9001:9001 \
    -v ${PWD}/mosquitto.conf:/mosquitto/config/mosquitto.conf \
    -v ${PWD}/data:/mosquitto/data \
    -v ${PWD}/log:/mosquitto/log \
    -d eclipse-mosquitto