
-- PlayMatch - Script de Base de Datos



-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS playmatch_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE playmatch_db;



--  Las tablas se crean automáticamente con spring.jpa.hibernate.ddl-auto=update
-- Este script es solo para crear la BD y insertar datos de prueba.



-- DATOS DE PRUEBA


-- Usuarios de prueba
INSERT INTO usuarios (nombre, email, password, telefono, reputacion, fecha_registro) VALUES
    ('Ivan', 'ivan@email.com', '1234', '600111222', 5.0, NOW()),
    ('Maria', 'maria@email.com', '1234', '600333444', 4.5, NOW()),
    ('Carlos', 'carlos@email.com', '1234', '600555666', 3.8, NOW()),
    ('Ana', 'ana@email.com', '1234', '600777888', 4.2, NOW()),
    ('Pedro', 'pedro@email.com', '1234', '600999000', 5.0, NOW())
ON DUPLICATE KEY UPDATE nombre = nombre;

-- Pistas de prueba
INSERT INTO pistas (nombre, foto, precio_hora, ubicacion, capacidad_max) VALUES
    ('Campo Municipal La Fica', 'https://ejemplo.com/fica.jpg', 25.00, 'Murcia Centro', 14),
    ('Pista El Palmar', 'https://ejemplo.com/palmar.jpg', 20.00, 'El Palmar, Murcia', 10),
    ('Campo Espinardo', 'https://ejemplo.com/espinardo.jpg', 15.00, 'Espinardo, Murcia', 14),
    ('Indoor Fútbol Murcia', 'https://ejemplo.com/indoor.jpg', 35.00, 'Murcia Centro', 10),
    ('Pista La Alberca', 'https://ejemplo.com/alberca.jpg', 18.00, 'La Alberca, Murcia', 12)
ON DUPLICATE KEY UPDATE nombre = nombre;

-- Reservas de prueba
INSERT INTO reservas (usuario_id, pista_id, fecha_partido, hora_inicio, hora_fin, estado) VALUES
    (1, 1, CURDATE() + INTERVAL 3 DAY, '18:00:00', '19:30:00', 'pagado'),
    (2, 2, CURDATE() + INTERVAL 5 DAY, '20:00:00', '21:30:00', 'pendiente'),
    (1, 3, CURDATE() + INTERVAL 7 DAY, '17:00:00', '18:30:00', 'pendiente')
ON DUPLICATE KEY UPDATE estado = estado;

-- Partidos de prueba
INSERT INTO partidos (reserva_id, titulo, jugadores_max, estado, es_publica) VALUES
    (1, 'Pachanga viernes tarde', 14, 'abierto', true),
    (2, 'Partido domingo relax', 10, 'abierto', true),
    (3, 'Entreno semanal', 14, 'abierto', false)
ON DUPLICATE KEY UPDATE titulo = titulo;

-- Participaciones de prueba
INSERT INTO participaciones (usuario_id, partido_id, estado) VALUES
    (1, 1, 'confirmado'),
    (2, 1, 'confirmado'),
    (3, 1, 'confirmado'),
    (4, 2, 'confirmado'),
    (5, 2, 'confirmado'),
    (1, 2, 'lista_espera')
ON DUPLICATE KEY UPDATE estado = estado;



