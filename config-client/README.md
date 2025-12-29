# config-client

This guide walks you through the process of standing up, and consuming configuration from, the [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Spring Cloud Config Client**: Consumes configuration from the Config Server
- **Netflix Eureka Client**: Service discovery and registration

> **Note**: Spring Boot 3.5.7 is not officially compatible with Spring Cloud 2024.0.0 (which supports Spring Boot 3.4.x). The compatibility verifier has been disabled as a workaround. For production, consider using Spring Boot 3.4.7.

## Service Dependencies

- **eureka-service** (required) - Must be running for service registration
- **config-service** (required) - Must be running to fetch configuration

## Startup Order

**3. Start after eureka-service and config-service**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Then start config-service
cd ../config-service
./buildMaven.sh && ./runMaven.sh

# 3. Finally start config-client
cd ../config-client
./buildMaven.sh
./runMaven.sh
```

## Configuration

- **Default Port**: 8016
- **Service Name**: config-client
- **Config Server URL**: http://localhost:8116/
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/

## What you'll build

You'll set-up a [Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) and then build a client that consumes the configuration on startup and then ```_refreshes_``` the configuration without restarting the client.

## What you'll need

- java_version: 21
- [prereq_editor_jdk_buildtools](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc)
- [how_to_complete_this_guide](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to-complete_this_guide.adoc)

## Reading Configuration from the Config Server using the Config Client

Now that we've stood up a Config Server, let's stand up a new Spring Boot application that uses the Config Server to load its own configuration and that `_refreshes_` its  configuration to reflect changes to the Config Server on-demand, without restarting the JVM. Add the `org.springframework.cloud:spring-cloud-starter-config` dependency in order to connect to the Config Server. 
Spring will see the configuration property files just like it would any property file loaded from `application.properties` or `application.yml` or any other `PropertySource`.

### Spring Boot 3.x Configuration Changes

**Important**: In Spring Boot 3.x, the bootstrap phase has been **removed**. Instead of using `bootstrap.properties`, you must configure the Config Client using `spring.config.import` in your `application.properties` or `application.yml` file.

#### Configuration in application.properties

```properties
# Config Server connection using spring.config.import
spring.config.import = optional:configserver:http://localhost:8116/

# Application name (used by Config Server to identify which config to serve)
spring.application.name = config-client

# Config Server settings
config.service.port = 8116
```

The `optional:` prefix allows the application to start even if the Config Server is unavailable, which is useful for development and testing.

#### Configuration in application.yml

```yaml
spring:
  application:
    name: config-client
  config:
    import: optional:configserver:http://localhost:8116/
```

We also want to enable the `/refresh` endpoint so that we can demonstrate dynamic configuration changes:

`config-client/src/main/resources/application.properties`
[source,java]
----
- [application.properties](config-client/src/main/resources/application.properties)
----

> **Note**: The `bootstrap.properties` file is no longer used in Spring Boot 3.x. All configuration should be in `application.properties` or `application.yml` using `spring.config.import`.

The client may access any value in the Config Server using the traditional mechanisms (e.g. `@ConfigurationProperties`, `@Value("${...}")` or through the `Environment` abstraction). Create a Spring MVC REST controller that returns the resolved `message` property's value. Consult the [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/) guide to learn more about building REST services with Spring MVC and Spring Boot.

By default, the configuration values are read on the client's startup, and not again. You can force a bean to `_refresh_` its configuration - to pull updated values from the Config Server - by annotating the `MessageRestController` with the Spring Cloud Config `@RefreshScope` and then by triggering a `_refresh_` event.

`config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java`
[source,java]
----
- [ConfigClientApplication](config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java)
----

## Test the application

Test the end-to-end result by starting the Config Service first and then, once loaded, starting the client.
Visit the client app in the browser, `http://localhost:8016/message`. There, you should see the String `Hello Lakra` reflected in the response.

Change the `message` key in the `config-client.properties` file in the Git repository to something different (`Hello Git Config!`, perhaps?).
You can confirm that the Config Server sees the change by visiting `http://localhost:8116/config-client/default`. 
You need to invoke the `refresh` Spring Boot Actuator endpoint in order to force the client to refresh itself and draw the new value in. 
**Spring Boot's Actuator** exposes operational endpoints, like health checks and environment information, about an application. 
In order to use it you must add `org.springframework.boot:spring-boot-starter-actuator` to the client app's CLASSPATH. You can invoke the  `refresh` Actuator endpoint by sending an empty HTTP `POST` to the client's `refresh` endpoint, `http://localhost:8016/actuator/refresh`, and then confirm it worked by reviewing the `http://localhost:8016/message` endpoint.

```shell
curl localhost:8016/actuator/refresh -d {} -H "Content-Type: application/json"
```

NOTE: we set `management.endpoints.web.exposure.include=*` in the client app to make this easy to test (by default since Spring Boot 2.0 the Actuator endpoints are not exposed by default). 
By default you can still access them over JMX if you don't set the flag.

## Spring Boot 3.x Migration Notes

### Key Changes from Spring Boot 2.x

1. **Bootstrap Phase Removed**: The bootstrap context has been completely removed in Spring Boot 3.x. Use `spring.config.import` in `application.properties` or `application.yml` instead of `bootstrap.properties`.

2. **Config Server Connection**: 
   - **Old (Spring Boot 2.x)**: Use `bootstrap.properties` with `spring.cloud.config.uri`
   - **New (Spring Boot 3.x)**: Use `spring.config.import=optional:configserver:http://localhost:8116/` in `application.properties`

3. **Optional Config Server**: The `optional:` prefix allows the application to start even if the Config Server is unavailable. Remove it if you want the application to fail fast when the Config Server is down.

4. **Jakarta EE**: All `javax.*` packages have been migrated to `jakarta.*` in Spring Boot 3.x.

5. **Actuator Endpoints**: Actuator endpoints remain the same, but ensure you have `spring-boot-starter-actuator` in your dependencies.

### Example Configuration

```properties
# application.properties
spring.application.name=config-client
spring.config.import=optional:configserver:http://localhost:8116/
management.endpoints.web.exposure.include=*
```

### Compatibility Note

This client uses Spring Boot 3.5.7 with Spring Cloud 2024.0.0. While not officially compatible (Spring Cloud 2024.0.0 supports Spring Boot 3.4.x), the compatibility verifier has been disabled to allow the upgrade. For production environments, consider using Spring Boot 3.4.7 for full compatibility.

## Summary
Congratulations! You've just used Spring to centralize configuration for all your services by first standing up a Config Server and then dynamically updating configuration without restarting your applications.

## See Also

The following guides may also be helpful:

* [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Creating a Multi Module Project](https://spring.io/guides/gs/multi-module/)

- [footer](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc)

## Reference

*  [university-event-driven-architecture-for-java-developers-app-exercises](https://github.com/cockroachdb/university-event-driven-architecture-for-java-developers-app-exercises)
* [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html)
* [Spring Boot 3.x Migration Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/upgrading.html)
* [Spring Cloud 2024.0.0 Release Notes](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2024.0-Release-Notes)
* [Spring Boot 3.x Configuration Properties Migration](https://docs.spring.io/spring-boot/docs/current/reference/html/upgrading.html#upgrading.configuration-properties)

# Author

- Rohtash Lakra

