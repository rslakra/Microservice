# Eureka Client

Eureka client application for service discovery demonstration.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Netflix Eureka Client**: Service discovery and registration

## Service Dependencies

- **eureka-service** (required) - Must be running for service registration

## Startup Order

**Can be started after eureka-service**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Then start eureka-client
cd ../eureka-client
./buildMaven.sh
./runMaven.sh
```

## Configuration

- **Default Port**: 8162 (configurable via `EUREKA_CLIENT_PORT` environment variable)
- **Service Name**: eureka-client
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/

## Endpoints

### Eureka Client

- **Home Page**

```shell
http://localhost:8162/
```

- **Info Page**

```shell
http://localhost:8162/actuator/info
```

- **Service Health Status**

```shell
http://localhost:8162/actuator/health
```

```json
{
  "status": "UP"
}
```

## References

# Author

- Rohtash Lakra
