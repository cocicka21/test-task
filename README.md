# test-task
## Станкевич Артур

### Инструкция по запуску:

#### Скачать проект и выполнить команду в командной строке в корневой директории проекта
```
docker compose up -d
```
#### После старта документация Swagger(Open Api) будет доступна по [сслыке](http://localhost:8090/api/swagger-ui/index.html)

#### Для логирования использовал Elasticsearch
#### Кэша нет
#### Имеются 3 пользователя из миграии:
##### [имя, пароль, email, телефон]
##### [Roman, qwerty, Roman@example.com, 79207865432]
##### [Igor, asdfgh, Igor@example.com, 79317167854]
##### [Anna, zxcvbn, Anna@example.com, 79418976543]

### Достаточно залогиниться по email+password("/auth/login/email"), либо по phone+password("/auth/login/phone") получнныей токен ввести в Swagger по кнопке "Authorize", что бы получить доступ к апи
#### Апи достпуные без авторизации:
#### - GET - /users, /users/{userId}
#### - POST - /auth/login/phone, /auth/login/email