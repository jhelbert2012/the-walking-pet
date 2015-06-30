#!/bin/bash

MEM_OPTS="-Xms40m -Xmx512m -XX:MaxPermSize=128m"
DEBUG_MODE="FALSE"
GENERATE_KEYSTORE="FALSE"

while getopts "kd:" options
    do
      case $options in
        d)
	  DEBUG_PORT=$OPTARG
	  DEBUG_MODE="TRUE"
          ;;
        k)
          GENERATE_KEYSTORE="TRUE"
	  ;; 
	*)
          echo "Usage: runJetty.sh [-k][-d port_number]"
          echo "-k: generate https keystore."
          echo "-d port_number: start Jetty in debug mode with port number."
          exit 1
      esac
    done

if [ $GENERATE_KEYSTORE == "TRUE" ]
then
    mvn keytool:clean keytool:genkey
fi

DEBUG_OPTS=""
if [ $DEBUG_MODE == "TRUE" ]; then
  DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n"
fi

export MAVEN_OPTS="$MEM_OPTS $DEBUG_OPTS"

echo ">>> Using MAVEN_OPTS=$MAVEN_OPTS"

#mvn jetty:run -P qa
mvn jetty:run -P development