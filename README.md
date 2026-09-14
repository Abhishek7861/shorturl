A full-stack URL shortening application built with **Spring Boot**, **MongoDB**, and **React**.

The application allows users to:

- Convert a long URL into a compact short URL.
- Open a short URL and get redirected to the original URL.
- Check how many times a short URL has been accessed.
- View the original long URL associated with a short URL.

## Architecture
<img width="2824" height="562" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/33fc337b-4ee6-4b79-924c-2632629552cd" />


## Tech Stack

### Backend

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Data MongoDB
- MongoDB
- Maven
- Lombok
- JUnit / Spring Boot Test

### Frontend

- React 18
- React Router 6
- Axios
- Create React App
- CSS

## Project Structure

```text
shorturl/
├── shorturl-be/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/myshorturl/shorturl/
│   │   │   │   ├── controller/
│   │   │   │   │   └── ShortUrlController.java
│   │   │   │   ├── exceptionhandler/
│   │   │   │   │   ├── ErrorMessageDto.java
│   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── CreateUrlDto.java
│   │   │   │   │   ├── UrlModel.java
│   │   │   │   │   └── UrlResponseDto.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── UrlRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   └── UrlService.java
│   │   │   │   └── utils/
│   │   │   │       ├── GenerateHash.java
│   │   │   │       └── ValidateUrl.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── ...
│   └── pom.xml
│
└── shorturl-fe/
    ├── public/
    ├── src/
    │   ├── api/
    │   │   ├── axiosConfig.js
    │   │   ├── get.js
    │   │   └── post.js
    │   ├── components/
    │   │   ├── Footer/
    │   │   ├── ShortUrlHit/
    │   │   ├── TitleHeading/
    │   │   └── UrlBox/
    │   ├── pages/
    │   │   ├── error/
    │   │   └── home/
    │   ├── App.js
    │   └── index.js
    └── package.json
```

## How It Works

### 1. Create a Short URL

The frontend sends the original URL to the backend:

```http
POST /
Content-Type: application/json

{
  "longUrl": "https://example.com/some/long/path"
}
```

The backend:

1. Validates the supplied URL.
2. Generates a SHA-1 hash.
3. Uses the first 10 hexadecimal characters of the hash as the short URL identifier.
4. Checks MongoDB for an existing identifier.
5. Handles collisions by modifying the hash input and generating another identifier.
6. Stores the URL mapping in MongoDB.
7. Returns the generated short identifier.

Example response:

```json
{
  "id": "a1b2c3d4e5",
  "shortUrl": "a1b2c3d4e5",
  "longUrl": "https://example.com/some/long/path",
  "urlHit": 0
}
```

The frontend combines the identifier with the configured backend URL to display the complete short URL.

### 2. Redirect Using the Short URL

When a user opens:

```http
GET /a1b2c3d4e5
```

the backend:

1. Looks up the identifier in MongoDB.
2. Increments the hit counter.
3. Saves the updated document.
4. Returns a `307 Temporary Redirect` with the original URL in the `Location` header.

```text
Browser
   |
   | GET /a1b2c3d4e5
   v
Spring Boot
   |
   | MongoDB lookup
   v
Original URL
   |
   | 307 Redirect
   v
Destination Website
```

### 3. Get URL Information

The application provides an information endpoint:

```http
GET /{shortUrl}/info
```

Example:

```http
GET /a1b2c3d4e5/info
```

Response:

```json
{
  "id": "a1b2c3d4e5",
  "shortUrl": "a1b2c3d4e5",
  "longUrl": "https://example.com/some/long/path",
  "urlHit": 5
}
```

The React frontend uses this endpoint to display the hit count and original URL.

## API Reference

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/` | Create a short URL |
| `GET` | `/{shortUrl}` | Redirect to the original URL |
| `GET` | `/{shortUrl}/info` | Get URL details and hit count |

### Create Short URL

**Request**

```http
POST /
```

```json
{
  "longUrl": "https://www.example.com"
}
```

**Success**

```http
201 Created
```

```json
{
  "id": "xxxxxxxxxx",
  "shortUrl": "xxxxxxxxxx",
  "longUrl": "https://www.example.com",
  "urlHit": 0
}
```

### Invalid URL

The backend returns a `400 Bad Request` when URL validation fails.

```json
{
  "message": "Invalid url Syntax"
}
```

### Unknown Short URL

Requests for a short URL that does not exist also result in `400 Bad Request` with an error message.

## Hash Generation

Short identifiers are generated using SHA-1:

```text
long URL
   |
   v
SHA-1
   |
   v
hexadecimal hash
   |
   v
first 10 characters
   |
   v
short identifier
```

For example:

```text
https://example.com
        |
        v
SHA-1 hash
        |
        v
a1b2c3d4e5........
        |
        v
a1b2c3d4e5
```

The implementation intentionally keeps the short identifier to **10 hexadecimal characters**.

### Collision Handling

If the generated identifier already exists for another long URL, the service changes the hash input and tries again.

If the identifier already belongs to the same long URL, the existing identifier is reused.

This gives the application deterministic short URLs for the same input URL while attempting to avoid hash-prefix collisions.

## MongoDB Data Model

URLs are stored in the `shortUrl` collection.

```text
shortUrl
├── _id
├── shortUrl
├── longUrl
└── urlHit
```

The corresponding Java model is:

```java
@Document(collection = "shortUrl")
public class UrlModel {
    @Id
    private final String id;
    private final String shortUrl;
    private final String longUrl;
    private int urlHit;
}
```

MongoDB configuration is currently defined in:

```text
shorturl-be/src/main/resources/application.properties
```

Default configuration:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=testdb
```

