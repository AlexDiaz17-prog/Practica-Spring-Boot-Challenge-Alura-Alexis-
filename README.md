# 📚 LiterAlura - Catálogo de Libros con Spring Boot

Aplicación de consola desarrollada con **Spring Boot**, que consume la API pública de **Gutendex** para buscar libros y almacenarlos en una base de datos **PostgreSQL**.

El sistema permite consultar libros por título, almacenarlos evitando duplicados y realizar diferentes consultas desde un menú interactivo en consola.

---

## 🚀 Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- API pública: Gutendex (https://gutendex.com/)
- Maven

---

## 🏗 Arquitectura del Proyecto

La aplicación sigue una estructura limpia separada por responsabilidades:


DemoApplication
↓
Principal (menú interactivo)
↓
Consumo API + Conversión JSON
↓
Repositories (JPA)
↓
Base de datos PostgreSQL


---

## 📂 Estructura principal


com.example.demo
│
├── model
│ ├── Autor (record API)
│ ├── Libro (record API)
│ ├── DatosGeneralesLibro (record API)
│ ├── AutorBaseDatos (Entidad JPA)
│ └── LibroBaseDatos (Entidad JPA)
│
├── repository
│ ├── AutorRepository
│ └── LibroRepository
│
├── principal
│ └── Principal (menú y lógica principal)
│
└── DemoApplication (punto de inicio)


---

## 🗄 Base de Datos

Se crean automáticamente las siguientes tablas:

- `autores`
- `libros`
- `libros_autores` (tabla intermedia ManyToMany)

### 🔁 Relación

Un libro puede tener múltiples autores y un autor puede tener múltiples libros:


LibroBaseDatos ←→ AutorBaseDatos
@ManyToMany


---

## 📌 Funcionalidades

Al ejecutar la aplicación se muestra el siguiente menú:


1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en determinado año
5 - Listar libros por idioma
0 - Salir


---

### 1️⃣ Buscar libro por título

- Consulta la API Gutendex.
- Obtiene el primer resultado.
- Guarda el libro en la base de datos.
- Evita duplicar libros.
- Evita duplicar autores.
- Mantiene correctamente la relación ManyToMany.

---

### 2️⃣ Listar libros registrados

Muestra en consola:

- Título
- Idioma
- Número de descargas

---

### 3️⃣ Listar autores registrados

Muestra:

- Nombre
- Año de nacimiento

---

### 4️⃣ Listar autores vivos en un año determinado

- Filtra autores cuyo año de nacimiento sea menor o igual al año ingresado.

> ⚠ Puede mejorarse agregando año de fallecimiento para mayor precisión.

---

### 5️⃣ Listar libros por idioma

Permite filtrar por:

- `es` → Español  
- `en` → Inglés  
- `fr` → Francés  
- `pt` → Portugués  

---

## 🛡 Validaciones implementadas

- ✔ No se duplican libros  
- ✔ No se duplican autores  
- ✔ Uso de `Optional` para evitar errores  
- ✔ Restricciones `unique` en base de datos  
- ✔ Manejo básico de listas vacías  

---

## ⚙ Configuración necesaria

En `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
▶ Cómo ejecutar

Clonar el repositorio

Configurar PostgreSQL

Ejecutar:

mvn spring-boot:run

O ejecutar la clase:

DemoApplication.java
🎯 Objetivo del proyecto

Este proyecto demuestra:

Consumo de API externa

Conversión de JSON a objetos Java

Uso de Records

Persistencia con JPA

Relaciones ManyToMany

Consultas derivadas en Spring Data

Arquitectura limpia en consola

Manejo de duplicados

Uso de Streams
