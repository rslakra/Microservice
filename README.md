Microservice
=========
---

This repository contains all the projects categorized based on the modules and learning purposes.

These projects should have the basic and core implementations which can be used by other projects.
It might be some of them use any third party library, so the source code available in this repository will be available
for AS IT IS usage.

## Folder Structure Conventions

---

```
    /
    ├── api-gateway-service         # The API Gateway Project
    ├── common-service              # The Common Service Project
    ├── config-client               # The Config Client Project
    ├── config-service              # The Config Service Project
    ├── eureka-client               # The Eureka/Registry Client Project
    ├── eureka-service              # The Eureka/Registry Server Project
    ├── product-client              # The Product Client Project
    ├── product-service             # The Product Service Project
    └── README.md
```

This guide walks you through the process of standing up, and consuming configuration from,
the [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)

## What you'll build

You'll set-up a [Config Server](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) and then build a
client that consumes the configuration on startup and then ```_refreshes_``` the configuration without restarting the
client.

## What you'll need

- java_version: 11
- [prereq_editor_jdk_buildtools](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc)
- [how_to_complete_this_guide](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to_complete_this_guide.adoc)

## Build with Gradle

- [build_system_intro](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_system_intro.adoc)
- [create_directory_structure_hello](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/create_directory_structure_hello.adoc)
- [create_both_builds](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/create_both_builds.adoc)

`config-service/build.gradle`

AsciiDoc source formatting doesn't support groovy, so using java instead
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

`config-service/pom.xml`
----

