# Tax Calculation Microservice
## Web Api Creation
1. create a new maven project in IDE named taxes-calculation
2. create a pom.xml with dependencies for Spring and Jackson
3. create an application-context.xml file
4. create a web.xml file
5. create a new package com.gridu.microservices.taxes.model
6. create a new class com.gridu.microservices.taxes.model.TaxCategory
7. create a new class com.gridu.microservices.taxes.service.TaxCategoryService
8. create a new package com.gridu.microservices.taxes.rest
9. create a new class com.gridu.microservices.taxes.rest.TaxesCalculationRestResource
10. create method getTaxesCategories
11. test this service
12. create new classes TaxRule and State
13. create a new class com.gridu.microservices.taxes.service.StateService
14. create a new class com.gridu.microservices.taxes.service.TaxRuleService
15. create a data initializer class in Java script to save initial values using this services
16. create a new REST service getTaxesRules
17. test 2 getTaxCategories and getTaxRules
18. create a new REST service calculateTaxes
19. test this new service
## Add Persistence
1. update pom.xml with Hibernate and Java PostreSQL dependencies
2. install PostgeSQL
3. create a new database named tax_calculation in PostgreSQL
4. create tables for tax_category, tax_rule and state
5. update application-context with beans for JPA integration with PostgreSQL
6. add JPA annotations to integrate with the database
7. create a new REST service getTaxesRules
8. test 2 services
9. create a new REST service calculateTaxes
20. test this new service
# Product Catalog Microservice
# Shopping Cart Microservice
