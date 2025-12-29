# Eureka Service

Load balancing application - The library to enable the communication between client and server.

All client server communication will be done through this load balancing server application.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Netflix Eureka Server**: Service discovery and registration

## Service Dependencies

**No dependencies** - This service must be started first as it is the service registry/discovery server that all other services depend on.

## Startup Order

**1. Start this service FIRST** - All other services depend on Eureka Service for service discovery.

```bash
cd eureka-service
./buildMaven.sh
./runMaven.sh
```

## Endpoints

### Eureka Service Endpoints

- **Home Page (Eureka/Registry Server Dashboard)**

```shell
http://localhost:8761/
```

- **Info Page**

```shell
http://localhost:8761/actuator/info
```

- **Service Health Status**

```shell
http://localhost:8761/actuator/health
```

```json
{
  "status": "UP"
}
```

## Configuration

- **Default Port**: 8761
- **Service Name**: eureka-service
- **Eureka Dashboard**: http://localhost:8761/

## References

# Author

- Rohtash Lakra