- [Maven config-service](https://raw.githubusercontent.com/spring-guides/{project_id}/master/initial/config-service/pom.xml)

----

`config-client/pom.xml`
----

- [Maven config-service](https://raw.githubusercontent.com/spring-guides/{project_id}/master/initial/config-client/pom.xml)

----

- [spring-boot-maven-plugin](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/spring-boot-maven-plugin.adoc)
- [hide-show-sts](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-sts.adoc)

## Stand up a Config Server

You'll first need a Config Service to act as a sort of intermediary between your Spring applications and a typically
version-controlled repository of configuration files. You can use Spring Cloud's `@EnableConfigServer` to standup a
config server that other applications can talk to. This is a regular Spring Boot application with one annotation added
to _enable_ the config server.

----

- [ConfigServiceApplication](config-service/src/main/java/com/rslakra/microservice/configservice/ConfigServiceApplication.java)

----

The Config Server needs to know which repository to manage. There are several choices here, but we'll use a Git-based
filesystem repository.
You could as easily point the Config Server to a Github or GitLab repository, as well. On the file system, create a new
directory and `git init` it.
Then add a file called `config-client.properties` to the Git repository. Make sure to also `git commit` it, as well.
Later, you will connect to the Config Server with a Spring Boot application whose `spring.application.name` property
identifies it as `config-client` to the Config Server.
This is how the Config Server will know which set of configuration to send to a specific client. It will _also_ send all
the values from any file named `application.properties` or `application.yml` in the Git repository.
Property keys in more specifically named files (like `config-client.properties`) override those
in `application.properties` or `application.yml`.

Add a simple property and value, `message = Hello world`, to the newly created `config-client.properties` file and
then `git commit` the change.

Specify the path to the Git repository by specifying the `spring.cloud.config.server.git.uri` property
in `config-service/src/main/resources/application.properties`.
Make sure to also specify a different `server.port` value to avoid port conflicts when you run both this server and
another Spring Boot application on the same machine.

`config-service/src/main/resources/application.properties`
[source,properties]
----

- [application.properties](config-service/src/main/resources/application.properties)

----

In this example we are using a file-based git repository at `${HOME}/Downloads/AppData/ConfigService`.
You can create one easily by making a new directory and git committing properties and YAML files to it.

OR

You can clone an existing sample repository [config-service-resources](https://github.com/rslakra/config-service-resources.git)

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

Or you could use a remote git repository, e.g. on github, if you change the configuration file in the application to
point to that instead.

## Reading Configuration from the Config Server using the Config Client

Now that we've stood up a Config Server, let's stand up a new Spring Boot application that uses the Config Server to
load its own configuration and that `_refreshes_` its configuration to reflect changes to the Config Server on-demand,
without restarting the JVM. Add the `org.springframework.cloud:spring-cloud-starter-config` dependency in order to
connect to the Config Server.
Spring will see the configuration property files just like it would any property file loaded
from `application.properties` or `application.yml` or any other `PropertySource`.

The properties to configure the Config Client must necessarily be read in `_before_` the rest of the application's
configuration is read from the Config Server, during the _bootstrap_ phase. Specify the
client's `spring.application.name` as `config-client` and the location of the Config Server `spring.cloud.config.uri`
in `config-client/src/main/resources/bootstrap.properties`, where it will be loaded earlier than any other
configuration.

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

The client may access any value in the Config Server using the traditional mechanisms (
e.g. `@ConfigurationProperties`, `@Value("${...}")` or through the `Environment` abstraction). Create a Spring MVC REST
controller that returns the resolved `message` property's value. Consult
the [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/) guide to learn more about building REST
services with Spring MVC and Spring Boot.

By default, the configuration values are read on the client's startup, and not again. You can force a bean
to `_refresh_` its configuration - to pull updated values from the Config Server - by annotating
the `MessageRestController` with the Spring Cloud Config `@RefreshScope` and then by triggering a `_refresh_` event.

`config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java`
[source,java]
----

- [ConfigClientApplication](config-client/src/main/java/com/rslakra/microservices/configclient/ConfigClientApplication.java)

----

## Test the application

Test the end-to-end result by starting the Config Service first and then, once loaded, starting the client.
Visit the client app in the browser, `http://localhost:8080/message`. There, you should see the String `Hello Lakra`
reflected in the response.

Change the `message` key in the `config-client.properties` file in the Git repository to something
different (`Hello Git Config!`, perhaps?).
You can confirm that the Config Server sees the change by visiting `http://localhost:8888/config-client/default`.
You need to invoke the `refresh` Spring Boot Actuator endpoint in order to force the client to refresh itself and draw
the new value in.
**Spring Boot's Actuator** exposes operational endpoints, like health checks and environment information, about an
application.
In order to use it you must add `org.springframework.boot:spring-boot-starter-actuator` to the client app's CLASSPATH.
You can invoke the  `refresh` Actuator endpoint by sending an empty HTTP `POST` to the client's `refresh`
endpoint, `http://localhost:8080/actuator/refresh`, and then confirm it worked by reviewing
the `http://localhost:8080/message` endpoint.

```shell
curl localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"
```

NOTE: we set `management.endpoints.web.exposure.include=*` in the client app to make this easy to test (by default since
Spring Boot 2.0 the Actuator endpoints are not exposed by default).
By default you can still access them over JMX if you don't set the flag.

## Summary

Congratulations! You've just used Spring to centralize configuration for all your services by first standing up a and to
then dynamically update configuration.

## See Also

The following guides may also be helpful:

* [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Creating a Multi Module Project](https://spring.io/guides/gs/multi-module/)

- [footer](https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc)

## Reference

* [university-event-driven-architecture-for-java-developers-app-exercises](https://github.com/cockroachdb/university-event-driven-architecture-for-java-developers-app-exercises)
* [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html)

## Prerequisites

---

What things you need to install the software and how to install them

Check if you already have java or not. If not, install java ``11`` version.

```
java -version
```

Now, check if you already have Maven or not. If not, install Maven ``3.6.0`` or
latest version.

```
mvn --version
```

Next, make sure you have an IDE either Eclipse or IntelliJ.

## How to set up

---

You should have GIT account to check out the code.

### 1. Clone the repository in your GIT account or local machine

Open terminal and go to your workspace. Then run the following command to check out the source code.

> ```
>   git clone https://github.com/rslakra/Microservice.git
> ```

### 2. Build the project

> ```
>   cd Microservice
> ./buildMaven.sh
> ```

### 3. Run the program

Run the program with the following command

> ```./run.sh```


If you are missing either any of the above-mentioned steps or software,
please following the following ``Installing`` section.

## Installing

A step by step series of examples that tell you how to get a development
environment running on your local machine.

The following URLs will help you to download and install the required software.
Please follow the instructions and download the latest stable version, as
supported by your OS version.

* [Open JDK 11](https://openjdk.org/projects/jdk/11/) - Open JDK 11 Development Kit
* [Brew JDK 11](https://formulae.brew.sh/formula/openjdk@11) - Brew Formula Open JDK 11 Development Kit
* [Oracle JDK 11](https://www.oracle.com/java/technologies/downloads/#java11-mac) - Oracle JDK 11 Development Kit
* [Maven](https://maven.apache.org/download.cgi) - Apache Maven
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=mac) - Download IntelliJ IDEA
* [Eclipse](https://www.eclipse.org/downloads/) - Eclipse IDE 2019‑09
* [Tomcat](https://tomcat.apache.org/download-90.cgi) - Apache Tomcat® 9.x

### Deployment

---

Once you have installed all the above-mentioned software, then you are ready to run the code on your local machine.

## Pipeline

---


[![Pipeline Status][status-image]][status-url]

[status-image]: AppSuite/badges/master/pipeline.svg

[status-url]: AppSuite/badges/master/pipeline.svg

## Contributing

---

Please read [CONTRIBUTING.md](https://github.com/rslakra/AppSuite/blob/master/CONTRIBUTING.md) for details on our code
of conduct, and the process for submitting pull requests to us.

## Authors

---

* [Rohtash Lakra](https://github.com/rslakra)
* [Microservice](https://github.com/rslakra/Microservice.git)

See also the list of [contributors](https://github.com/rslakra/AppSuite/contributors) who participated in this project.

## License

---

This project is licensed under the Apache License - see the [LICENSE.md](https://github.com/rslakra/AppSuite/LICENSE.md)
file for details

## Acknowledgments

---

* Passion
* Inspiration
* Motivation
