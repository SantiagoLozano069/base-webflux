# base-webflux

Proyecto base de gestión de usuarios (CRUD) que emplea diversas tecnologías.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Uso](#uso)


## Descripción

El objetivo principal es brindar una base para la construcción de nuevos proyectos, por ende, se desarrolla las principales operaciones (CRUD) implementando tecnologías comúnes en proyectos de desarrollo de software, tales como:

  - WebFlux
  - Spring Security (JWT)
  - WebClient
  - MongoDb
  - Unit Test
  - Integration Test
  - Docker
  - Cocker Compose

Para lograr esto se crearon dos proyectos:
  - security-gateway -> Encargado de recibir las peticiones del cliente, autenticar, autorizar y consumir las funcionalidades del siguiente servicio. 
  - user-management -> Encargado de gestionar usuarios mediante las funcionalidades más basicas (CRUD) y persistir en una base de datos No Relacional (MongoDb).
    

## Requisitos

Para modificar el proyecto se debe tener en cuenta el uso e implementación de las siguientes tecnologías:
  - Java 17
  - Spring WebFlux
  - Maven
    
    
## Configuración

Para ejecutar el proyecto se debe tener instalado java 17, Maven y Docker. Una vez se cuente con las herramientas necesarias se descarga el proyecto, se ubica en el directorio ./ donde se encuentra el archivo docker-compose, se abre la terminal de comandos y se ejecuta el comando: docker compose up


## Uso

El proyecto cuenta con una serie de métodos o funciones que se describen a continuación:


  - CREATE_USER -> Es la funcionalidad encargada de crear nuevos usuarios para que, posteriormente, se pueda realizar el login y tener acceso a las demás funcionaes.
     - Tipo: POST
     - Endopoint: http://localhost:8081/autentication/create-user
     - Body: {
              "name" : "Santiago Lozano",
              "email": "slozano@gmail.com",
              "password": "santia4ggd."
            }
     - Response: {
                  "id": "64f074903a95cb3f2a72836f",
                  "name": "Santiago Lozano",
                  "email": "slozano@gmail.com",
                  "password": "f0EhWdF2vWdXIHmFX03Z2obr6fdiFllkOD1VIX9JjqY="
                }



  - LOGIN -> Función encargada de recibir el correo y la contraseña del usuario creado con anterioridad y generar un token con el que se podrá acceder a las demás funcionaliades.
      - Tipo: POST
      - Endpoint: http://localhost:8081/autentication/login
      - Body:
            {
              "username" : "slozano@gmail.com",
              "password": "santia4ggd."
            }
      - Response: 
                {
                  "token":    "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJzdWIiOiJzYW50aWFnb2xvemFub0BnbWFpbC5jb20iLCJpYXQiOjE2OTM0ODAxMzAsImV4cCI6MTY5MzUwODkzMH0.KcW0NButRo0PdY5Tgb350SM6Pnswud8PKnxUNW9Wt5TyPjg5TvnKGczAuvWDykr69foa0fhXxme1_G2-aa3Now"
                }



  - GET_USER_BY_EMAIL -> Función que obtiene la información de un usuario con base a un email dado.
      - Tipo: GET
      - Endpoint: http://localhost:8081/api/v1/user/slozano@gmail.com
      - Header: Authorization
      - Response:
                {
                  "id": "64f074903a95cb3f2a72836f",
                  "name": "Santiago Lozano",
                  "email": "slozano@gmail.com",
                  "password": "f0EhWdF2vWdXIHmFX03Z2obr6fdiFllkOD1VIX9JjqY="
                }



  - GET_ALL_USERS -> Función que muestra la lista completa de todos los usuarios registrados.
      - Tipo: GET
      - Endpoint: http://localhost:8081/api/v1/user
      - Header: Authorization
      - Response:
                  [
                      {
                          "id": "64e0ac506afb9c21cf846c97",
                          "name": "Santiago",
                          "email": "santttttiagolozano200@gmail.com",
                          "password": "123"
                      },
                      {
                          "id": "64e0ac616afb9c21cf846c98",
                          "name": "Santiago",
                          "email": "siagolozano@gmail.com",
                          "password": "123"
                      },
                      {
                          "id": "64e0ac9c6350163541cb7d6b",
                          "name": "Santiago",
                          "email": "santtiagolozano120@gmail.com",
                          "password": "123"
                      },
                      {
                          "id": "64e12dd597482c1002980115",
                          "name": "Valentina Aldana",
                          "email": "valentinaaldana@gmail.com",
                          "password": "VAl123"
                      }
                ]
    
