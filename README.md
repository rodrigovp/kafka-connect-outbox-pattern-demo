# Kafka Connect Outbox Pattern Demo

This project shows how to reliably publish events from a database to Kafka using the Outbox Pattern with Debezium and Kafka Connect.

🇧🇷 [Read this in Portuguese](README.pt-BR.md)

---

## What problem does this solve?

In distributed systems, it's common to face issues like:

- Data is saved in the database, but no event is published
- Events are published, but the database transaction fails
- Systems get out of sync

---

## How does it work?

Instead of sending events directly to Kafka, the application writes them to an outbox table inside the database.

Then:

1. Debezium captures changes from the database (CDC)
2. Kafka Connect publishes those events to Kafka

---

## Architecture (simplified)

Application -> Database (outbox table) -> Debezium -> Kafka Connect -> Kafka

---

## How to run

docker-compose up -d

Wait a few seconds until everything is up.

---

## How to test

1. Insert a record into the outbox table
2. Consume messages from Kafka
3. Check if the event appears

---

## Tests

This project includes integration tests that validate the full flow.

---

## Common issues

- PostgreSQL not configured for CDC
- Kafka Connect not ready yet
- Wrong connector configuration

---

## Learn more

If you want to go deeper into Kafka, event modeling and best practices:

- Kafka Connect e Kafka Streams — integre suas aplicações ao Kafka usando as melhores práticas  
  (Portuguese edition — available on [Amazon](https://www.amazon.com.br/Kafka-Connect-Streams-aplicações-melhores-ebook/dp/B0D4R7N7SR) and [Casa do Código](https://www.casadocodigo.com.br/products/livro-kafka-connect-e-kafka-streams))

- Kafka e Schema Registry — modelagem de eventos com robustez e segurança  
  (Portuguese edition — available on [Amazon](https://www.amazon.com.br/Kafka-Schema-Registry-Modelagem-segurança-ebook/dp/B0FKDDMFPG) and [Casa do Código](https://www.casadocodigo.com.br/products/livro-kafka-schema-registry))
