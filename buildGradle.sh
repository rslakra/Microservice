#!/bin/bash
#Author: Rohtash Lakra
clear
echo
JAVA_VERSION=11
export JAVA_HOME=$(/usr/libexec/java_home -v $JAVA_VERSION)
#export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-11.jdk/Contents/Home"
echo "${JAVA_HOME}"
echo
