# Building microservices with spring-boot
---

![spring-microservices](/spring-microservices.png)

## SpringBootAdmin
---
Managing and monitoring Spring Boot applications. Each client application have to registers to the admin server.

### Admin Server Setup

we need to create a simple Spring Boot web application and also add the following Maven dependency:

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.2.2</version>
</dependency>
```

`@EnableAdminServe` have to configure in to the main class, as below:

```java
@SpringBootApplication
@EnableAdminServer
public class AdminserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminserverApplication.class, args);
	}
}

```
### Client App Setup

client spring-boot application must add the following maven dependency:

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.2.2</version>
</dependency>
```

we need to configure the client to know about the admin server's base URL.

```yml
spring:
  application:
    name: person-app      
  boot:
      admin:
        client:
          #spring-boot-admin-url
          url: http://localhost:9090/
          instance:
            service-base-url: http://localhost:8081/
management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      exposure:
        include: '*'	              
```

## Swagger Doc
---
`swagger-doc` is a spring-boot application to centralized the documentation server. Original Soruce [microservice-patterns](https://github.com/hellosatish/microservice-patterns/tree/master/centralized-swagger-docs).

### Configuration:
In the **application.yaml** file, you have to configure for all of microservices as below.


```yml
documentation: 
  baseurl: http://localhost
  swagger: 
    services:   
      - 
        name: employee-app
        url: http://localhost:8081/employee/v2/api-docs
        version: 2.0
      - 
        name: person-app
        url: http://localhost:8082/person/v2/api-docs
        version: 2.0
```

## Netflix Zuul
---

Rate limiting is used to control the amount of incoming and outgoing traffic to or from a network. 
[Spring Cloud Netflix Zull](https://github.com/spring-cloud/spring-cloud-netflix) is an open source gateway for spring-boot aplication. `Redis` is used as a datastorage.


For maven dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
<dependency>
    <groupId>com.marcosbarbero.cloud</groupId>
    <artifactId>spring-cloud-zuul-ratelimit</artifactId>
    <version>2.2.0.RELEASE</version>
</dependency>
```

configuration for `Zuul` properties in `application.yaml`. Example :

```yml
#micro-service-list
employee-app:
  host: localhost
  port: 8081
  basepath: employee
person-app:
  host: localhost
  port: 8082
  basepath: person

#rete limit policy
zuul:
  routes:
    employee-health-check:
      url: http://${employee-app.host}:${employee-app.port}
      path: /${employee-app.basepath}/health-check
    employee-api:
      url: http://${employee-app.host}:${employee-app.port}
      path: /${employee-app.basepath}/**
      stripPrefix: false
    person-api:
      url: http://${person-app.host}:${person-app.port}
      path: /${person-app.basepath}/health-check
  ratelimit:
    enabled: true
    repository: redis
    policy-list:
      employee-health-check:
        - limit: 1
          refresh-interval: 3
          type:
            - origin
  strip-prefix: true  
```

According to above configuration, you can access `employee/health-check` url only one time within three seconds. If you acess two or more time within three seconds, you will get error response like `too many request...`.







