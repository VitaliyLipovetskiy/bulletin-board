Тестовое задание
================

Задание: разработать backend-сервер на Spring для функционирования доски объявлений.

Функционал, который требуется реализовать:
1. Регистрация и аутентификация пользователя в личном кабинете:
   - пользователь при регистрации должен указать роль, email и пароль; 
   - аутентификацию реализовать через вход по email и паролю. 
2. В личном кабинете пользователь может создать объявление и разместить его на доске объявлений в общем списке. Объявление содержит название, описание, контакты продавца и изображения. 
3. Доска объявлений в данном случае - это список всех объявлений с многочисленными фильтрами (продумать максимально возможные варианты фильтров на своё усмотрение), который отображается на главной странице сервиса.

4. Пользователь может как размещать свои объявления, так и совершать сделки в рамках других объявлений. 
5. Объявления имеют 2 статуса - активное и снятое с публикации. 
6. Продумать и реализовать вариант коммуникации между покупателем и продавцом во время совершения сделки. 
7. Для всех методов необходимо реализовать API-методы с документацией на Swagger. 
8. Покрыть весь функционал тестами. Желательно использовать TDD при разработке.

Дополнительные требования:
1. Сделать обертку исходного кода в docker-образ (добавить в корневую директорию файл Dockerfile, docker-compose.yml при необходимости). 
2. В readme файл разместить текст данного задания, а, также, инструкцию по развертыванию проекта и основные команды для запуска. 
3. Исходный код выложить на github.com в публичный репозиторий. 
4. При создании коммитов писать осмысленные названия. 
5. Использовать инструмент тестового покрытия для отображения % покрытия исходного кода тестами. 
6. Для проверки кода дополнительно подключить линтер на выбор.



**Регистрация нового пользователя с ролью ADMIN**

POST http://localhost:8081/api/users <br>
Content-Type: application/json;charset=UTF-8

{
"role": "ADMIN",
"email":"admin_1@gmail.com",
"password":"admin"
}

**Получить пользователя с id=2**

GET http://localhost:8081/api/users/2 <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // login: admin@gmail.com password: admin

**Получить пользователя с id=1**

GET http://localhost:8081/api/users/1 <br>
Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=   // login: user@yandex.ru password: password

**Исправить пользователя**

PUT http://localhost:8081/api/users <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // login: admin@gmail.com password: admin <br>
Content-Type: application/json;charset=UTF-8

{
"role": "ADMIN",
"email":"admin@gmail.com",
"password":"admin123"
}

**Включить(отключить) пользователя**

PATCH http://localhost:8081/api/users/2?enabled=true <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu    // login: admin@gmail.com password: admin <br>
Content-Type: application/json;charset=UTF-8



