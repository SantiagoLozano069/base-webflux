# base-webflux

Proyecto base de gestión de usuarios (CRUD) que emplea diversas tecnologías.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Uso](#uso)
- [Contribución](#contribución)
- [Licencia](#licencia)


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
  - security-gateway : Encargado de recigir las peticiones del cliente, autenticar, autorizar y consumir las funcionalidades del siguiente servicio. 
  - user-management: Encargado de gestionar usuarios mediante las funcionalidades más basicas (CRUD) y persistir en una base de datos No Relacional (MongoDb).
    

## Requisitos

Para modificar el proyecto se debe tener en cuenta la utilización e implementación de las siguientes tecnologías:
  - Java 17
  - Spring WebFlux
  - Maven
  - Junit

    
## Configuración

Para ejecutar el proyecto se debe tener instalado java 17, Maven y Docker. Una vez se cuente con las herramientas necesarias se descarga el proyecto, se ubica en el directorio ./ donde se encuentra el archivo docker-compose, se abre la terminal de comandos y se ejecuta el comando: docker compose up


  



  
