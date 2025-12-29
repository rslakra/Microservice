# config-service

This guide walks you through the process of standing up, and consuming configuration from, the [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Spring Cloud Config Server**: Provides centralized configuration management
- **Netflix Eureka Client**: Service discovery and registration

> **Note**: Spring Boot 3.5.7 is not officially compatible with Spring Cloud 2024.0.0 (which supports Spring Boot 3.4.x). The compatibility verifier has been disabled as a workaround. For production, consider using Spring Boot 3.4.7.

## Service Dependencies

- **eureka-service** (required) - Must be running for service registration

## Startup Order

**2. Start after eureka-service**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Then start config-service
cd ../config-service
./buildMaven.sh
./runMaven.sh
```

## What you'll build

You'll set-up a [Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) and then build a client that consumes the configuration on startup and then ```_refreshes_``` the configuration without restarting the client.

## What you'll need

- java_version: 21
- [prereq_editor_jdk_buildtools](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc)
- [how_to_complete_this_guide](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to_complete_this_guide.adoc)

## Configuration

- **Default Port**: 8116
- **Service Name**: config-service
- **Config Server URL**: http://localhost:8116/
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/

## Stand up a Config Server

You'll first need a Config Service to act as a sort of intermediary between your Spring applications and a typically version-controlled repository of configuration files. You can use Spring Cloud's `@EnableConfigServer` to stand up a config server that other applications can talk to. This is a regular Spring Boot application with one annotation added to _enable_ the config server.

`com/rslakra/microservice/configservice/ConfigServiceApplication.java`
----
- [ConfigServiceApplication](com.rslakra.microservice.configservice/ConfigServiceApplication.java)
----

The Config Server needs to know which repository to manage. There are several choices here, but we'll use a Git-based filesystem repository. 
You could as easily point the Config Server to a Github or GitLab repository, as well. On the file system, create a new directory and `git init` it. 
Then add a file called `config-client.properties` to the Git repository. Make sure to also `git commit` it, as well.
Later, you will connect to the Config Server with a Spring Boot application whose `spring.application.name` property identifies it as `config-client` to the Config Server. 
This is how the Config Server will know which set of configuration to send to a specific client. It will _also_ send all the values from any file named `application.properties` or `application.yml` in the Git repository.
Property keys in more specifically named files (like `config-client.properties`) override those in `application.properties` or `application.yml`.

Add a simple property and value, `message = Hello world`, to the newly created `config-client.properties` file and then `git commit` the change.

Specify the path to the Git repository by specifying the `spring.cloud.config.server.git.uri` property in `config-service/src/main/resources/application.properties`. 
Make sure to also specify a different `server.port` value to avoid port conflicts when you run both this server and another Spring Boot application on the same machine.

`config-service/src/main/resources/application.properties`
[source,properties]
----
- [application.properties](config-service/src/main/resources/application.properties)
----

### Configuration Options

The Config Server supports multiple backend storage options:

1. **Git Repository** (default): Configure `spring.cloud.config.server.git.uri` to point to a Git repository
2. **Native Profile**: Use `spring.profiles.active=native` with `spring.cloud.config.server.native.searchLocations` for file-based configuration (useful for testing)

In this example we are using a file-based git repository at `${HOME}/Downloads/AppData/ConfigService`.
You can create one easily by making a new directory and git committing properties and YAML files to it.

E.g.

----
```shell
cd ~/Downloads/AppData/ConfigService
find .
./.git

...
./application.yml
```

----

Or you could use a remote git repository, e.g. on github, if you change the configuration file in the application to point to that instead.

## Spring Boot 3.x Migration Notes

### Key Changes from Spring Boot 2.x

1. **Bootstrap Phase Removed**: The bootstrap context has been removed. Use `spring.config.import` instead of `bootstrap.properties`
2. **Profile Configuration**: Use `spring.config.activate.on-profile` instead of `spring.profiles` in YAML files
3. **Jakarta EE**: All `javax.*` packages have been migrated to `jakarta.*`
4. **Native Profile Support**: The native profile is fully supported for file-based configuration without Git

### Compatibility Note

This service uses Spring Boot 3.5.7 with Spring Cloud 2024.0.0. While not officially compatible (Spring Cloud 2024.0.0 supports Spring Boot 3.4.x), the compatibility verifier has been disabled to allow the upgrade. For production environments, consider using Spring Boot 3.4.7 for full compatibility.

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

# Author

- Rohtash Lakra

