# Basic function with minimal dependencies (Java)

The project source includes function code and supporting resources:
- `src/main` - A Java function.
- `src/test` - A unit test and helper classes.
- `template.yml` - An AWS CloudFormation template that creates an application.
- `build.gradle` - A Gradle build file.
- `1-create-bucket.sh`, `2-deploy.sh`, etc. - Shell scripts that use the AWS CLI to deploy and manage the application.

Use the following instructions to deploy the sample application.

# Requirements
- [Java 8 runtime environment (SE JRE)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 5](https://gradle.org/releases/) or [Maven 3](https://maven.apache.org/docs/history.html)
- The Bash shell. For Linux and macOS, this is included by default. In Windows 10, you can install the [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10) to get a Windows-integrated version of Ubuntu and Bash.
- [The AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html) v1.17 or newer.

If you use the AWS CLI v2, add the following to your [configuration file](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html) (`~/.aws/config`):

```
cli_binary_format=raw-in-base64-out
```

This setting enables the AWS CLI v2 to load JSON events from a file, matching the v1 behavior.

# Setup
Download or clone this repository.

    $ git clone https://github.com/GaetanoPiazzolla/lambda-deploy-tracing.git

To create a new bucket for deployment artifacts, run `1-create-bucket.sh`.

    $ ./1-create-bucket.sh
    make_bucket: lambda-artifacts-78645687

# Deploy
To deploy the application, run `2-deploy.sh`.

    $ ./2-deploy.sh
    BUILD SUCCESSFUL in 1s
    Successfully packaged artifacts and wrote output template to file out.yml.
    Waiting for changeset to be created..
    Successfully created/updated stack - java-basic

This script uses AWS CloudFormation to deploy the Lambda functions and an IAM role. If the AWS CloudFormation stack that contains the resources already exists, the script updates it with any changes to the template or function code.

# Expose public URL
To expose the lambda as a public URL run `3-expose-url.sh`.

    $ ./3-expose-url.sh
    $ ./3-expose-url.sh
    {
      "FunctionUrl": "https://78656486.lambda-url.eu-north-1.on.aws/",
      "FunctionArn": "arn:aws:lambda:eu-north-1:786786:function:java-basic-function-7867546",
      "AuthType": "NONE",
     "CreationTime": "2023-07-11T12:00:27.747272Z"
    }

#

# Cleanup
To delete the application, run `4-cleanup.sh`.

    $ ./4-cleanup.sh
    Deleted java-basic stack.
    Delete deployment artifacts and bucket (lambda-artifacts-456489452)? (y/n)y
    delete: s3://lambda-artifacts-456489452/74871512
    delete: s3://lambda-artifacts-456489452/74871513
    remove_bucket: lambda-artifacts-456489452 ...

# Lumigo Integration
To enable lumigo integration, copy the token from the portal and then run `5-lumigo.sh`

    $ ./5-lumigo.sh <token>
    {
      "FunctionName": "java-basic-function-UYSNaZ9Y4nef",
      "FunctionArn": "arn:aws:lambda:eu-north-1:4563465:function:java-basic-function-786786" 
    ...

### Code started from https://github.com/awsdocs/aws-lambda-developer-guide including only one Lambda java-basic handler, using only gradle.
### Related to medium article: TODO