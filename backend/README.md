# Бэкенд
В данном репозитории представлен исходный код для проекта стримингового сервиса Musicman.

## Использованные технологии:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html);
- [Maven](https://maven.apache.org/download.cgi) последняя версия;
- [Spring boot](https://spring.io/projects/spring-boot);
- [PostgreSQL](https://www.postgresql.org/);
- [Liquibase](https://www.liquibase.org/);
- [Spring security](https://spring.io/projects/spring-security);
- [Json web tokens](https://jwt.io/);
- [Swagger](https://swagger.io/) документация;
- [Docker](https://www.docker.com/).

## Запуск приложения
### Необходимые технологии:
Для запуска приложения понадобится только докер.

### Запуск
В папке приложения musicman переместиться в backend. В терминале запустить следующую команду:
```
docker-compose up -d
```

## Документация
Чтобы просмотреть все эндпоинты приложения, необходимо перейти по ссылке: http://localhost:8080/api/v1/swagger-ui/index.html.