# API Gateway

This application will act as a middleware in between Server Application and Client Application.

All requests going to the Server application will be filtered here.

We are using ```spring-cloud-starter-gateway``` library (replacing deprecated Netflix Zuul) to enable this filtering process.

```netflix-eureka-client``` is the library which used to register the application with Eureka naming server.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Spring Cloud Gateway**: API Gateway with reactive stack (WebFlux)
- **Netflix Eureka Client**: Service discovery and registration

## Service Dependencies

- **eureka-service** (required) - Must be running for service discovery
- **config-service** (optional) - For centralized configuration management

## Startup Order

**4. Start after eureka-service (and optionally config-service)**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Optionally start config-service
cd ../config-service
./buildMaven.sh && ./runMaven.sh

# 3. Then start api-gateway-service
cd ../api-gateway-service
./buildMaven.sh
./runMaven.sh
```

## Configuration

- **Default Port**: 8763
- **Service Name**: api-gateway-service
- **Gateway URL**: http://localhost:8763/
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/

## Endpoints

### API Gateway Service

- **Home Page**

```shell
http://localhost:8763/
```

- **Info Page**

```shell
http://localhost:8763/actuator/info
```

- **Service Health Status**

```shell
http://localhost:8763/actuator/health
```

```json
{
  "status": "UP"
}
```

## Gateway Features

- **Service Discovery**: Automatically discovers services from Eureka
- **Load Balancing**: Routes requests to available service instances
- **Dynamic Routing**: Routes configured via service discovery or custom routes
- **Request/Response Filtering**: Can filter and transform requests/responses

## References

- [API Gateway with Load Balancer](https://dzone.com/articles/create-an-api-gateway-with-load-balancer-in-java)

# Author

- Rohtash Lakra
