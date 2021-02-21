# Getting Started
Our dev environment can be used in two ways
## Setup
### Docker
```shell
docker-compose up -d
```
When running through docker the container automaticly detects changes under your classpath, rebuilds the code and restarts 
the servlet container. The container outputs two logs `spring-build.log` and `spring-run.log` it's recommended to to run
`tail -f` on both of them because they have information about what going on i.e. `spring-build.log` will tell you why your
code is not compiling. `spring-run.log` will tell you why your requests are not working as expected.
### Local Installation
Because we are using Spring Boot you can just follow its really simple to install everything.
1. Install Java 15
    1. Pro Tip: To manage multiple versions of Java, you can use Jenv.
1. Run ./setup.sh
1. Navigate to http://localhost:8080 in your browser to see the application running
### Debugging
We have added JVM args to the `bootRun` task. These JVM args enable something called JPDA (Java Platform Debugger Architecture)
this allows you to add break points in your code witch get triggered when that part of the code executes. Please find out
on your own how to set up JPDA on your code editor or IDE. These are the JVM args we use
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

