# MQTT BOT Smart Object

This project implement a BOT MQTT Smart Object with existing resources of different types.
Each resource will generate data with different random time period and send data to a local broker.

Three main topics categories have been defined: 

- Telemetry
- Events
- Command & Controls

The project includes also Tester classes useful to test the performance and the behaviour of created MQTT Smart Objects.
The MQTT library currently used by the project is Eclipse Paho (https://www.eclipse.org/paho/).

## RUN MQTT Broker

Folder 'docker' contains the scripts to run a local MQTT broker through Eclipse Mosquitto (https://mosquitto.org/) Docker container.
