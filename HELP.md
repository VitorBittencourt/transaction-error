# Read Me First

When upgrading versions the TransactionManager vanishes

```
ERROR o.s.b.a.w.r.e.AbstractErrorWebExceptionHandler - [5fbfa122-1]  500 Server Error for HTTP POST "/foo/save/123"
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.transaction.TransactionManager' available
```

Original working versions:

```xml

<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.5.5</version>
```

```xml

<groupId>com.google.cloud</groupId>
<artifactId>spring-cloud-gcp-dependencies</artifactId>
<version>2.0.4</version>
```

Tried with some later versions at different times. Nothing worked. Latest attempt was:

```xml

<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.6.12</version>
```

```xml

<groupId>com.google.cloud</groupId>
<artifactId>spring-cloud-gcp-dependencies</artifactId>
<version>3.3.0</version>
```

To test, just start the app (I'm using firestore emulator, but same error was first seen in the real thing) and send a
POST request to localhost:8080/foo/save/123

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/#build-image)

