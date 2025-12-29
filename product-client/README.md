# product-client

The client application will act as the Client Application for displaying products.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Spring Cloud OpenFeign**: REST client for calling product-service
- **Netflix Eureka Client**: Service discovery and registration
- **Thymeleaf**: Server-side templating for UI

## Service Dependencies

- **eureka-service** (required) - Must be running for service discovery
- **api-gateway-service** (required) - Must be running to route requests
- **product-service** (required) - Must be running to fetch product data
- **config-service** (optional) - For centralized configuration management

## Startup Order

**6. Start after eureka-service, api-gateway-service, and product-service**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Optionally start config-service
cd ../config-service
./buildMaven.sh && ./runMaven.sh

# 3. Start api-gateway-service
cd ../api-gateway-service
./buildMaven.sh && ./runMaven.sh

# 4. Start product-service
cd ../product-service
./buildMaven.sh && ./runMaven.sh

# 5. Finally start product-client
cd ../product-client
./buildMaven.sh
./runMaven.sh
```

## Configuration

- **Default Port**: 8085
- **Service Name**: product-client
- **Context Path**: /product-client
- **Base URL**: http://localhost:8085/product-client
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/
- **API Gateway**: Routes through api-gateway-service at http://localhost:8763/

## Endpoints

### Service

- **Home Page / Products List**
```shell
http://localhost:8085/product-client/
```

- **Products Page (Alternative Route)**
```shell
http://localhost:8085/product-client/products
```

### REST API (via API Gateway)

- **Get All Products** (JSON)
```shell
GET http://localhost:8763/api-gateway-service/api/products
```

## Features

- **Product Display**: Displays products fetched from product-service
- **Service Discovery**: Uses Eureka to discover services
- **API Gateway Integration**: Routes requests through API Gateway
- **Error Handling**: Graceful error handling when services are unavailable

## References

# Author

- Rohtash Lakra
