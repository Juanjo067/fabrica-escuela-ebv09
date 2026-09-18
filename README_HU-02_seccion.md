# Agregar al README.md, en la seccion "Historias de usuario implementadas"

- HU-02 — Inicio de sesion: un usuario inicia sesion con su correo y contrasena para acceder a las funciones de la plataforma.

# Nueva seccion de endpoints

## Iniciar sesion — `POST /api/auth/login`

Body de ejemplo:

```json
{
  "correo": "ana.perez@correo.com",
  "contrasena": "claveSegura123"
}
```

Respuestas:

- `200 OK` — inicio de sesion exitoso, se devuelven los datos del usuario (sin la contrasena).
- `400 Bad Request` — falta el correo o la contrasena.
- `400 Bad Request` — el correo o la contrasena no son validos. El mensaje es
  generico a proposito: no se revela cual de los dos datos fue el incorrecto
  (asi lo pide el requisito no funcional de HU-02).

**Nota importante:** el proyecto todavia no tiene Spring Security ni BCrypt
instalado (no aparece en el pom.xml), asi que por ahora el login compara la
contrasena directamente contra lo que haya guardado en `contrasenaHash`. Esto
funciona para la demo del sprint, pero **no es seguro para un sistema real**:
las contrasenas quedan visibles en la base de datos. Si mas adelante agregan
la dependencia `spring-boot-starter-security`, avisen para actualizar tanto
el registro (HU-01) como el login (HU-02) a que usen `BCryptPasswordEncoder`.

---

# Criterios de aceptacion — HU-02 (para el documento de historias de usuario)

**HU-02 — Inicio de sesion**
Como usuario quiero iniciar sesion para acceder a las funciones de la plataforma.

**Escenario 1: Inicio de sesion exitoso**
Dado que el usuario tiene una cuenta registrada
Cuando ingresa su correo y contrasena correctos y confirma
Entonces el sistema debe darle acceso (200 OK) y devolver sus datos basicos.

**Escenario 2: Credenciales incorrectas**
Dado que el usuario esta en el formulario de inicio de sesion
Cuando ingresa un correo o contrasena incorrectos
Entonces el sistema debe responder 400 Bad Request con el mensaje "El correo
o la contrasena no son validos", sin indicar cual de los dos fue el dato
incorrecto, y no debe permitir el acceso.

**Requisitos no funcionales:**
- El sistema debe validar las credenciales antes de otorgar acceso.
- El mensaje de error no debe revelar si el usuario o la contrasena es el
  dato incorrecto.
