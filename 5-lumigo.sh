#!/bin/bash
set -eo pipefail
FUNCTION=$(aws cloudformation describe-stack-resource --stack-name java-basic --logical-resource-id function --query 'StackResourceDetail.PhysicalResourceId' --output text)
aws lambda update-function-configuration --function-name $FUNCTION \
    --environment "Variables={LUMIGO_TRACER_TOKEN=$1,JAVA_TOOL_OPTIONS='-Djdk.attach.allowAttachSelf=true'}"