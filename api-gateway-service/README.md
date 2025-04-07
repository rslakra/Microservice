# API Gateway

This application will act as a middleware in between Server Application and Client Application.

All requests going to the Server application will be filtered here.

We are using ```spring-cloud-starter-netflix-zuul``` library to enable this filtering process.

```netflix-eureka-client``` is the library which used to register the application with Eureka naming server.

## Endpoints

### Eureka Service

- Home Page

```shell
http://localhost:8161/
```

- Info Page

```shell
http://localhost:8161/actuator/info
```

- Service Health Status

```shell
http://localhost:8161/actuator/health
```

```json
{
  "status": "UP"
}
```

# References

- [API Gateway with Load Balancer](https://dzone.com/articles/create-an-api-gateway-with-load-balancer-in-java)

# Author

- Rohtash Lakra
