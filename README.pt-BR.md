# Kafka Connect Outbox Pattern Demo

Este projeto mostra como publicar eventos de forma confiável do banco de dados para o Kafka usando o padrão Outbox com Debezium e Kafka Connect.

---

## Qual problema isso resolve?

Em sistemas distribuídos, é comum acontecer:

- Dados são salvos no banco, mas o evento não é publicado
- Evento é publicado, mas a transação falha
- Sistemas ficam inconsistentes

---

## Como funciona?

Em vez de enviar eventos direto para o Kafka, a aplicação escreve em uma tabela outbox no banco.

Depois:

1. O Debezium captura as mudanças (CDC)
2. O Kafka Connect publica no Kafka

---

## Arquitetura (simplificada)

Aplicação -> Banco (outbox) -> Debezium -> Kafka Connect -> Kafka

---

## Como rodar

```
docker-compose up -d
mvn -f app/pom.xml mvn spring-boot:start
```

---

## Como testar

1. Inserir um registro na tabela outbox
```
curl -X POST http://localhost:8080/orders
```

2. Verificar o registro no banco
```
docker exec -it postgres psql -U postgres -d orders -c "SELECT * FROM outbox;"
```

3. Verificar o evento no kafka
```
docker exec -it kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic dbserver1.public.outbox --from-beginning
```

---

## Aprofundamento

Se quiser aprender mais sobre Kafka e boas práticas:

- Kafka Connect e Kafka Streams - integre suas aplicações ao Kafka usando as melhores práticas
  ([Amazon](https://www.amazon.com.br/Kafka-Connect-Streams-aplicações-melhores-ebook/dp/B0D4R7N7SR) | [Casa do Código](https://www.casadocodigo.com.br/products/livro-kafka-connect-e-kafka-streams))

- Kafka e Schema Registry - modelagem de eventos com robustez e segurança
  ([Amazon](https://www.amazon.com.br/Kafka-Schema-Registry-Modelagem-segurança-ebook/dp/B0FKDDMFPG) | [Casa do Código](https://www.casadocodigo.com.br/products/livro-kafka-schema-registry))
