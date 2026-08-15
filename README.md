# Property View

RESTful API for managing hotels.

The application provides functionality for retrieving hotels, searching by different parameters, creating hotels, adding amenities and histograms.

## Technologies

* Java 21
* Spring Boot 4
* Spring Web
* Spring Data JPA
* Hibernate
* Maven
* Liquibase
* H2
* PostgreSQL
* Swagger / OpenAPI
* JUnit 5
* MockMvc

## Requirements

* Java 21+
* Maven

## Running the Application

By default, the application uses an H2 database.

Run the application with:

```bash
mvn spring-boot:run
```

The application will be available at:

```text
http://localhost:8092
```

The application runs on port `8092`.

## Swagger

Swagger UI:

```text
http://localhost:8092/swagger-ui/index.html
```

## API Endpoints

All endpoints use the following common prefix:

```text
/property-view
```

### Get All Hotels

```http
GET /property-view/hotels
```

Returns a list of all hotels with short information.

### Get Hotel by ID

```http
GET /property-view/hotels/{id}
```

Returns detailed information about a specific hotel.

### Search Hotels

```http
GET /property-view/search
```

Supported parameters:

* `name`
* `brand`
* `city`
* `country`
* `amenities`

Example:

```http
GET /property-view/search?city=Minsk
```

### Create Hotel

```http
POST /property-view/hotels
```

Example request:

```json
{
  "name": "DoubleTree by Hilton Minsk",
  "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...",
  "brand": "Hilton",
  "address": {
    "houseNumber": 9,
    "street": "Pobediteley Avenue",
    "city": "Minsk",
    "country": "Belarus",
    "postCode": "220004"
  },
  "contacts": {
    "phone": "+375 17 309-80-00",
    "email": "doubletreeminsk.info@hilton.com"
  },
  "arrivalTime": {
    "checkIn": "14:00",
    "checkOut": "12:00"
  }
}
```

### Add Amenities

```http
POST /property-view/hotels/{id}/amenities
```

Example request:

```json
[
  "Free parking",
  "Free WiFi",
  "Fitness center"
]
```

### Get Hotel Histogram

```http
GET /property-view/histogram/{param}
```

Supported parameters:

* `brand`
* `city`
* `country`
* `amenities`

Example:

```http
GET /property-view/histogram/city
```

Example response:

```json
{
  "Minsk": 2,
  "Mogilev": 1
}
```

## Database

Liquibase is used for database schema management and migrations.

The application supports both H2 and PostgreSQL databases.

### H2

H2 is used by default.

Run the application with:

```bash
mvn spring-boot:run
```

Liquibase automatically creates and updates the required database schema.

### PostgreSQL

The application can also be run with PostgreSQL.

Create a PostgreSQL database named:

```text
propertyview
```

The default PostgreSQL connection settings are:

```text
URL: jdbc:postgresql://localhost:5432/propertyview
Username: postgres
```

The PostgreSQL password is provided through the `DB_PASSWORD` environment variable.

#### Windows PowerShell

Set the database password:

```powershell
$env:DB_PASSWORD="your_password"
```

Then run the application with the PostgreSQL profile:

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=postgres"
```

Liquibase automatically creates and updates the required database schema when the application starts.

## Testing

Run all tests with:

```bash
mvn clean test
```

The project uses:

* JUnit 5
* Spring Boot Test
* MockMvc

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/propertyView/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── repository/
│   │       └── service/
│   │
│   └── resources/
│       ├── application.properties
│       ├── application-h2.properties
│       ├── application-postgres.properties
│       └── db/
│           └── changelog/
│
└── test/
    └── java/
        └── com/example/propertyView/
```

The application follows a layered architecture.
