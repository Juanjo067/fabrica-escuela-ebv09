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

### Consultar perfil — `GET /api/usuarios/{id}`

Devuelve los datos personales del usuario (sin la contraseña).

Respuestas:
- `200 OK` — datos del usuario.
- `404 Not Found` — no existe un usuario con ese id.

### Actualizar perfil — `PUT /api/usuarios/{id}`

Body de ejemplo:
```json
{
  "nombre": "Ana María",
  "apellido": "Pérez",
  "correo": "ana.perez@correo.com",
  "telefono": "3001234567"
}
```

Respuestas:
- `201 Created` — cuenta creada correctamente, se devuelven los datos del usuario (sin la contraseña).
- `400 Bad Request` — falta algún campo obligatorio, las contraseñas no coinciden, o el correo ya está registrado.


---

# Historias de usuario del Sprint 2

- HU-11 — Bloquear horario: un especialista bloquea un rango de horas en una fecha para que nadie pueda reservarlo, y puede desbloquearlo después.
- HU-13 — Consultar citas: un usuario ve sus citas con especialidad, especialista, fecha, hora y estado.
- HU-14 — Cancelar cita: un usuario cancela una cita pendiente y el horario queda libre para otros usuarios.
- HU-15 — Reprogramar cita: un usuario mueve una cita pendiente a otra fecha y hora del mismo especialista; el horario anterior queda libre.

## Modelo `Cita`

Una sola entidad `Cita` (tabla `cita`) la usan HU-11, HU-13, HU-14 y HU-15.

Campos: `idCita`, `usuario`, `especialista`, `especialidad`, `fechaCita`, `horaCita`, `estadoCita`, `fechaCreacion`, `motivoCancelacion`, `fechaCancelacion`.

Estados (`EstadoCita`): `SOLICITADA`, `CONFIRMADA`, `CANCELADA`, `COMPLETADA`.

Las respuestas no incluyen `contrasenaHash`, `correo` ni `telefono` del usuario y del especialista.

---

## HU-11: Bloquear horario

### Bloquear horario — `POST /api/especialistas/{idEspecialista}/bloqueos`

Body de ejemplo:

```json
{
  "fecha": "2026-10-19",
  "horaInicio": "09:00:00",
  "horaFin": "10:00:00",
  "motivo": "Reunión de equipo"
}
```

(`motivo` es opcional, máximo 200 caracteres. El rango bloqueado incluye la hora de inicio y excluye la de fin: 09:00-10:00 bloquea las citas de las 09:00 a las 09:59.)

Respuestas:

- `201 Created` — horario bloqueado; se devuelve el bloqueo con su `idBloqueo` y `estado: "ACTIVO"`.
- `400 Bad Request` — falta la fecha o una hora, la hora de inicio no es anterior a la de fin, la fecha u hora ya pasó, el rango tiene citas programadas, o ya existe un bloqueo activo que se cruza.
- `404 Not Found` — no existe un especialista con ese id.

### Desbloquear horario — `DELETE /api/bloqueos/{idBloqueo}`

No borra el registro: el bloqueo pasa a estado `LIBERADO` (queda como historial) y el horario vuelve a estar disponible.

Respuestas:

- `200 OK` — horario desbloqueado; se devuelve el bloqueo con `estado: "LIBERADO"`.
- `400 Bad Request` — el bloqueo ya había sido liberado.
- `404 Not Found` — no existe un bloqueo con ese id.

### Listar bloqueos activos — `GET /api/especialistas/{idEspecialista}/bloqueos`

Opcional `?fecha=AAAA-MM-DD` para ver solo los de un día. Sirve para obtener el `idBloqueo` que necesita el desbloqueo.

Respuestas:

- `200 OK` — lista de bloqueos activos (puede venir vacía).
- `400 Bad Request` — la fecha no tiene formato `AAAA-MM-DD`.
- `404 Not Found` — no existe un especialista con ese id.

### Reglas de negocio

- No se puede bloquear un rango que ya tenga una cita activa (`estadoCita` distinto de `CANCELADA`). Primero hay que cancelar o reprogramar esas citas (HU-14 / HU-15).
- No se pueden crear dos bloqueos activos que se crucen (uno que termina a las 10:00 y otro que empieza a las 10:00 sí se permiten).
- No se puede bloquear en el pasado. Se usa la hora de Colombia (`America/Bogota`).

### Pendiente: efecto en HU-12 (solicitar cita)

Para que un bloqueo impida reservar, `solicitarCita` (HU-12) debe revisar los bloqueos activos. Cuando HU-12 esté en `main`, hay que agregar esa validación.

### Datos de prueba

Hace falta un especialista existente. Como el registro de especialistas (HU-04) puede no estar integrado todavía, se crea manualmente un registro en las tablas `usuario` y `especialista` desde el Table Editor de Supabase, usando el mismo `id_usuario` en ambas tablas.

Para probar el rechazo por citas reservadas, inserta una cita a mano en la tabla `cita` desde Supabase.

La tabla `bloqueo_horario` se crea sola al arrancar la aplicación (`ddl-auto=update`).

---

## HU-13: Consultar citas

### Consultar citas de un usuario — `GET /api/citas/usuario/{idUsuario}`

Respuestas:

- `200 OK` — el usuario existe y tiene citas: se devuelve la lista.
- `200 OK` con `[]` — el usuario existe y no tiene citas (el frontend muestra el mensaje de "no tienes citas").
- `404 Not Found` — no existe un usuario con ese id (`No existe un usuario con id {idUsuario}`).

### Pruebas realizadas

