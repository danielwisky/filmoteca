# API Filmoteca

A filmoteca é uma API de filmes, criada com Spring Boot utilizando um banco de dados NoSQL MongoDB.

Através da filmoteca você pode criar e buscar filmes, utilizando como opção de filtro o nível de censura.

Todas as mensagens de erro são retornadas no padrão:

```
{
  "errors": [
    "elenco: não pode ser nulo",
    "nome: não pode estar em branco"
  ]
}
```

## Tecnologias

* [Java 13](https://www.java.com/pt_BR/)
* [Spring Boot 2.2.6](http://spring.io/projects/spring-boot)
* [MongoDB](https://www.mongodb.com/) NoSQL Database
* [SpringFox](http://springfox.github.io/springfox/) API com Swagger
* [Docker](https://www.docker.com/)

## Requisitos para executar a filmoteca

* [Java 13](https://www.java.com/pt_BR/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

Gerar o pacote `jar` da filmoteca com o maven `mvn`:

```
mvn package
```

Deploy da filmoteca usando o docker compose

```
docker-compose up
```

Quando filmoteca estiver disponível, você pode navegar através do link: [Swagger](http://localhost:8080/swagger-ui.html)

Documentação do Swagger está disponível através do link: [API Docs](http://localhost:8080/v2/api-docs)