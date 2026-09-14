# Plataforma de Citas Médicas — Backend

Backend del Sprint 1 del proyecto de citas médicas, desarrollado con **Spring Boot** y **PostgreSQL** (alojado en Supabase).

## Historias de usuario implementadas

- **HU-01 — Registro de usuario**: un usuario crea su cuenta para gestionar sus citas médicas.
- **HU-06 — Registrar especialidad**: un especialista registra los tipos de consulta que ofrece.

## Tecnologías

- Java 17
- Spring Boot 4.1.1 (Web, Data JPA, Validation)
- PostgreSQL (Supabase)
- Maven

## Estructura del proyecto

```
src/main/java/com/citas/app/CitasBack/
 ├── controller   → Endpoints REST
 ├── dto          → Objetos de petición con validaciones
 ├── model        → Entidades (tablas de la base de datos)
 ├── repository   → Acceso a datos (JPA)
 └── service      → Lógica de negocio
```

## Cómo correrlo

1. Clona el repositorio.
2. Crea el archivo `src/main/resources/application.properties` con tu propia conexión a PostgreSQL:
   ```
   spring.datasource.url=jdbc:postgresql://TU_HOST:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
3. Abre el proyecto en VS Code o IntelliJ.
4. Corre la clase `CitasBackApplication.java`.
5. El servidor queda disponible en `http://localhost:8080`.

## Endpoints disponibles

### Registrar especialidad — `POST /api/especialidades`

Body de ejemplo:
```json
{
  "nombreEspecialidad": "Optometría",
  "descripcion": "Consulta general de la vista",
  "duracionMinutos": 30,
  "idEspecialista": 1
}
```

Respuestas:
- `201 Created` — especialidad registrada correctamente, junto con los datos del especialista.
- `400 Bad Request` — falta algún campo obligatorio (nombre, descripción, duración o id del especialista).
- `404 Not Found` — el `idEspecialista` indicado no existe.

### Listar especialidades — `GET /api/especialidades`

Devuelve todas las especialidades registradas.

### Registrar usuario — `POST /api/usuarios`

Body de ejemplo:
```json
{
  "nombre": "Ana María",
  "apellido": "Pérez",
  "correo": "ana.perez@correo.com",
  "contrasena": "claveSegura123",
  "confirmarContrasena": "claveSegura123",
  "telefono": "3001234567"
}
```

Respuestas:
- `201 Created` — cuenta creada correctamente, se devuelven los datos del usuario (sin la contraseña).
- `400 Bad Request` — falta algún campo obligatorio, las contraseñas no coinciden, o el correo ya está registrado.

## Nota sobre datos de prueba

Como HU-01 (registro de usuario) todavía no está implementada, para probar este endpoint es necesario crear manualmente un registro en las tablas `usuario` y `especialista` desde el Table Editor de Supabase, usando el mismo `id_usuario` en ambas tablas.
