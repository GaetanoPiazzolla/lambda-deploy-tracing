#!/bin/bash
set -eo pipefail
FUNCTION=$(aws cloudformation describe-stack-resource --stack-name java-basic --logical-resource-id function --query 'StackResourceDetail.PhysicalResourceId' --output text)
aws lambda create-function-url-config --function-name $FUNCTION --auth-type NONE
aws lambda add-permission --function-name $FUNCTION --action lambda:InvokeFunctionUrl --statement-id https --principal "*" --function-url-auth-type NONE --output text
