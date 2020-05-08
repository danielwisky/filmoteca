# filmoteca

API de filmes

##### Tecnologias
* Java 13
* Spring Boot
* Spring Data MongoDB
* Docker

##### Gerando o pacote da filmoteca
```
mvn package
```

##### Criando a imagem docker da filmoteca
```
docker build -t danielwisky/filmoteca .
```

##### Deploy da filmoteca usando o docker compose
```
docker-compose up
```