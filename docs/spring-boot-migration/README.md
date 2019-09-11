# Migrate to Srping Boot

## Add Spring Boot Parent to POM or Gradle

## Remove spring core from the dependencies
## Replace All Spring Dependencies with Spring Boot Starters
 1. spring-core - spring-boot-parent
 1. spring-web - spring-boot-starter-web
 1. spring-web-mvc - spring-boot-starter-web
 1. spring-orm - spring-boot-started-data-jpa
 1. spring-tx - spring-boot-started-data-jpa
 1. spring-test - spring-boot-starter-test
### Remove Unnecessary Dependencies
 1. jackson-databind - is not needed
 1. jackson-dataformat-xml - is not needed
 1. validation-api - is not needed
 1. hibernate-core - is not needed
 1. hibernate-entitymanager - is not needed

## Create Spring Boot Main Class
```java
package com.gridu.microservice.taxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("file:src/main/webapp/config/application-context.xml")
public class TaxesCalculationApplication {

	public static void main(String[] args) {

		SpringApplication.run(TaxesCalculationApplication.class, args);
	}
}
```

## Add Spring Could Version
### Maven
```xml
<properties>
  <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
</properties>
```
### Gradle
```groovy
ext {
	set('springCloudVersion', "Greenwich.SR2")
}
```
## Add Spring Cloud Eureka Starter
### Maven
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<dependency>
  <groupId>javax.inject</groupId>
  <artifactId>javax.inject</artifactId>
  <version>1</version>
</dependency>
```
### Gradle
```groovy
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
}

```
## Add Spring Cloud Dependency Management
### Maven
```xml
<dependencyManagement>
<dependencies>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>${spring-cloud.version}</version>
    <type>pom</type>
    <scope>import</scope>
  </dependency>
</dependencies>
</dependencyManagement>
```
### Gradle
```groovy
dependencyManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
	}
}
```