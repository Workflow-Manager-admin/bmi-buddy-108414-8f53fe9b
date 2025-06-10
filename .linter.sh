#!/bin/bash
cd /home/kavia/workspace/code-generation/bmi-buddy-108414-8f53fe9b/bmi_buddy
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

