#!/bin/bash
#Author: Rohtash Lakra
clear
echo
JAVA_VERSION=11
export JAVA_HOME=$(/usr/libexec/java_home -v $JAVA_VERSION)
echo "${JAVA_HOME}"
echo
./gradlew clean build
echo
