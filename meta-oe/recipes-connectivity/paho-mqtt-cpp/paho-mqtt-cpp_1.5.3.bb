SUMMARY = "Paho MQTT - C++ libraries for the MQTT and MQTT-SN protocols"
DESCRIPTION = "Client implementation of open and standard messaging protocols for Machine-to-Machine (M2M) and Internet of Things (IoT)."
HOMEPAGE = "http://www.eclipse.org/paho/"
SECTION = "console/network"
LICENSE = "EPL-2.0 | EDL-1.0"

LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=c0fa3a632eea67f4079c54df781d1441 \
    file://edl-v10;md5=3adfcc70f5aeb7a44f3f9b495aa1fbf3 \
    file://epl-v20;md5=d9fc0efef5228704e7f5b37f27192723 \
"

SRC_URI = "git://github.com/eclipse/paho.mqtt.cpp;protocol=https;branch=master;tag=v${PV}"
SRCREV = "17ff3dc0270738adc710667be44847eebc038ae0"

DEPENDS = "openssl paho-mqtt-c"


inherit cmake

EXTRA_OECMAKE += "-DPAHO_WITH_SSL=ON -DPAHO_BUILD_EXAMPLES=OFF"
