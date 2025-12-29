# common-service

Common service providing shared utilities and components for microservices.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21

## Service Dependencies

**No runtime dependencies** - This is a library/service that can be used by other services.

## Startup Order

**Can be started independently or as a dependency**

This service can be:
- Built as a library and included in other services
- Run as a standalone service (if configured)

```bash
cd common-service
./buildMaven.sh
./runMaven.sh  # If configured to run standalone
```

## Configuration

- **Service Type**: Library/Shared Service
- **Usage**: Included as a dependency in other microservices

## Features

- **Shared Components**: Common utilities and components
- **Reusable Code**: Shared across multiple microservices

## Endpoints

# References

# Author

- Rohtash Lakra
