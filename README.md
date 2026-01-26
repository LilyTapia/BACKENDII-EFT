# BACKENDII-EFT — API REST con Seguridad, HATEOAS y OpenAPI

Proyecto backend desarrollado en **Java 17 + Spring Boot**, con foco en buenas prácticas de APIs REST:
**seguridad (Spring Security + JWT)**, **persistencia JPA**, **HATEOAS**, documentación **OpenAPI/Swagger** y
pruebas/cobertura con **JaCoCo**.

## Stack (confirmado por `pom.xml`)
- Spring Boot 3.x (Web + Actuator)
- Spring Security + JWT
- Spring Data JPA
- MySQL (runtime) y H2 (runtime)
- Spring HATEOAS
- OpenAPI / Swagger UI (springdoc)
- Validaciones (Bean Validation)
- Testing (Spring Boot Test / Security Test)
- Cobertura (JaCoCo)

## Documentación y evidencias incluidas
- `HATEOAS_DEMO.md` — demo de respuestas enriquecidas con enlaces
- `HATEOAS_IMPLEMENTATION_REPORT.md` — reporte de implementación HATEOAS
- `POSTMAN_TESTING_GUIDE.md` — guía de pruebas en Postman
- `JSON_CYCLICAL_REFERENCES_FIX.md` — solución a referencias cíclicas en JSON
- `COMPILATION_ISSUES_FIX.md` — solución de issues de compilación
- `SOLUTION_SUMMARY.md` — resumen de la solución y cambios

## Estructura del repositorio
- `src/` código fuente
- `data/` datos/recursos de apoyo
- `pom.xml` dependencias y configuración
- `.mvn/`, `mvnw*` wrapper Maven

## Cómo ejecutar (local)
```bash
./mvnw spring-boot:run
