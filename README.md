# КОМАНДИ ДЛЯ ПОСТМЕН
1. Реєстрація:
   URL: http://localhost:8080/auth/register
   Method: POST
   JSON:
   {
   "email": "test@example.com",
   "password": "password123",
   "roles": ["USER"] (або "ADMIN")
   }
2. Логін:
   URL: http://localhost:8080/auth/login
   Method: POST
   JSON:
   {
   "email": "test@example.com",
   "password": "password123",
   }
3. Логаут:
   URL: http://localhost:8080/auth/logout
   Method: POST
   Headers:
   Authorization: Bearer (accessToken)
4. Отримання списку користувачів (тільки ADMIN):
   URL: http://localhost:8080/users
   Method: GET
   Headers:
   Authorization: Bearer (accessToken)
5. Фільтрація(через пошук по email, ролі)
   URL: http://localhost:8080/users/filter?email=test@example.com (по email)
   URL: http://localhost:8080/users/filter?role=ADMIN (по ролі)
   Method: GET
   Headers:
   Authorization: Bearer (accessToken)
6. Отримання користувача за id:
   URL: http://localhost:8080/users/1
   Method: GET
   Headers:
      Authorization: Bearer (accessToken)
7. Оновлення користувача:
   URL: http://localhost:8080/users/update
   Method: PUT
   Headers:
      Authorization: Bearer (accessToken)
8. Видалення користувача:
   URL: http://localhost:8080/users/delete
   Method: DELETE
   Headers:
   Authorization: Bearer (accessToken)
9. Створення посту(тільки користувач):
   URL: http://localhost:8080/posts
   Method: POST
   Headers:
   Authorization: Bearer (accessToken)
10. Показати всі пости:
    URL: http://localhost:8080/posts
    Method: GET
11. Отримання посту за id:
    URL: http://localhost:8080/posts/1
    Method: GET
12. Оновлення посту:
    URL: http://localhost:8080/posts/1
    Method: PUT
    Headers:
    Authorization: Bearer (accessToken)
13. Видалення посту:
    URL: http://localhost:8080/posts/1
    Method: DELETE
    Headers:
    Authorization: Bearer (accessToken)



# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/maven-plugin/build-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.2/specification/configuration-metadata/annotation-processor.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.2/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

