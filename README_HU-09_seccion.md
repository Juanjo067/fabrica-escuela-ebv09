# Agregar al README.md, en la seccion "Historias de usuario implementadas"

- HU-09 — Definir horarios disponibles: un especialista define bloques de horario en los que puede recibir citas.

# Nueva seccion de endpoints — agregar despues de "Registrar usuario"

## Definir horario disponible — `POST /api/disponibilidad`

Body de ejemplo:

```json
{
  "diaSemana": "MONDAY",
  "horaInicio": "08:00:00",
  "horaFin": "12:00:00",
  "idEspecialista": 1
}
```

Respuestas:

- `201 Created` — horario registrado correctamente, junto con los datos del bloque.
- `400 Bad Request` — falta algun campo obligatorio (dia, hora de inicio, hora de fin o id del especialista).
- `400 Bad Request` — la hora de inicio no es anterior a la hora de fin.
- `400 Bad Request` — **conflicto de horario**: ya existe un bloque de ese especialista que se superpone con el rango solicitado, para el mismo dia.
- `404 Not Found` — el `idEspecialista` indicado no existe.

## Consultar disponibilidad de un especialista — `GET /api/disponibilidad/especialista/{idEspecialista}`

Devuelve todos los horarios activos de ese especialista.

---

# Criterios de aceptacion — HU-09 (para el documento de historias de usuario)

**HU-09 — Definir horarios disponibles**
Como especialista quiero definir mis horarios disponibles para organizar mi agenda de consultas.

**Escenario 1: Crear horario exitoso**
Dado que el especialista existe en el sistema
Cuando envia un dia de la semana, una hora de inicio y una hora de fin validas (inicio antes que fin)
Entonces el sistema debe registrar el horario y devolverlo con estado 201 Created.

**Escenario 2: Conflicto de horario**
Dado que el especialista ya tiene un horario registrado en un dia y rango de horas
Cuando intenta registrar otro horario para el mismo dia que se superpone con el existente
Entonces el sistema debe rechazar la solicitud con 400 Bad Request y el mensaje
"Ya existe un horario registrado que se superpone con el rango indicado para ese dia",
sin crear el nuevo horario.

**Escenario 3: Campos obligatorios incompletos**
Dado que el especialista esta registrando un horario
Cuando omite el dia, la hora de inicio, la hora de fin o el id del especialista
Entonces el sistema debe responder 400 Bad Request indicando cual campo especifico
hace falta (mismo formato que ya usan en HU-01/HU-06: `{"campo": "mensaje"}`).

**Requisitos no funcionales:**
- La agenda debe presentar fecha y hora de forma clara.
- El sistema debe evitar la creacion de horarios superpuestos, tanto a nivel de
  aplicacion (este servicio) como se recomienda reforzarlo mas adelante a nivel
  de base de datos con una restriccion, para evitar condiciones de carrera.
