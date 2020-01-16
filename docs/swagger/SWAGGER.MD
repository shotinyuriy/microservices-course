# How  to add swagger to a Spring-Boot project

## 1 
Add a maven / gradle dependency 
```xml
<spring-plugin.version>2.0.0.RELEASE</spring-plugin.version>

<dependency>
    <groupId>org.springframework.plugin</groupId>
    <artifactId>spring-plugin-core</artifactId>
    <version>${spring-plugin.version}</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.6.1</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.6.1</version>
</dependency>
```
```
compile 'org.springframework.plugin:spring-plugin-core:2.0.0.RELEASE'
compile 'io.springfox:springfox-swagger2:2.6.1'
compile 'io.springfox:springfox-swagger-ui:2.6.1'
```
## 2 
Create a SwaggerConfiguration component annotated with @Configuration and @EnableSwagger2
## 3 
Create a Docket bean inside this configuration
```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
					"currency-exchange-service", "Currency Exchange API Info", "1.0",
					"https://google.com", new Contact("Yuriy Shotin", "https://google.com", "shotinyuriy@gmail.com"),
					"GNU GPL License", "https://google.com");
	
	private final static Set<String> DEFAULT_PRODUCES_AND_CONSUMES  = new HashSet<>(
			Arrays.asList("application/json", "application/xml")
			);
	
	@Bean
	public Docket api() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(DEFAULT_API_INFO);
		docket.produces(DEFAULT_PRODUCES_AND_CONSUMES);
		docket.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
		return docket;
	}
}
```
## 4
Usually swagger has conflicts with Spring Boot application so we need to create 4 more components to fix it
* DocumentationPluginsManagerReplaced
* DuplicateGroupsDetector
* OverrideSwaggerClasses
* TypeNameExtractorReplaced

## 5
URLs for Swagger

### Swagger GET /v2/api-docs
http://{current-service-url}/v2/api-docs
### Swagger UI
http://{current-service-url}/swagger-ui.html
