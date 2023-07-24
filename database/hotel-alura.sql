create database hotel_alura;
use hotel_alura;

-- Creación de la tabla "login"
CREATE TABLE login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(150) NOT NULL,
    clave VARCHAR(150) NOT NULL
);
select * from login;
INSERT INTO login (usuario, clave) VALUES ('admin', 'admin');

-- Creación de la tabla "huespedes"
CREATE TABLE huespedes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  fecha_nacimiento DATE,
  nacionalidad VARCHAR(50),
  telefono VARCHAR(20) NOT NULL,
  reserva_id INT,
  FOREIGN KEY (reserva_id) REFERENCES reservas(id)
);

-- Creación de la tabla "reservas"
CREATE TABLE reservas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fecha_entrada DATE NOT NULL,
  fecha_salida DATE NOT NULL,
  valor DECIMAL(20, 2) NOT NULL,
  forma_pago VARCHAR(50)
);
