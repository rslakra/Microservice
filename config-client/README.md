# config-client

This guide walks you through the process of standing up, and consuming configuration from, the [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)

## What you'll build

You'll set-up a [Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) and then build a client that consumes the configuration on startup and then ```_refreshes_``` the configuration without restarting the client.

## What you'll need

- java_version: 21
- [prereq_editor_jdk_buildtools](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc)
- [how_to_complete_this_guide](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to_complete_this_guide.adoc)

## Build with Gradle

- [build_system_intro](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_system_intro.adoc)
- [create_directory_structure_hello](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/create_directory_structure_hello.adoc)
- [create_both_builds](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/create_both_builds.adoc)

`config-service/build.gradle`
// AsciiDoc source formatting doesn't support groovy, so using java instead
----
- [build.gradle](https://raw.githubusercontent.com/spring-guides/{project_id}/master/initial/config-service/build.gradle)
----

`config-client/build.gradle`
AsciiDoc source formatting doesn't support groovy, so using java instead
----
- [build.gradle](https://raw.githubusercontent.com/spring-guides/{project_id}/master/initial/config-client/build.gradle)
----

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/spring-boot-gradle-plugin.adoc[]

# Build with Maven

- [build_system_intro_maven](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_system_intro_maven.adoc)
- [create_directory_structure_hello](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/create_directory_structure_hello.adoc)

To get you started quickly, here are the complete configurations for the server and client applications:

`config-client/pom.xml`
----
- [Maven config-service](https://raw.githubusercontent.com/spring-guides/{project_id}/master/initial/config-client/pom.xml)
----

- [spring-boot-maven-plugin](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/spring-boot-maven-plugin.adoc)
- [hide-show-sts](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-sts.adoc)

## Reading Configuration from the Config Server using the Config Client

Now that we've stood up a Config Server, let's stand up a new Spring Boot application that uses the Config Server to load its own configuration and that `_refreshes_` its  configuration to reflect changes to the Config Server on-demand, without restarting the JVM. Add the `org.springframework.cloud:spring-cloud-starter-config` dependency in order to connect to the Config Server. 
Spring will see the configuration property files just like it would any property file loaded from `application.properties` or `application.yml` or any other `PropertySource`.

The properties to configure the  Config Client must necessarily be read in `_before_` the rest of the application's configuration is read from the Config Server, during the _bootstrap_ phase. Specify the client's `spring.application.name` as `config-client` and the location of the Config Server `spring.cloud.config.uri` in `config-client/src/main/resources/bootstrap.properties`, where it will be loaded earlier than any other configuration.


`config-client/src/main/resources/bootstrap.properties`
[source,java]
----
- [bootstrap.properties](config-client/src/main/resources/bootstrap.properties)
----

We also want to enable the `/refresh` endpoint so that we can demonstrate dynamic configuration changes:

`config-client/src/main/resources/application.properties`
[source,java]
----
- [application.properties](config-client/src/main/resources/application.properties)
----

The client may access any value in the Config Server using the traditional mechanisms (e.g. `@ConfigurationProperties`, `@Value("${...}")` or through the `Environment` abstraction). Create a Spring MVC REST controller that returns the resolved `message` property's value. Consult the [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/) guide to learn more about building REST services with Spring MVC and Spring Boot.

By default, the configuration values are read on the client's startup, and not again. You can force a bean to `_refresh_` its configuration - to pull updated values from the Config Server - by annotating the `MessageRestController` with the Spring Cloud Config `@RefreshScope` and then by triggering a `_refresh_` event.

`config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java`
[source,java]
----
- [ConfigClientApplication](config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java)
----

## Test the application

Test the end-to-end result by starting the Config Service first and then, once loaded, starting the client.
Visit the client app in the browser, `http://localhost:8080/message`. There, you should see the String `Hello Lakra` reflected in the response.

Change the `message` key in the `config-client.properties` file in the Git repository to something different (`Hello Git Config!`, perhaps?).
You can confirm that the Config Server sees the change by visiting `http://localhost:8888/config-client/default`. 
You need to invoke the `refresh` Spring Boot Actuator endpoint in order to force the client to refresh itself and draw the new value in. 
**Spring Boot's Actuator** exposes operational endpoints, like health checks and environment information, about an application. 
In order to use it you must add `org.springframework.boot:spring-boot-starter-actuator` to the client app's CLASSPATH. You can invoke the  `refresh` Actuator endpoint by sending an empty HTTP `POST` to the client's `refresh` endpoint, `http://localhost:8080/actuator/refresh`, and then confirm it worked by reviewing the `http://localhost:8080/message` endpoint.

```shell
curl localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"
```

NOTE: we set `management.endpoints.web.exposure.include=*` in the client app to make this easy to test (by default since Spring Boot 2.0 the Actuator endpoints are not exposed by default). 
By default you can still access them over JMX if you don't set the flag.


## Summary
Congratulations! You've just used Spring to centralize configuration for all your services by first standing up a  and to then dynamically update configuration.

## See Also

The following guides may also be helpful:

* [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Creating a Multi Module Project](https://spring.io/guides/gs/multi-module/)

- [footer](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc)

## Reference

*  [university-event-driven-architecture-for-java-developers-app-exercises](https://github.com/cockroachdb/university-event-driven-architecture-for-java-developers-app-exercises)
* [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html)
