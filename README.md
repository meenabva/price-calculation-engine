# Pricing Engine

A Spring Boot service for calculating product prices with a base price, configured pricing drivers, or AI-suggested drivers. Price calculations can be requested asynchronously through Kafka. Results are recorded in a SQLite database.

## Requirements

- Java 21
- Apache Kafka available at `localhost:9092`
- Ollama running locally with the model used for pricing recommendations available

The Maven wrapper is included, so a separate Maven installation is not required.

## Run locally

Start Kafka on `localhost:9092`, then run the application from the project root:

```bash
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd` instead of `./mvnw`:

```powershell
.\mvnw.cmd spring-boot:run
```

The service starts on Spring Boot's default port, `8080`. SQLite data is stored in `pricing_data.db` in the working directory. Hibernate updates the schema on startup and `src/main/resources/data.sql` inserts sample products and pricing configurations.

## API

### Submit an asynchronous price calculation

```http
POST /api/calculate-price
Content-Type: application/json
```

```json
{
  "productId": 1,
  "metadata": {
    "strategy": "BASE"
  }
}
```

The request is published to the Kafka topic `price-requests` and processed by a consumer in the `pricing-group` group. The endpoint currently returns an empty response; the calculated result is written to the application logs and saved as a pricing audit record.

Set `metadata.strategy` to one of:

| Strategy | Behavior |
| --- | --- |
| `BASE` | Uses the product's base price. |
| `MULTI_DRIVER` | Adds the configured driver impacts for the product type. |
| `AI` | Gets driver recommendations from Gemini and adds their impacts. |

Strategy names are case-insensitive. The seeded product IDs are `1` (Widget A, `STANDARD`), `2` (Gadget B, `PREMIUM`), and `3` (T-Shirt, `APPAREL`).

### Request AI pricing advice

```http
GET /api/ai?productId=1
```

This endpoint calls the configured AI model and returns recommended pricing drivers and weightages.

## Configuration

Defaults are in `src/main/resources/application.properties`:

- SQLite URL: `jdbc:sqlite:pricing_data.db`
- Kafka broker: `localhost:9092`
- Kafka topic: `price-requests`

The `AI` strategy gets pricing recommendations from a locally hosted Ollama model through Spring AI. Start Ollama and make the configured model available before calling `/api/ai` or submitting a request with the `AI` strategy. Configure the Ollama connection and model for your environment using Spring AI's Ollama settings.

## Current implementation notes

The driver calculators (`TAX`, `DISCOUNT`, `COMMISSION`, `LOGISTICS`, and `SURCHARGE`) currently return fixed placeholder amounts. Their calculation logic does not yet use the supplied context or weightage, so multi-driver and AI results are illustrative rather than production pricing.

**Dependency note:** The current `pom.xml` declares Spring AI's Vertex AI Gemini starter, while the recommendation workflow is intended to use local Ollama. Align the Spring AI starter dependency with Ollama if setting up this repository from a clean checkout.
