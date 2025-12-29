# product-service

Product management service providing REST API and admin UI for managing products.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Cloud**: 2024.0.0
- **Java**: 21
- **Spring Data JPA**: Database persistence
- **H2 Database**: In-memory database (file-based)
- **Liquibase**: Database migration tool
- **Thymeleaf**: Server-side templating for admin UI
- **Netflix Eureka Client**: Service discovery and registration

## Service Dependencies

- **eureka-service** (required) - Must be running for service registration
- **config-service** (optional) - For centralized configuration management

## Startup Order

**5. Start after eureka-service (and optionally config-service)**

```bash
# 1. First start eureka-service
cd ../eureka-service
./buildMaven.sh && ./runMaven.sh

# 2. Optionally start config-service
cd ../config-service
./buildMaven.sh && ./runMaven.sh

# 3. Then start product-service
cd ../product-service
./buildMaven.sh
./runMaven.sh
```

## Configuration

- **Default Port**: 8084
- **Service Name**: product-service
- **Context Path**: /product-service
- **Base URL**: http://localhost:8084/product-service
- **Eureka Registration**: Registered with eureka-service at http://localhost:8761/

## Endpoints

### REST API Endpoints

- **Get All Products** (JSON)
```shell
GET http://localhost:8084/product-service/api/products
```

- **Get Product by ID** (JSON)
```shell
GET http://localhost:8084/product-service/api/products/{id}
```

### Admin UI Endpoints

- **Home Page**
```shell
http://localhost:8084/product-service/
```

- **Product List**
```shell
http://localhost:8084/product-service/products
```

- **Create Product**
```shell
http://localhost:8084/product-service/products/new
```

- **Edit Product**
```shell
http://localhost:8084/product-service/products/{id}/edit
```

- **Upload Products (CSV)**
```shell
http://localhost:8084/product-service/products/upload
```

- **Download Products (CSV)**
```shell
GET http://localhost:8084/product-service/products/download
```

- **H2 Database Console**
```shell
http://localhost:8084/product-service/h2
```

## Features

- **Product Management**: CRUD operations for products
- **CSV Import/Export**: Bulk import and export of products via CSV
- **Admin UI**: Web-based interface for managing products
- **REST API**: JSON API for programmatic access
- **Database**: H2 file-based database with Liquibase migrations

## Database

- **Type**: H2 Database (file-based)
- **Location**: `~/Downloads/H2DB/ProductService`
- **Console**: http://localhost:8084/product-service/h2
- **Username**: sa
- **Password**: (empty)

## References

# Author

- Rohtash Lakra
