#!/bin/bash
echo "Running integration tests..."
LOG_FILE="runIntegrationTests.$(date +%Y%m%d-%H%M).log"
echo "Logging output to $LOG_FILE"
/usr/bin/xvfb-run --server-args="-screen 0 1024x768x24" mvn integration-test > $LOG_FILE