## Prerequisites

Install the following:

- Java 17+
- Maven 3.9+ (or use the included Maven Wrapper)
- Node.js and npm
- MongoDB

Make sure MongoDB is running locally on port `27017`.

## Running the Backend

Navigate to the backend:

```bash
cd shorturl-be
```

Using Maven Wrapper:

### macOS / Linux

```bash
./mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

The backend starts on:

```text
http://localhost:8080
```

You can also build the application:

```bash
./mvnw clean package
```

and run the generated JAR:

```bash
java -jar target/shorturl-0.0.1-SNAPSHOT.jar
```

## Running the Frontend

Navigate to the frontend:

```bash
cd shorturl-fe
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm start
```

The React application runs at:

```text
http://localhost:3000
```

The frontend currently configures the backend base URL as:

```text
http://localhost:8080
```

in:

```text
shorturl-fe/src/api/axiosConfig.js
```

## Frontend Flow

The home page contains two primary operations.

### URL Shortening

```text
User enters long URL
        |
        v
UrlBox
        |
        v
POST /
        |
        v
Spring Boot API
        |
        v
Short URL
        |
        v
Displayed to user
```

### URL Hit Lookup

```text
User enters short URL
        |
        v
ShortUrlHit
        |
        v
GET /{shortUrl}/info
        |
        v
Spring Boot API
        |
        v
MongoDB
        |
        v
Hit count + original URL
```

## Error Handling

The backend uses a global exception handler with Spring's `@ControllerAdvice`.

`GlobalExceptionHandler` handles `BadRequestException` and converts it into a structured response:

```json
{
  "message": "..."
}
```

This keeps error responses consistent across the API.

## Testing

The backend currently includes a Spring Boot context test:

```bash
cd shorturl-be
./mvnw test
```

The frontend is configured with React Testing Library and Jest through Create React App:

```bash
cd shorturl-fe
npm test
```

## Design Highlights

### Separation of Responsibilities

The backend follows a simple layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
MongoDB
```

Utility responsibilities are separated into:

- `GenerateHash` — short identifier generation
- `ValidateUrl` — URL syntax validation

### Repository Abstraction

`UrlRepository` extends Spring Data MongoDB's `MongoRepository`, providing persistence operations without requiring handwritten MongoDB queries.

A custom lookup is provided for short URLs:

```java
UrlModel findByShortUrl(String shortUrl);
```

### Deterministic Short URLs

The hash-based approach means the same long URL produces the same short identifier, provided no collision-resolution path is required.

## Current Limitations / Production Considerations

This project is a straightforward implementation intended for learning and demonstrating a URL-shortening service. Before deploying it at production scale, several areas should be improved.

### 1. Hash Collision Strategy

The application uses only the first 10 characters of a SHA-1 hash. Although this provides a compact identifier, collisions are theoretically possible.

A production implementation could use:

- Base62 encoding
- A database-generated numeric ID
- Random identifiers with uniqueness checks
- A dedicated ID-generation strategy

### 2. Atomic Hit Counter

The current redirect flow reads the document, increments `urlHit`, and saves it.

Under high concurrency, multiple requests could update the same document simultaneously and potentially lose increments.

A MongoDB atomic `$inc` operation would be more appropriate for a high-traffic deployment.

### 3. CORS

The frontend runs on port `3000` while the backend runs on port `8080`. A production deployment should explicitly configure CORS or serve both applications behind a common domain/reverse proxy.

### 4. Configuration

The MongoDB connection and frontend API URL are currently hard-coded/local-development configuration.

For production, use environment-specific configuration and secrets management.

### 5. URL Validation

The backend validates URL syntax, but production systems should consider additional security controls, such as protection against malicious destinations and abuse.

### 6. Authentication and Rate Limiting

The current application does not implement authentication, authorization, or rate limiting.

For a public URL-shortening service, rate limiting and abuse prevention would be important.

### 7. Observability

Production deployment would benefit from:

- Structured logging
- Metrics
- Distributed tracing
- Health checks
- Error monitoring

## Possible Future Improvements

- Custom aliases for short URLs
- URL expiration
- User accounts
- Authentication and authorization
- QR-code generation
- Click analytics
- Geographic/device analytics
- Rate limiting
- Redis caching
- Atomic hit-counter updates
- Base62 short-code generation
- Docker support
- CI/CD pipeline
- Cloud deployment
- API documentation with OpenAPI/Swagger
- Automated integration tests
- Custom domains

## Resume Description

A concise resume description for this project:

> **Short URL — URL Shortening Platform**  
> Built a full-stack URL shortening service using Spring Boot, MongoDB, and React. Implemented deterministic SHA-1 based short-code generation, collision handling, URL validation, HTTP redirects, click tracking, REST APIs, and a React-based frontend.

## License

No explicit license is currently defined in the repository.
