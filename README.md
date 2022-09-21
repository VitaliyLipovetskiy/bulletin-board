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

РАЗВЕРТЫВАНИЕ
=============

Создать образ Docker <br>
docker build --tag bulletin-board .
<br>

Запустить образ (внутри контейнера открываем 8081 порт)<br>
docker run --publish 8080:8081  bulletin-board

**_Swagger_**

http://localhost:8080/swagger-ui/index.html


**_Регистрация нового пользователя с ролью ADMIN_**

POST http://localhost:8081/api/users <br>
Content-Type: application/json;charset=UTF-8

{
"role": "ADMIN",
"email":"admin_1@gmail.com",
"password":"admin"
}

**_Получить пользователя с id=2_**

GET http://localhost:8081/api/users/2 <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // login: admin@gmail.com password: admin

**_Получить пользователя с id=1_**

GET http://localhost:8081/api/users/1 <br>
Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=   // login: user@yandex.ru password: password

**_Исправить пользователя_**

PUT http://localhost:8081/api/users <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // login: admin@gmail.com password: admin <br>
Content-Type: application/json;charset=UTF-8

{
"role": "ADMIN",
"email":"admin@gmail.com",
"password":"admin123"
}

**_Включить(отключить) пользователя_**

PATCH http://localhost:8081/api/users/2?enabled=true <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu    // login: admin@gmail.com password: admin <br>
Content-Type: application/json;charset=UTF-8

**_Получить все свои объявления_**

GET http://localhost:8081/api/ads
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu       // Admin id=2<br>
#Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=   // User id=1

**_Получить объявления согласно фильтру_**

Варианты: "name","description","contact","email" - по подстроке<br>
"enabled=true" - только активные, "enabled=false" - только снятые, иначе по enabled не фильтруется

GET http://localhost:8081/api/ads/filter?email=adm <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu       // Admin id=2

**_Получить объявление с номером 1_**

GET http://localhost:8081/api/ads/1 <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu

**_Создать новое объявление_**

POST http://localhost:8081/api/ads <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu <br>
Content-Type: application/json;charset=UTF-8

{
"name": "Объявление",
"description": "Продам",
"contact": "Я",
"image": "нет"
}

**_Создать новое активное объявление_**

POST http://localhost:8081/api/ads <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu <br>
Content-Type: application/json;charset=UTF-8

{
"name": "ADMIN",
"description": "admin1@gmail.com",
"contact": "admin",
"image": "admin",
"enabled": true
}

**_Удалить свое объявление 1_**

DELETE http://localhost:8081/api/ads/1  <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu

**_Исправить свое объявление 2_**

PUT http://localhost:8081/api/ads/2 <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // Admin id=3 <br>
Content-Type: application/json;charset=UTF-8

{
"id": 2,
"name": "user",
"description":"admin1@gmail.com",
"contact":"admin",
"image":"qqqq"
}

**_Сделать объявление активное_**

PATCH http://localhost:8081/api/ads/2?enabled=true  <br>
Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu   // Admin id=2


Остальное не успел((




