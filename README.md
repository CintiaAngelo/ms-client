# ms-client

Microservico responsavel por expor os endpoints publicos do banco JAVER e consumir
o ms-storage via Feign Client.

## Tecnologias

- Java 21
- Spring Boot 4.0.3
- Spring Cloud OpenFeign
- Lombok
- JUnit 5 + Mockito + AssertJ

## Pre-requisitos

- Java 21+
- Maven 3.9+
- ms-storage rodando na porta 8081

## Como executar

Certifique-se de que o ms-storage esta rodando antes de subir este servico.
```bash
mvn spring-boot:run
```

O servico sobe na porta **8080**.

## Endpoints

### Criar cliente
```
POST /clientes
```
```json
{
  "nome": "Joao Silva",
  "telefone": 11999998888,
  "correntista": true,
  "scoreCredito": 750.0,
  "saldoCc": 2500.0
}
```

### Buscar por ID
```
GET /clientes/{id}
```

### Listar todos
```
GET /clientes
```

### Atualizar
```
PUT /clientes/{id}
```

### Deletar
```
DELETE /clientes/{id}
```

### Calcular score de credito
```
GET /clientes/{id}/score-credito
```

Formula: `saldo_cc * 0.1`

Exemplo de resposta:
```json
{
  "id": 1,
  "nome": "Joao Silva",
  "saldoCc": 2500.0,
  "scoreCalculado": 250.0
}
```

## Como testar
```bash
mvn test
```

## Arquitetura
```
Cliente HTTP
     |
  Controller
     |
  Service (interface + impl)
     |
  StorageClient (Feign)
     |
  ms-storage :8081
```

## Decisoes tecnicas

- Feign Client declarativo via interface anotada com @FeignClient, sem boilerplate de RestTemplate
- DTOs proprios desacoplados do ms-storage — cada servico e independente
- FeignException.NotFound capturada no Service e traduzida para ClienteNotFoundException
- Calculo do score de credito encapsulado no Service seguindo o principio de responsabilidade unica
- Testes unitarios isolados com Mockito sem subir contexto Spring
# ms-client
