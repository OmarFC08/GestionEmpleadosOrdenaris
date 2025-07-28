# 📋 Sistema de Gestión de Empleados

Este proyecto es un sistema de gestión para registrar empleados, su dirección y jerarquía (relación jefe-subordinado), desarrollado con **Spring Boot** y **Oracle 11g**. Permite realizar operaciones CRUD básicas, modificar jerarquía y puestos, y visualizar la estructura completa de la organización.

---

## 🚀 Tecnologías utilizadas

- ☕ Java 21
- 🌱 Spring Boot
- 🧪 Spring Data JPA
- 🐘 Oracle 11g (SQL Developer + Data Modeler)
- 🛠 Maven
- 💻 Spring Tool Suite 3.5.4

---

## 📦 Instalación y ejecución

1. **Clona el repositorio** (si aplica):
   ```bash
   git clone https://github.com/OmarFC08/GestionEmpleadosOrdenaris
   cd GestionEmpleadosOrdenaris
   ```

2. **Configura la base de datos Oracle**:
   - Crea un esquema y las tablas necesarias (empleados y direcciones).
   - Ajusta las credenciales en `application.properties`.

3. **Ejecuta el proyecto con Maven desde Spring Tool Suite o terminal**:
   ```bash
   mvn spring-boot:run
   ```

---

## 🔧 Endpoints disponibles

### 📌 Crear nuevo empleado con dirección
```http
POST /empleados
```
**Body (JSON):**
```json
{
    "nombres": "Fernando",
    "apellidos": "Martinez Martinez",
    "curp": "MAMF000205HMCRRRA2",
    "puesto": "Desarollador",
    "claveJefe": "FOCO96050832",

    "calle": "Av. Matamoros",
    "noExterior": "13",
    "noInterior": "",
    "colonia": "Centro",
    "municipio": "Cuauhtémoc",
    "estado": "CDMX",
    "pais": "México"
}
```

---

### 📌 Listar todos los empleados
```http
GET /empleados
```
Resultado esperado: La lista completa de los empleados
```json
    {
        "claveEmpleado": "FOCO96050832",
        "nombres": "Omar Flores",
        "apellidos": "Flores Castillo",
        "curp": "FOCO960508HTLLSM08",
        "puesto": "Arquitecto de Software",
        "jefe": null,
        "direccion": {
            "idDireccion": 2,
            "calle": "Adolfo lopez Mateos",
            "noExterior": "123",
            "noInterior": null,
            "colonia": "Del valle",
            "municipio": "Venustaino Carranza",
            "estado": "CDMX",
            "pais": "México"
        }
    },
    {
        "claveEmpleado": "GUGP00071284",
        "nombres": "Patricia",
        "apellidos": "Gutierrez Garcia",
        "curp": "GUGP000712MTLTRA0",
        "puesto": "Desarrollador Java",
        "jefe": {
            "claveEmpleado": "FOCO96050832",
            "nombres": "Omar Flores",
            "apellidos": "Flores Castillo",
            "curp": "FOCO960508HTLLSM08",
            "puesto": "Arquitecto de Software",
            "jefe": null,
            "direccion": {
                "idDireccion": 2,
                "calle": "Adolfo lopez Mateos",
                "noExterior": "123",
                "noInterior": null,
                "colonia": "Del valle",
                "municipio": "Venustaino Carranza",
                "estado": "CDMX",
                "pais": "México"
            }
        }.......

```
### 📌 Modificar el puesto de un empleado
```http
PATCH /empleados/{claveEmpleado}/puesto
```
**Body:**
```json
{
  "puesto": "Gerente"
}
```

---

### 📌 Modificar el jefe de un empleado
```http
PATCH /empleados/{claveEmpleado}/jefe
```
**Body:**
```json
{
  "claveJefe": "LOMG900505"
}
```

---

### 📌 Obtener jerarquía de empleados
```http
GET /empleados/jerarquia
```

Devuelve una lista con la jerarquía completa de empleados (quién es jefe de quién).

---

## 🧠 Lógica adicional

- **Generación automática de clave de empleado** a partir de los primeros 10 caracteres del CURP + 2 dígitos únicos generados aleatoriamente.
- Relación uno-a-uno entre `GEmpleados` y `GDireccionEmpleado`.
- Relación jerárquica entre empleados (jefe-subordinado).

## 📁 Recursos adicionales

- 🧪 [Colección Postman](postman/ORDENARIS.postman_collection.json)
- 🧭 [Diagrama E-R](docs/diagrama-er.png)
- 🐘 [SQL Base de datos](docs/GestionEmpleados.sql)

---

## 👨‍💻 Autor

**Omar Flores Castillo**
-Tel: 241 108 97 75
-email: omarflores8596@gmail.com
---
