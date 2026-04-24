# PlayMatch Backend - API REST con Spring Boot

## Requisitos previos
- **Java 17** o superior
- **Maven 3.8+**
- **MySQL 8.0+** (o MariaDB 10.6+)

## Configuración rápida

### 1. Crear la base de datos
```bash
mysql -u root -p < src/main/resources/schema.sql
```
Esto crea la BD `playmatch_db` y opcionalmente inserta datos de prueba.

### 2. Configurar credenciales
Edita `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```
La API estará disponible en `http://localhost:8080`

---

## Endpoints de la API

### Usuarios (`/api/usuarios`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/usuarios` | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/usuarios` | Registrar nuevo usuario |
| PUT | `/api/usuarios/{id}` | Actualizar datos del usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario |
| POST | `/api/usuarios/login` | Iniciar sesión |

**Ejemplo - Registro:**
```json
POST /api/usuarios
{
    "nombre": "Juan",
    "email": "juan@email.com",
    "password": "1234",
    "telefono": "600123456"
}
```

**Ejemplo - Login:**
```json
POST /api/usuarios/login
{
    "nombre": "Juan",
    "password": "1234"
}
```

---

### Pistas (`/api/pistas`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pistas` | Listar todas las pistas |
| GET | `/api/pistas/{id}` | Obtener pista por ID |
| GET | `/api/pistas/buscar?ubicacion=Murcia` | Buscar por ubicación |
| POST | `/api/pistas` | Crear nueva pista |
| PUT | `/api/pistas/{id}` | Actualizar pista |
| DELETE | `/api/pistas/{id}` | Eliminar pista |

**Ejemplo - Crear pista:**
```json
POST /api/pistas
{
    "nombre": "Campo Municipal",
    "foto": "https://ejemplo.com/foto.jpg",
    "precioHora": 25.00,
    "ubicacion": "Murcia Centro",
    "capacidadMax": 14
}
```

---

### Reservas (`/api/reservas`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/reservas` | Listar todas las reservas |
| GET | `/api/reservas/{id}` | Obtener reserva por ID |
| GET | `/api/reservas/usuario/{id}` | Reservas de un usuario |
| GET | `/api/reservas/pista/{id}` | Reservas de una pista |
| POST | `/api/reservas` | Crear nueva reserva |
| PUT | `/api/reservas/{id}/estado` | Cambiar estado |
| DELETE | `/api/reservas/{id}` | Eliminar reserva |

**Ejemplo - Crear reserva:**
```json
POST /api/reservas
{
    "usuarioId": 1,
    "pistaId": 2,
    "fechaPartido": "2026-05-15",
    "horaInicio": "18:00",
    "horaFin": "19:30"
}
```

**Ejemplo - Cambiar estado:**
```json
PUT /api/reservas/1/estado
{
    "estado": "pagado"
}
```

---

### Partidos (`/api/partidos`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/partidos` | Listar todos |
| GET | `/api/partidos/{id}` | Obtener por ID |
| GET | `/api/partidos/publicos` | Solo partidos públicos |
| GET | `/api/partidos/estado/{estado}` | Filtrar por estado |
| POST | `/api/partidos` | Crear partido |
| PUT | `/api/partidos/{id}` | Actualizar partido |
| PUT | `/api/partidos/{id}/estado` | Cambiar estado |
| DELETE | `/api/partidos/{id}` | Eliminar partido |

**Ejemplo - Crear partido:**
```json
POST /api/partidos
{
    "reservaId": 1,
    "titulo": "Pachanga viernes",
    "jugadoresMax": 14,
    "esPublica": true
}
```

---

### Participaciones (`/api/participaciones`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/participaciones` | Listar todas |
| GET | `/api/participaciones/{id}` | Obtener por ID |
| GET | `/api/participaciones/partido/{id}` | Jugadores de un partido |
| GET | `/api/participaciones/usuario/{id}` | Partidos de un usuario |
| POST | `/api/participaciones` | Unirse a un partido |
| DELETE | `/api/participaciones/salir?usuarioId=1&partidoId=2` | Salir de un partido |
| DELETE | `/api/participaciones/{id}` | Eliminar por ID |

**Ejemplo - Unirse a partido:**
```json
POST /api/participaciones
{
    "usuarioId": 3,
    "partidoId": 1
}
```

> Al unirse, si el partido está lleno, el usuario entra automáticamente en `lista_espera`.
> Al salir un confirmado, el primero en lista de espera se promueve a `confirmado`.

---

## Lógica de negocio incluida

- **Validación de solapamiento**: No se pueden crear dos reservas en la misma pista/hora/fecha.
- **Lista de espera automática**: Si un partido está lleno, el nuevo jugador queda en espera.
- **Promoción automática**: Si sale un confirmado, sube el primero de la lista de espera.
- **Unicidad**: Un usuario no puede unirse dos veces al mismo partido.
- **Validaciones JPA**: Email válido, campos obligatorios, valores positivos, etc.

---

## Integración con tu app Android

Para conectar tu app Android, cambia la `BASE_URL` de tu `RetrofitCliente.java`:

```java
// Antes (PHP)
private static final String BASE_URL = "https://pachangasfutbol.free.nf/futbol_app/";

// Después (Spring Boot local)
private static final String BASE_URL = "http://10.0.2.2:8080/api/";
// 10.0.2.2 es la IP del host desde el emulador Android

// En dispositivo físico, usa la IP de tu PC:
// private static final String BASE_URL = "http://192.168.x.x:8080/api/";
```

Y actualiza tu `ApiServicio.java`:
```java
public interface ApiServicio {

    @POST("usuarios/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @GET("usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("pistas")
    Call<List<Pista>> getPistas();

    @GET("partidos")
    Call<List<Partido>> getPartidos();

    @POST("usuarios")
    Call<Usuario> registrar(@Body Usuario usuario);

    @POST("reservas")
    Call<Reserva> crearReserva(@Body ReservaRequest request);

    @POST("partidos")
    Call<Partido> crearPartido(@Body PartidoRequest request);

    @POST("participaciones")
    Call<Participacion> unirseAPartido(@Body ParticipacionRequest request);
}
```

> **Nota**: El login ahora envía JSON (`@Body`) en vez de `@FormUrlEncoded` + `@Field`.
