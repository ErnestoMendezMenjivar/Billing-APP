# Billing-APP

La **Billing API** es una aplicación desarrollada en Spring Boot que proporciona servicios para la gestión de datos relacionados con facturación. La API utiliza MapStruct para el mapeo de objetos, Swagger para la documentación de la API y Angular para el desarrollo del frontend.

## Características principales

- **Spring Boot:** Utilizamos Spring Boot para crear una aplicación backend robusta y escalable.
- **MapStruct:** Se utiliza para realizar el mapeo entre objetos DTO (Data Transfer Objects) y entidades del dominio.
- **Swagger:** La API está documentada utilizando Swagger, lo que permite a los desarrolladores explorar y probar fácilmente los endpoints.
- **Angular:** El frontend se desarrolla utilizando Angular, proporcionando una interfaz de usuario moderna y receptiva.

## Funcionalidades

La API proporciona las siguientes funcionalidades:

- Creación, lectura, actualización y eliminación de datos relacionados con facturación.
- Manejo de excepciones para un manejo adecuado de errores.
- Seguridad con Spring Security para proteger los endpoints y autenticación básica para acceder a Swagger.

## Estructura del proyecto

El proyecto está organizado de la siguiente manera:

- **Controller:** Contiene los controladores que manejan las peticiones HTTP entrantes.
- **Entity:** Define las entidades del dominio que representan los datos almacenados en la base de datos.
- **Repository:** Contiene interfaces que extienden JpaRepository para realizar operaciones de base de datos.
- **Service:** Contiene la lógica de negocio de la aplicación.
- **Exception:** Maneja las excepciones lanzadas por la aplicación.
- **SecurityConfig:** Configuración de seguridad de Spring Boot para proteger los endpoints.
- **Frontend (Angular):** La interfaz de usuario está desarrollada con Angular y utiliza Angular Material para una apariencia moderna y consistente.

## Instalación y Uso

1. Clona el repositorio desde GitHub.
2. Configura la base de datos MySQL y actualiza la configuración de conexión en `application.properties`.
3. Ejecuta la aplicación Spring Boot.
4. Accede a la interfaz de usuario en tu navegador para comenzar a utilizar la aplicación.



## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).

