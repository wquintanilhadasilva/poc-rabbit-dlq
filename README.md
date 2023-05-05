# Getting Started

## Mensagem NÃO CONSUMIDA por ninguém:
```
curl --request POST \
--url http://localhost:8081/api/message \
--header 'Content-Type: application/json' \
--data '{
"mensagem":"Mensagem assíncrona",
"status": 3
}'
```

## Mensagem consumida pelo processador:
```
curl --request POST \
--url http://localhost:8081/api/message \
--header 'Content-Type: application/json' \
--data '{
"mensagem":"Mensagem assíncrona",
"status": 1
}'
```

## Mensagem consumida pelo DLQ:
```
curl --request POST \
--url http://localhost:8081/api/message \
--header 'Content-Type: application/json' \
--data '{
"mensagem":"Mensagem assíncrona",
"status": 2
}'
```

```
curl --request POST \
--url http://localhost:8081/api/message \
--header 'Content-Type: application/json' \
--data '{
"mensagem":"Mensagem assíncrona",
"status": 0
}'
```