# Стриминговый сервис "Musicman"

Проект стримингового музыкального сервиса, разработанный специально для предмета "Технологии программирования".

## Особенности приложения
1. React приложение
2. Наличие эквалайзера
3. Собственный алгоритм рекомендаций
4. Возможность рекомендовать композиции другим пользователям

## Разработано с использованием технологий
- [React.js](https://ru.reactjs.org/)
- [Effector.js](https://effector.dev/)
- [TypeScript](https://www.typescriptlang.org/)
- [MaterialUI](https://mui.com/)
- [Spring boot](https://spring.io/) 
- [Liquibase](https://www.liquibase.org/)

## Авторы
- [Анастасия Сашина](https://github.com/Arcturs) - бэкенд разработчик, автор документации;
- [Евгений Охрямкин](https://github.com/Eugene-Okhryamkin) - фронтенд разработчик, лидер команды;
- [Захар Казмиров](https://github.com/Neonvolt) - бизнес-аналитик, дизайнер.

## Описание проекта
Проект находится по адресу [www.musicman-vsu.online](https://www.musicman-vsu.online). Особенностями данного проекта является возможность рекомендации песни друзьям, просмотр рекомендованных треков, настройка звука с помощью эквалайзера.

### Запуск проекта
Чтобы запустить локально проект, необходимо установить Docker. Далее в командной строке, находясь в папке приложения, выполнить следующую команду:
```shell
docker-compose up -d
```
Документация бэкенда будет доступна по [localhost:8080](http://localhost:8080/api/v1/swagger-ui/index.html), фронтэнд по [localhost:3000](http://localhost:3000) и метрики по [localhost:3001](http://localhost:3001).
Далее вкратце представлено описание возможных проблем и их решение:
- Не поднимается база данных. В этом случае нужно проследовать в файл .env и поменять поле 'MUSICMAN_DATABASE_HOST' на адрес докер-машины (его можно выяснить с помощью команды ifconfig или ipconfig в зависимости от вашей ОС);
- Фронтенд не отправляет запросы. В папке фронтенда, в webapp находим package.json. Изменяем поле 'proxy' на адрес 'http://localhost:8080/api/v1', если вы тестируете локально.

### Роли для тестирования приложения:
- Администратор (логин - admin@mail.com, пароль - password);
- Пользователь (логин - pajobi7427@byorby.com, пароль - qwerty1234).

## Документация и полезные ссылки:

### Общие
Все диаграммы, ТЗ, курсовая работа, сопроводительное письмо находятся [здесь](documentation).
Полезные ссылки:
- [Официальный сайт www.musicman-vsu.online](https://www.musicman-vsu.online)
- [Trello](https://trello.com/b/y1g020zX/%D1%81%D1%82%D1%80%D0%B8%D0%BC%D0%B8%D0%BD%D0%B3%D0%BE%D0%B2%D1%8B%D0%B9-%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81)
- [Miro](https://miro.com/app/board/uXjVMaVV5qU=/?share_link_id=838823599808/)
- [Figma](https://www.figma.com/proto/lnKM9pm5HmxMi5NboduJrd/WebProject?page-id=0%3A1&node-id=2-2&viewport=551%2C207%2C0.61&scaling=scale-down)
- [Защита ТЗ](https://drive.google.com/file/d/1NdsEdDp1seJ7x68AnN0qodGPCHbLI0SQ/view?usp=sharing)
- [Презентация проекта](https://drive.google.com/file/d/14JYVUG-M_FETpuYLdn2UnzRyDk0uuzIl/view?usp=sharing)

### Серверная часть
Подробности разворачивания бэкенд части приложения в [readme](backend/README.md) файле.
Полезные ссылки:
- [Swagger](https://app.swaggerhub.com/apis/YANINASTYA2010/MusicmanAPI/1.0.11)
- [Метрики](https://www.musicman-vsu.online/metrics/d/ab9ac4ba-edae-4dd9-9424-dc9c0720c90f/jvm-micrometer?orgId=1&refresh=30s)
- [Организация кода на бэкенде](https://drive.google.com/file/d/1kDUXixqFKNxApIXAUZx676jLM7nFlCOo/view?usp=sharing)
- [Деплой приложения](https://drive.google.com/file/d/1Crbc_Z9vKYQJz-t0aVrGGzB0G7OdLY8r/view?usp=drive_link)

### Клиентская часть
Подробности разворачивания фронтэнд части приложения в [readme](frontend/README.md) файле.
- [Организация кода на фронтенде](https://vk.com/away.php?to=https%3A%2F%2Fdrive.google.com%2Ffile%2Fd%2F1LP367WfTLUUF5OdwnAMZdkuBzRKBESv1%2Fview%3Fusp%3Ddrive_link&cc_key=)
- [Демонстрация приложения](https://vk.com/away.php?to=https%3A%2F%2Fdrive.google.com%2Ffile%2Fd%2F1LP367WfTLUUF5OdwnAMZdkuBzRKBESv1%2Fview%3Fusp%3Ddrive_link&cc_key=)