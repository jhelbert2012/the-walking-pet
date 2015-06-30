#!/bin/bash

#DEVELOPMENT SCHEMA AUTOGENERATION
mvn zebra-hibernate:export-schema -Dvar.persistence.unit.name=com.zebra.das $@

