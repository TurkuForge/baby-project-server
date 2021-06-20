# Getting Started
Our dev environment can be used in two ways Docker or Local Installation
- [With Docker](#docker) you don't need to worry about the dependencies needed to run the environment. All you need is Docker.
- [With the local installation](#local-installation) you get more fine-grained control over your local environment E.g. Limiting the JVM heap size, specifying custom JVM args, and anything else you can come up with. But you have to maintain the local installation yourself. No additional services will be installed or started automatically.

## Setup
### Docker

We set up our Docker environment to be easy to work with. When you start the container it will start a process that listens for change under your classpath, 
once a change is detected it will automatically rebuild the changed files and restart the servlet container (in our case Tomcat). This allows a simple and easy development process because you never have to rebuild manually, the container knows what it needs to do and it does it for you.

To use Docker, follow these steps:
1. Run `docker-compose up -d`
1. Then navigate to `http://localhost:8080/` in your browser to see the application running.

**NOTE: The first time you run the docker-compose command it will take a bit longer to start so be patient**

### Local Installation
**NOTE: We don't recommend the local installation unless you have worked with [Gradle](https://gradle.org/) and/or other JVM-based languages before.**

The local installation offers more control and more configuration for your own development environment, but we update the Java versions without warning so make sure
to keep your local environment up to date and check this guide periodically so you can migrate to the new requirements.

To install locally, follow these steps:
1. Install Java 15
    1. Pro Tip: To manage multiple versions of Java, you can use [Jenv](https://www.jenv.be/).
1. Run `./setup.sh`
1. Navigate to `http://localhost:8080/` in your browser to see the application running

## After setup
Once everything is working and you have navigated to `http://localhost:8080/` you should see the root API resource in a `hal+json` format. If you don't see that check the [troubleshooting section](#troubleshooting).

## Troubleshooting
When `setup.sh` runs it will output two log files `spring-build.log` and `spring-run.log` (we recommend running
`tail -f` on these files for a more detailed output). 

The logs will give you more information about what is going on inside the application.
- `spring-build.log` informs you in case you have any compile-time errors happening in your code such as syntax or type errors, missing dependencies, etc.
- `spring-run.log` informs you about the runtime errors such as webserver exceptions and the Tomcat access log.

If you are having any issues setting up odds are one of these two files will tell you what you need to do.

## Debugging
We have added JVM args to the `bootRun` task. These JVM args enable something called JPDA (Java Platform Debugger Architecture)
this allows you to add breakpoints in your code witch get triggered when that part of the code executes. 

(Check the documentation for your code editor or IDE for more information about how to set up JPDA). 

These are the JVM args we use to enable JPDA:
```shell
-Xdebug -Xrunjdwp:transport=dt_socket,address=*:5005,server=y,suspend=n
```

# Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/gradle-plugin/reference/html/#build-image)
* [WebSocket](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-websockets)

### Guides
The following guides illustrate how to use some features concretely:

* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