- `GET /api/citas/usuario/1` con citas: 200 con la lista.
- `GET /api/citas/usuario/1` sin citas: 200 con `[]`.
- `GET /api/citas/usuario/999`: 404.

### Pendiente

El endpoint recibe el id en la URL y todavía no valida la sesión del usuario (depende del login, HU-02).

---

## HU-14: Cancelar cita

### Cancelar cita — `PUT /api/citas/{idCita}/cancelar`

Body de ejemplo:

```json
{
  "idUsuario": 2,
  "motivo": "Tengo un imprevisto"
}
```

(`idUsuario` es obligatorio; `motivo` es opcional, máximo 200 caracteres.)

Respuesta de ejemplo (`200 OK`):

```json
{
  "mensaje": "Cita cancelada exitosamente",
  "idCita": 5,
  "estadoCita": "CANCELADA",
  "motivo": "Tengo un imprevisto",
  "fechaCancelacion": "2026-10-12T19:30:12.481",
  "horarioLiberado": {
    "fechaCita": "2026-10-19",
    "horaCita": "08:30:00"
  }
}
```

Respuestas:

- `200 OK` — cita cancelada; se devuelve el resultado y el horario que quedó libre.
- `400 Bad Request` — falta `idUsuario`, la cita no pertenece a ese usuario, ya estaba cancelada, no está pendiente (`SOLICITADA` o `CONFIRMADA`), o faltan menos horas que la anticipación mínima.
- `404 Not Found` — no existe una cita con ese id.

### Condiciones para cancelar

1. La cita existe y pertenece al usuario que la cancela.
2. La cita está en estado `SOLICITADA` o `CONFIRMADA` (no `CANCELADA` ni `COMPLETADA`).
3. Faltan al menos 24 horas para la cita. Este valor se cambia en `application.properties` sin tocar código:

```properties
   citas.cancelacion.horas-minimas=24
```

   (Para una demo con citas cercanas se puede poner `0`.)

### Cómo queda liberado el horario

La cita no se borra: su `estadoCita` pasa a `CANCELADA`. Las validaciones de horario ocupado ignoran las citas `CANCELADA`, así que ese horario se puede volver a solicitar de inmediato.

### Datos de prueba

Hace falta una cita con fecha al menos 24 horas en el futuro. Mientras HU-12 no esté en `main`, se inserta a mano en la tabla `cita` desde Supabase, con un usuario y un especialista existentes.

**Nota importante:** todavía no hay token ni sesión que el backend pueda leer para saber quién cancela, así que el `idUsuario` viaja en el body para comprobar que la cita es suya. Cuando exista autenticación, ese dato se toma del token y se quita del body.

---

## HU-15: Reprogramar cita

### Reprogramar cita — `PUT /api/citas/{idCita}/reprogramar`

Body de ejemplo:

```json
{
  "idUsuario": 2,
  "nuevaFechaCita": "2026-10-20",
  "nuevaHoraCita": "10:00:00",
  "motivo": "Cambio de agenda"
}
```

(`idUsuario`, `nuevaFechaCita` y `nuevaHoraCita` son obligatorios; `motivo` es opcional, máximo 200 caracteres.)

Respuesta de ejemplo (`200 OK`):

```json
{
  "mensaje": "Cita reprogramada exitosamente",
  "idCita": 5,
  "estadoCita": "SOLICITADA",
  "horarioAnterior": { "fechaCita": "2026-10-19", "horaCita": "08:30:00" },
  "horarioNuevo":    { "fechaCita": "2026-10-20", "horaCita": "10:00:00" }
}
```

Respuestas:

- `200 OK` — cita reprogramada; se devuelven el horario nuevo y el anterior (que quedó libre).
- `400 Bad Request` — falta algún campo obligatorio, la cita no pertenece al usuario, no está pendiente (`SOLICITADA` o `CONFIRMADA`), faltan menos horas que la anticipación mínima, el nuevo horario ya pasó o es igual al actual, o está ocupado / bloqueado.
- `404 Not Found` — no existe una cita con ese id.

### Validaciones, en este orden

1. La cita existe, es del usuario y está pendiente (`SOLICITADA` o `CONFIRMADA`).
2. Faltan al menos 24 horas para la cita actual. Se cambia en `application.properties`:

```properties
   citas.reprogramacion.horas-minimas=24
```

3. El nuevo horario es futuro y distinto al actual.
4. El nuevo horario no está ocupado por otra cita activa.
5. El nuevo horario no está bloqueado por el especialista (HU-11).

**Pendiente:** la validación de que el nuevo horario caiga dentro de la disponibilidad activa del especialista (HU-09) está desactivada (marcada con `TODO` en `ReprogramacionCitaService`) hasta que esa HU esté en `main`.

### Cómo queda liberado el horario anterior

La cita conserva su mismo id y estado, y solo cambian su `fechaCita` y `horaCita`; así el horario viejo deja de estar ocupado y se puede volver a solicitar. Cada cambio queda registrado en la tabla `reprogramacion_cita` (de qué fecha y hora a cuál, cuándo y con qué motivo). La actualización de la cita y el registro del historial ocurren en una sola transacción.

### Datos de prueba

Hace falta una cita con fecha al menos 24 horas en el futuro, insertada a mano en la tabla `cita` desde Supabase mientras HU-12 no esté en `main`.

La tabla `reprogramacion_cita` se crea sola al arrancar la aplicación (`ddl-auto=update`).

**Nota importante:** igual que en HU-14, el `idUsuario` viaja en el body mientras no exista autenticación con token.

---

## Colecciones de Postman

En la carpeta `postman/` están `HU-11.postman_collection`, `HU-14.postman_collection` y `HU-15.postman_collection`.