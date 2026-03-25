# Sistema de Gestión de Socios y Cuotas

### Proyecto Fin de Ciclo – Desarrollo de Aplicaciones Multiplataforma (DAM)

## Descripción del proyecto

Este proyecto consiste en el desarrollo de una aplicación de escritorio destinada a la gestión interna de un club deportivo amateur. El sistema permite administrar socios, controlar el pago de cuotas mensuales y gestionar las invitaciones asociadas a cada tipo de membresía.

La aplicación centraliza la información del club en una base de datos relacional, sustituyendo métodos manuales como hojas de cálculo o registros en papel. De esta forma se mejora la trazabilidad de los datos, se reducen errores administrativos y se facilita la gestión diaria del club.

El proyecto se desarrolla como aplicación Java con interfaz gráfica en JavaFX y persistencia de datos en MySQL.

---

## Objetivos del proyecto

El objetivo principal del proyecto es diseñar e implementar un sistema funcional que permita:

* Gestionar socios de un club deportivo.
* Registrar y consultar pagos de cuotas mensuales.
* Controlar las invitaciones asociadas a cada tipo de cuota.
* Facilitar la consulta del estado de pago de los socios.
* Centralizar la información administrativa del club.

Además, el proyecto busca aplicar los conocimientos adquiridos durante el ciclo formativo de Desarrollo de Aplicaciones Multiplataforma, integrando base de datos, lógica de negocio e interfaz de usuario.

---

## Funcionalidades principales

El sistema incluye las siguientes funcionalidades:

### Gestión de socios

* Alta de nuevos socios.
* Modificación de datos de socios existentes.
* Eliminación de socios.
* Consulta de la información completa de los socios.

### Gestión de cuotas

* Tipos de cuota configurables:

  * Básica
  * Gold
  * Premium
* Cada tipo de cuota dispone de un número determinado de invitaciones mensuales.

### Gestión de pagos

* Registro de pagos mensuales asociados a cada socio.
* Consulta del estado de pago por mes y año.
* Identificación de socios al corriente o con pagos pendientes.

### Gestión de invitaciones

* Control del número de invitaciones disponibles según el tipo de cuota.
* Registro del uso de invitaciones por periodo.

### Interfaz gráfica

* Tabla de visualización de socios.
* Formularios para creación y edición de socios.
* Confirmación de acciones críticas como eliminación de registros.

---

## Tecnologías utilizadas

El proyecto ha sido desarrollado utilizando las siguientes tecnologías:

Lenguaje de programación

* Java

Interfaz gráfica

* JavaFX

Base de datos

* MySQL

Acceso a datos

* JDBC

Entorno de desarrollo

* IntelliJ IDEA

Control de versiones

* Git y GitHub

---

## Arquitectura del sistema

La aplicación sigue una arquitectura sencilla en capas:

Presentación
Interfaz gráfica desarrollada con JavaFX.

Lógica de negocio
Clases que gestionan las reglas del sistema, como el control de cuotas, pagos e invitaciones.

Acceso a datos
Clases DAO encargadas de la comunicación con la base de datos mediante JDBC.

Persistencia
Base de datos MySQL que almacena socios, pagos, tipos de cuota e invitaciones.

---

## Estructura del proyecto

El proyecto se organiza en los siguientes paquetes principales:

config
Configuración de la conexión con la base de datos.

model
Clases del modelo de datos (Socio, TipoCuota, Pago, Invitacion, etc.).

dao
Clases de acceso a datos encargadas de interactuar con la base de datos.

ui
Clases de la interfaz gráfica desarrolladas con JavaFX.

---

## Requisitos del sistema

Para ejecutar la aplicación es necesario disponer de:

* Java JDK 17 o superior
* MySQL Server
* JavaFX SDK
* IntelliJ IDEA o cualquier entorno compatible con proyectos Java

---

## Autor

Marcos Fraile Fernández

Proyecto desarrollado como parte del módulo de Proyecto Final del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).

---

## INSTRUCCIONES DE EJECUCIÓN

1. Requisitos:

   * Java 17 instalado
   * MySQL instalado


2. Base de datos:

   * Crear una base de datos llamada: club_deportivo
   * Ejecutar el archivo: base_de_datos/club_deportivo.sql


3. Configuración:

   * Usuario MySQL: root
   * Contraseña: (indicar la tuya o cambiar en DatabaseConnection.java)


4. Ejecución:

   * Ir a la carpeta "ejecutable"
   * Ejecutar el archivo run.bat


5. Uso:

   * Añadir socios
   * Registrar pagos
   * Usar invitaciones

NOTAS:

  * Si hay errores de conexión, revisar usuario y contraseña de MySQL
  * JavaFX ya está incluido en la carpeta ejecutable
