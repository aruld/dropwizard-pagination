# DropWizard Pagination using Jersey Declarative Hyperlinking feature

Implemented DropWizard Pagination using [Jersey Declarative Hyperlinking](https://jersey.github.io/documentation/latest/declarative-linking.html).

Supports content negotiation using Accept headers. Uses Eclipse MOXy to support JAXB/JSON binding. 

[HTTP Link headers](http://tools.ietf.org/html/rfc5988#section-5) are added to responses using annotations.


![Alt text](/relative/path/to/screenshot.png?raw=true "Pagination")

How to start the Pagination application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/pagination-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`
4. To view the pagination, click this link `http://localhost:8080/items/view`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


